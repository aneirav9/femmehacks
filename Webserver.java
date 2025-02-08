// filepath: vsls:/femmehacks/Webserver.java
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.nio.file.Files;

public class Webserver {
    private static ArrayList<MoodEntry> entries = new ArrayList<>();
    private static final String IMAGE_DIR = "./images"; // Directory for images
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server running at http://localhost:8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.startsWith("/images/")) {
                handleImage(exchange, path);
            } else {
                try {
                    if ("POST".equals(exchange.getRequestMethod())) {
                        handlePost(exchange);
                    } else {
                        handleGet(exchange);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "Error: " + e.getMessage();
                    exchange.sendResponseHeaders(500, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                }
            }
        }

    private void handleImage(HttpExchange exchange, String path) throws IOException 
    {
            System.out.println("handleImage called with path: " + path); // Add this line
            File imageFile = new File(IMAGE_DIR + path.substring(7));
            System.out.println("f" + imageFile.getAbsolutePath()); // Add this line
        if (!imageFile.exists()) 
        {
            System.out.println("Image not found: " + imageFile.getAbsolutePath()); // Add this line
            String response = "Image not found";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
             os.write(response.getBytes());
            }
            return;
        }

    byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
    String contentType = "";
    if (path.endsWith(".png")) {
        contentType = "image/png";
    } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
        contentType = "image/jpeg";
    } else if (path.endsWith(".gif")) {
        contentType = "image/gif";
    } else {
        String response = "Unsupported image format";
        exchange.sendResponseHeaders(400, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        return;
    }

    exchange.getResponseHeaders().set("Content-Type", contentType);
    exchange.sendResponseHeaders(200, imageBytes.length);
    try (OutputStream os = exchange.getResponseBody()) {
        os.write(imageBytes);
    }
}
        
        //updated for taking in sleep input:
        private void handlePost(HttpExchange exchange) throws IOException {
            try {
                // Read the form data
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
            
                // Parse form data
                Map<String, String> params = parseFormData(formData);
                
                // Create new mood entry with additional fields
                int mood = Integer.parseInt(params.get("mood"));
                String note = params.get("note");
                String date = params.getOrDefault("date", "");  // Default empty string if missing
                
                // Add error checking for sleep hours
                double sleepHours = 0.0;
                String sleepStr = params.get("sleep");
                if (sleepStr != null && !sleepStr.trim().isEmpty()) {
                    sleepHours = Double.parseDouble(sleepStr);
                }
                
                // Get Gemini suggestions
                String suggestions = GeminiAPI.getSuggestions(mood, note);
                
                // Add entry with suggestions and new fields
                entries.add(new MoodEntry(mood, note, suggestions, sleepHours, date));
                System.out.println("Total entries: " + entries.size());

                // Redirect back to home page
                String response = getFullHtml();
                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
                String error = "Error processing entry: " + e.getMessage();
                byte[] errorBytes = error.getBytes();
                exchange.sendResponseHeaders(500, errorBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(errorBytes);
                }
            }
        }
        
        private void handleGet(HttpExchange exchange) throws IOException {
            String response = getFullHtml();
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

//beginngin of new html:

//end of old html

//line 115 is line 1 of input for html edits
       private String getFullHtml() {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <title>MoodMate</title>
            <style>
                body { 
                    font-family: Arial; 
                    max-width: 800px; 
                    margin: 0 auto; 
                    padding: 20px;
                    background-color: #FFC0CB; /* Pink background */
                }
                .tab { overflow: hidden; border: 1px solid #ccc; background-color: #f1f1f1; }
                .tab button { background-color: inherit; float: left; border: none; outline: none;
                    cursor: pointer; padding: 14px 16px; transition: 0.3s; }
                .tab button:hover { background-color: #ddd; }
                .tab button.active { background-color: #ccc; }
                .tabcontent { display: none; padding: 6px 12px; border: 1px solid #ccc;
                    border-top: none; }
                #Home { display: block; }
                .form-group { margin: 20px 0; }
                button.submit { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; }

                .logo-container {
                    display: flex;
                    align-items: center;
                    margin-bottom: 20px;
                }

                .logo {
                    width: 100px;
                    height: 100px;
                    object-fit: contain;
                    margin-right: 20px;
                }

                .logo-title {
                    font-size: 24px;
                    color: #333;
                }
            </style>
        </head>
        <body>
            <div class="logo-container">
                <img src="https://i.ibb.co/YBRDjt9Q/Mood-Mate-2.png" width="100" height="100">  
            </div>

            <div class="tab">
                <button class="tablinks" onclick="openTab(event, 'Home')" id="defaultOpen">Home</button>
                <button class="tablinks" onclick="openTab(event, 'History')">History</button>
                <button class="tablinks" onclick="openTab(event, 'Analysis')">Analysis</button>
                <button class="tablinks" onclick="openTab(event, 'Resources')">Resources</button>
            </div>

            <div id="Home" class="tabcontent">
                <h2>Daily Check-in</h2>
                <form method="post">
                    <div class="form-group">
                        <label>Date:</label><br>
                        <input type="date" name="date" required>
                    </div>
                    <div class="form-group">
                        <label>Hours of sleep last night:</label><br>
                        <input type="number" name="sleep" min="0" max="24" step="0.5" required>
                    </div>

                    <div class="form-group">
                        <label>How are you feeling today? (1-5):</label><br>
                        <select name="mood" required>
                            <option value="1">1 - Very Bad</option>
                            <option value="2">2 - Bad</option>
                            <option value="3">3 - Okay</option>
                            <option value="4">4 - Good</option>
                            <option value="5">5 - Excellent</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Add a note about your day:</label><br>
                        <textarea name="note" rows="4" cols="50"></textarea>
                    </div>
                    <button type="submit" class="submit">Save Entry</button>
                </form>
            </div>

            <div id="History" class="tabcontent">
                <h2>Your Previous Entries</h2>
                """ + getPreviousEntries() + """
            </div>

            <!--- new w extra auggestions --->
            <div id="Analysis" class="tabcontent">
                <h2>Weekly Analysis</h2>
                """ + getAnalysis() + """
            </div>
            <!--- end new --->

            <div id="Resources" class="tabcontent">
                <h2>Helpful Resources</h2>
                <ul>
                    <li>Mental Health Hotline: 988</li>
                    <li>Wellness Tips: [https://app.projecthealthyminds.com/crisis?gad_source=1&gclid=CjwKCAiAnpy9BhAkEiwA-P8N4sXuYO8tvCCJiscm1-zT7IuiJwpv4Eomm7zTeThds71Hfs91RtJVyRoCX7gQAvD_BwE] & [https://www.cdc.gov/mental-health/caring/index.html#:~:text=Treatment%20and%20support&text=Visit%20findtreatment.gov%20–%20a%20confidential,Code%20to%20435748%20(HELP4U)]</li>
                    <li>Fun Games Guide: [https://www.crazygames.com/t/relaxing]</li>
                    <li>Meditation Guide: [https://www.mindful.org/how-to-meditate/]</li>
                </ul>
            </div>

            <script>
            function openTab(evt, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("tabcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("tablinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }

            document.getElementById("defaultOpen").click();
            </script>
        </body>
        </html>
        """;
}

        private Map<String, String> parseFormData(String formData) throws IOException {
            Map<String, String> map = new HashMap<>();
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.toString());
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.toString());
                    map.put(key, value);
                }
            }
            return map;
        }

        //updated for sleep and date data in previous entries:
        private String getPreviousEntries() {
            if (entries.isEmpty()) {
                return "<p>No entries yet</p>";
            }
            
            StringBuilder sb = new StringBuilder();
            for (MoodEntry entry : entries) {
                sb.append(String.format("""
                    <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                        <div style="display: flex; justify-content: space-between;">
                            <strong>Date: %s</strong>
                            <strong>Sleep: %.1f hours</strong>
                        </div>
                        <strong>Mood: %d/5</strong><br>
                        Note: %s<br>
                        <div style="margin-top: 10px; background-color: #f0f0f0; padding: 10px; border-radius: 5px;">
                            <strong>🤖 AI Suggestions:</strong><br>
                            %s
                        </div>
                    </div>
                    """, entry.getDate(), entry.getSleepHours(), entry.getMood(), entry.getNote(), entry.getSuggestions()));
            }
            return sb.toString();
        }

        
        //updated  analysis method
        private String getAnalysis() {
            if (entries.isEmpty()) {
                return "<p>No entries yet for analysis.</p>";
            }
        
            int numEntries = entries.size();
            int numCompleteWeeks = numEntries / 7;
        
            if (numCompleteWeeks == 0) {
                return String.format("""
                    <div style="padding: 10px; text-align: center;">
                        <p style="font-size: 1.2em;">🌱 Starting Your Wellness Journey!</p>
                        <p>You have logged %d entries so far.</p>
                        <p>Just %d more until your first weekly wellness insights!</p>
                    </div>
                    """, numEntries, 7 - numEntries);
            }
        
            StringBuilder analysis = new StringBuilder();
            for (int week = 0; week < numCompleteWeeks; week++) {
                int startIndex = week * 7;
                int endIndex = startIndex + 7;
                List<MoodEntry> weekEntries = entries.subList(startIndex, endIndex);
        
                double avgMood = weekEntries.stream()
                    .mapToInt(e -> e.getMood())
                    .average()
                    .orElse(0.0);
                
                double avgSleep = weekEntries.stream()
                    .mapToDouble(e -> e.getSleepHours())
                    .average()
                    .orElse(0.0);
        
                String startDate = formatDate(weekEntries.get(0).getDate());
                String endDate = formatDate(weekEntries.get(6).getDate());
        
                analysis.append(String.format("""
                    <div style="border: 2px solid #4CAF50; padding: 20px; margin: 20px 0; border-radius: 10px; background-color: #fafff9;">
                        <h3 style="color: #2E7D32;">✨ Week %d Reflection</h3>
                        <p><strong>📅 Week of:</strong> %s to %s</p>
                        <div style="background-color: white; padding: 15px; border-radius: 8px; margin: 15px 0;">
                            <p style="font-size: 1.1em; margin: 5px 0;"><strong>Weekly Insights:</strong></p>
                            <ul style="list-style-type: none; padding-left: 10px;">
                                <li>🌟 Average Mood: %.1f/5</li>
                                <li>💤 Average Sleep: %.1f hours</li>
                            </ul>
                        </div>
                        <div style="margin: 15px 0;">
                            <p><strong>💭 Sleep & Mood Connection:</strong></p>
                            <p style="padding-left: 15px;">%s</p>
                        </div>
                        <div style="margin-top: 15px;">
                            <p><strong>🌈 Wellness Tips for Next Week:</strong></p>
                            %s
                        </div>
                    </div>
                    """,
                    week + 1,
                    startDate, endDate,
                    avgMood,
                    avgSleep,
                    getSleepMoodCorrelation(avgSleep, avgMood),
                    getOverallAssessment(avgMood, avgSleep)
                ));
            }
            return analysis.toString();
        }
        //end of updated analysis method

        //Helper methods

        // new getSleepMoodCorrelation method
        private String getSleepMoodCorrelation(double avgSleep, double avgMood) {
            if (avgSleep >= 7 && avgMood >= 3.5) {
                return "Amazing job with your sleep routine! Your consistent rest seems to be contributing to your positive mood. Keep up this wonderful balance! 🌟";
            } else if (avgSleep < 7 && avgMood < 3.5) {
                return "This week's data suggests that getting a bit more rest might help boost your mood. Remember, small steps toward better sleep can make a big difference! 💫";
            }
            return "Your sleep and mood patterns show some interesting variations. Even small improvements to your sleep routine could help you feel even better! ✨";
        }
        //end of new getSleepMoodCorrelation method

        // new getOverallAssessment method
        private String getOverallAssessment(double avgMood, double avgSleep) {
            StringBuilder tips = new StringBuilder("<ul style='list-style-type: none; padding-left: 15px;'>");
            
            if (avgMood >= 4) {
                tips.append("""
                    <li>🌟 You're thriving! Here are some ways to maintain this positive momentum:</li>
                    <li>• Write down what's working well - it's great to remember for future weeks</li>
                    <li>• Share your positive energy with friends or family</li>
                    <li>• Try one new wellness activity to keep things fresh and engaging</li>
                    """);
            } else if (avgMood >= 3) {
                tips.append("""
                    <li>🌱 You're maintaining a good balance! Here are some gentle suggestions:</li>
                    <li>• Start your day with a 5-minute gratitude practice</li>
                    <li>• Try a new peaceful activity, like gentle stretching or journaling</li>
                    <li>• Connect with a friend for a mood-boosting chat</li>
                    """);
            } else {
                tips.append("""
                    <li>💝 Remember that every day is a fresh start. Here are some nurturing suggestions:</li>
                    <li>• Begin with tiny, achievable self-care moments</li>
                    <li>• Try a calming bedtime routine - maybe some gentle music or reading</li>
                    <li>• Consider reaching out to someone you trust for support</li>
                    <li>• Celebrate small wins - they add up to big progress!</li>
                    """);
            }
            tips.append("</ul>");
            return tips.toString();
        }
        //end of new getOverallAssessment method

        private String formatDate(String date) {
            try {
                String[] parts = date.split("-");
                if (parts.length == 3) {
                    return parts[1] + "/" + parts[2] + "/" + parts[0];
                }
            } catch (Exception e) {
                // If there's any error, return the original date
            }
            return date;
        }
    }
}