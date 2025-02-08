//Old commented out stuff:





















// import com.sun.net.httpserver.HttpServer;
// import com.sun.net.httpserver.HttpHandler;
// import com.sun.net.httpserver.HttpExchange;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.InetSocketAddress;
// import java.util.ArrayList;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.URLDecoder;
// import java.nio.charset.StandardCharsets;
// import java.util.HashMap;
// import java.util.Map;


// public class Webserver {
// private static ArrayList<MoodEntry> entries = new ArrayList<>();
// public static void main(String[] args) throws IOException {
// HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
// server.createContext("/", new MyHandler());
// server.setExecutor(null);
// server.start();
// System.out.println("Server running at http://localhost:8000");
// }


// static class MyHandler implements HttpHandler {
// @Override
// public void handle(HttpExchange exchange) throws IOException {
// /*if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
// // Read the form data
// InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
// BufferedReader br = new BufferedReader(isr);
// String formData = br.readLine();


// // Parse form data
// Map<String, String> params = parseFormData(formData);
// // Create new mood entry
// int mood = Integer.parseInt(params.get("mood"));
// String note = params.get("note");
// entries.add(new MoodEntry(mood, note));
// }*/


// //verison for Gemini:
// // if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
// // // Read the form data
// // InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
// // BufferedReader br = new BufferedReader(isr);
// // String formData = br.readLine();
// // // Parse form data
// // Map<String, String> params = parseFormData(formData);
// // // Create new mood entry
// // int mood = Integer.parseInt(params.get("mood"));
// // String note = params.get("note");
// // // Get Gemini suggestions based on mood and note
// // String suggestions = GeminiAPI.getSuggestions(mood, note);
// // // Add entry with suggestions
// // entries.add(new MoodEntry(mood, note, suggestions));


// // exchange.getResponseHeaders().set("Location", "/");
// // exchange.sendResponseHeaders(302, -1);
// // return;
// // }


// //v2 for Gemini:
// if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
// try {
// // Read the form data
// InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
// BufferedReader br = new BufferedReader(isr);
// String formData = br.readLine();
// // Parse form data
// Map<String, String> params = parseFormData(formData);
// // Create new mood entry
// int mood = Integer.parseInt(params.get("mood"));
// String note = params.get("note");
// // Get Gemini suggestions
// String suggestions = GeminiAPI.getSuggestions(mood, note);
// // Add entry with suggestions
// entries.add(new MoodEntry(mood, note, suggestions));
// // Redirect back to homepage
// exchange.getResponseHeaders().add("Location", "/");
// exchange.sendResponseHeaders(303, -1);
// return;
// } catch (Exception e) {
// e.printStackTrace();
// String errorMessage = "Error processing your entry: " + e.getMessage();
// exchange.sendResponseHeaders(500, errorMessage.length());
// try (OutputStream os = exchange.getResponseBody()) {
// os.write(errorMessage.getBytes());
// }
// return;
// }
// }
// // v2 Gem end














// //end of change for Gemini


// String html = """
// <!DOCTYPE html>
// <html>
// <head>
// <title>MoodMate</title>
// <style>
// body { font-family: Arial; max-width: 800px; margin: 0 auto; padding: 20px; }
// .tab { overflow: hidden; border: 1px solid #ccc; background-color: #f1f1f1; }
// .tab button { background-color: inherit; float: left; border: none; outline: none;
// cursor: pointer; padding: 14px 16px; transition: 0.3s; }
// .tab button:hover { background-color: #ddd; }
// .tab button.active { background-color: #ccc; }
// .tabcontent { display: none; padding: 6px 12px; border: 1px solid #ccc;
// border-top: none; }
// #Home { display: block; }
// .form-group { margin: 20px 0; }
// button.submit { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; }
// </style>
// </head>
// <body>
// <h1>MoodMate</h1>
// <div class="tab">
// <button class="tablinks" onclick="openTab(event, 'Home')" id="defaultOpen">Home</button>
// <button class="tablinks" onclick="openTab(event, 'History')">History</button>
// <button class="tablinks" onclick="openTab(event, 'Analysis')">Analysis</button>
// <button class="tablinks" onclick="openTab(event, 'Resources')">Resources</button>
// </div>


// <div id="Home" class="tabcontent">
// <h2>Daily Check-in</h2>
// <form method="post">
// <div class="form-group">
// <label>How are you feeling today? (1-5):</label><br>
// <select name="mood" required>
// <option value="1">1 - Very Bad</option>
// <option value="2">2 - Bad</option>
// <option value="3">3 - Okay</option>
// <option value="4">4 - Good</option>
// <option value="5">5 - Excellent</option>
// </select>
// </div>
// <div class="form-group">
// <label>Add a note about your day:</label><br>
// <textarea name="note" rows="4" cols="50"></textarea>
// </div>
// <button type="submit" class="submit">Save Entry</button>
// </form>
// </div>


// <div id="History" class="tabcontent">
// <h2>Your Previous Entries</h2>
// """ + getPreviousEntries() + """
// </div>


// <div id="Analysis" class="tabcontent">
// <h2>Mood Analysis</h2>
// <p>Your mood trends and patterns will appear here.</p>
// </div>


// <div id="Resources" class="tabcontent">
// <h2>Helpful Resources</h2>
// <ul>
// <li>Mental Health Hotline: 988</li>
// <li>Wellness Tips</li>
// <li>Meditation Guides</li>
// </ul>
// </div>


// <script>
// function openTab(evt, tabName) {
// var i, tabcontent, tablinks;
// tabcontent = document.getElementsByClassName("tabcontent");
// for (i = 0; i < tabcontent.length; i++) {
// tabcontent[i].style.display = "none";
// }
// tablinks = document.getElementsByClassName("tablinks");
// for (i = 0; i < tablinks.length; i++) {
// tablinks[i].className = tablinks[i].className.replace(" active", "");
// }
// document.getElementById(tabName).style.display = "block";
// evt.currentTarget.className += " active";
// }


// // Get the element with id="defaultOpen" and click on it
// document.getElementById("defaultOpen").click();
// </script>
// </body>
// </html>
// """;


// exchange.sendResponseHeaders(200, html.length());
// try (OutputStream os = exchange.getResponseBody()) {
// os.write(html.getBytes());
// }
// }


// private Map<String, String> parseFormData(String formData) throws IOException {
// Map<String, String> map = new HashMap<>();
// String[] pairs = formData.split("&");
// for (String pair : pairs) {
// String[] keyValue = pair.split("=");
// if (keyValue.length == 2) {
// String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.toString());
// String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.toString());
// map.put(key, value);
// }
// }
// return map;
// }


// private String getPreviousEntries() {
// if (entries.isEmpty()) {
// return "<p>No entries yet</p>";
// }
// //old code
// /*StringBuilder sb = new StringBuilder();
// for (MoodEntry entry : entries) {
// sb.append(String.format("""
// <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
// <strong>Mood: %d/5</strong><br>
// Note: %s
// </div>
// """, entry.getMood(), entry.getNote()));
// }*/


// //new code for Gem suggestions
// StringBuilder sb = new StringBuilder();
// for (MoodEntry entry : entries) {
// sb.append(String.format("""
// <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
// <strong>Mood: %d/5</strong><br>
// Note: %s<br>
// <div style="margin-top: 10px; background-color: #f0f0f0; padding: 10px; border-radius: 5px;">
// <strong>🤖 AI Suggestions:</strong><br>
// %s
// </div>
// </div>
// """, entry.getMood(), entry.getNote(), entry.getSuggestions()));
// }
// //end of new code for Gem suggestions


// return sb.toString();
// }
// }
// }


















// // import com.sun.net.httpserver.HttpServer;
// // import com.sun.net.httpserver.HttpHandler;
// // import com.sun.net.httpserver.HttpExchange;
// // import java.io.IOException;
// // import java.io.OutputStream;
// // import java.net.InetSocketAddress;
// // import java.util.ArrayList;


// // public class Webserver
// // {
// // // Store our mood entries
// // private static ArrayList<MoodEntry> entries = new ArrayList<>();
// // public static void main(String[] args) throws IOException
// // {
// // HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
// // server.createContext("/", new MyHandler());
// // server.setExecutor(null);
// // server.start();
// // System.out.println("Server running at http://localhost:8000");
// // }


// // static class MyHandler implements HttpHandler {
// // @Override
// // public void handle(HttpExchange exchange) throws IOException
// // {
// // String html = """
// // <!DOCTYPE html>
// // <html>
// // <head>
// // <title>MoodMate</title>
// // <style>
// // body { font-family: Arial; max-width: 800px; margin: 0 auto; padding: 20px; }
// // .form-group { margin: 20px 0; }
// // button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; }
// // </style>
// // </head>
// // <body>
// // <h1>Welcome to MoodMate!</h1>
// // <form method="post">
// // <div class="form-group">
// // <label>How are you feeling today? (1-5):</label><br>
// // <select name="mood" required>
// // <option value="1">1 - Very Bad</option>
// // <option value="2">2 - Bad</option>
// // <option value="3">3 - Okay</option>
// // <option value="4">4 - Good</option>
// // <option value="5">5 - Excellent</option>
// // </select>
// // </div>
// // <div class="form-group">
// // <label>Add a note about your day:</label><br>
// // <textarea name="note" rows="4" cols="50"></textarea>
// // </div>
// // <button type="submit">Save Entry</button>
// // </form>
// // <h2>Previous Entries:</h2>
// // """ + getPreviousEntries() + """
// // </body>
// // </html>
// // """;


// // // Handle form submission
// // if (exchange.getRequestMethod().equalsIgnoreCase("POST"))
// // {
// // // Add code to handle form submission here
// // // For now, just redirect back to the main page
// // exchange.getResponseHeaders().set("Location", "/");
// // exchange.sendResponseHeaders(302, -1);
// // return;
// // }


// // exchange.sendResponseHeaders(200, html.length());
// // try (OutputStream os = exchange.getResponseBody())
// // {
// // os.write(html.getBytes());
// // }
// // }


// // private String getPreviousEntries() {
// // if (entries.isEmpty())
// // {
// // return "<p>No entries yet</p>";
// // }
// // StringBuilder sb = new StringBuilder();
// // for (MoodEntry entry : entries)
// // {
// // sb.append(String.format("""
// // <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
// // <strong>Mood: %d/5</strong><br>
// // Note: %s
// // </div>
// // """, entry.getMood(), entry.getNote()));
// // }
// // return sb.toString();
// // }
// // }
// // }










// // //--------------------------------------------------------------------
































// // // import com.sun.net.httpserver.HttpServer;
// // // import com.sun.net.httpserver.HttpHandler;
// // // import com.sun.net.httpserver.HttpExchange;
// // // import java.io.IOException;
// // // import java.io.OutputStream;
// // // import java.net.InetSocketAddress;


// // // public class Webserver {


// // // public static void main(String[] args) throws IOException {
// // // HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
// // // server.createContext("/", new Webserver.MyHandler()); // Note: Webserver.MyHandler
// // // server.setExecutor(null); // creates a default executor
// // // server.start();
// // // System.out.println("Server started on port 8000");
// // // }


// // // static class MyHandler implements HttpHandler { // Static inner class
// // // @Override
// // // public void handle(HttpExchange exchange) throws IOException {
// // // String response = "Hello World!";
// // // exchange.sendResponseHeaders(200, response.length());
// // // OutputStream os = exchange.getResponseBody();
// // // os.write(response.getBytes());
// // // os.close();
// // // }
// // // }
// // // }
















// specifc sections older versions:

//old handlePost functions:

        // private void handlePost(HttpExchange exchange) throws IOException {
        //     InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        //     BufferedReader br = new BufferedReader(isr);
        //     String formData = br.readLine();
            
        //     Map<String, String> params = parseFormData(formData);
        //     int mood = Integer.parseInt(params.get("mood"));
        //     String note = params.get("note");
        //     String suggestions = GeminiAPI.getSuggestions(mood, note);
        //     entries.add(new MoodEntry(mood, note, suggestions));
        
        //     // Send a complete HTML response instead of redirecting
        //     String response = getFullHtml();
        //     byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        //     exchange.sendResponseHeaders(200, bytes.length);
        //     try (OutputStream os = exchange.getResponseBody()) {
        //         os.write(bytes);
        //     }
        // }


        // good good good good good:
        //updated:
        // private void handlePost(HttpExchange exchange) throws IOException {
        //     try {
        //         // Read the form data
        //         InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        //         BufferedReader br = new BufferedReader(isr);
        //         String formData = br.readLine();
            
        //         // Parse form data
        //         Map<String, String> params = parseFormData(formData);
                
        //         // Create new mood entry
        //         int mood = Integer.parseInt(params.get("mood"));
        //         String note = params.get("note");
                
        //         // Get Gemini suggestions
        //         String suggestions = GeminiAPI.getSuggestions(mood, note);
                
        //         // Add entry with suggestions
        //         entries.add(new MoodEntry(mood, note, suggestions));
        
        //         // Redirect back to home page
        //         String response = getFullHtml();
        //         byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        //         exchange.getResponseHeaders().set("Content-Type", "text/html");
        //         exchange.sendResponseHeaders(200, responseBytes.length);
        //         try (OutputStream os = exchange.getResponseBody()) {
        //             os.write(responseBytes);
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //         String error = "Error processing entry: " + e.getMessage();
        //         byte[] errorBytes = error.getBytes();
        //         exchange.sendResponseHeaders(500, errorBytes.length);
        //         try (OutputStream os = exchange.getResponseBody()) {
        //             os.write(errorBytes);
        //         }
        //     }
        // }


































        // old get previous entries:

                //old
        // private String getPreviousEntries() {
        //     if (entries.isEmpty()) {
        //         return "<p>No entries yet</p>";
        //     }
            
        //     StringBuilder sb = new StringBuilder();
        //     for (MoodEntry entry : entries) {
        //         sb.append(String.format("""
        //             <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
        //                 <strong>Mood: %d/5</strong><br>
        //                 Note: %s<br>
        //                 <div style="margin-top: 10px; background-color: #f0f0f0; padding: 10px; border-radius: 5px;">
        //                     <strong>🤖 AI Suggestions:</strong><br>
        //                     %s
        //                 </div>
        //             </div>
        //             """, entry.getMood(), entry.getNote(), entry.getSuggestions()));
        //     }
        //     return sb.toString();
        // }












        // old analysis method:

                // //old Analysis method

        // //adding analysis for slep and mood and overall assessment
        // private String getAnalysis() {
        //     if (entries.isEmpty()) {
        //         return "<p>No entries yet for analysis.</p>";
        //     }

        //     int numEntries = entries.size();
        //     int numCompleteWeeks = numEntries / 7;

        //     //  debuggingggggggggg section
        //     if (numCompleteWeeks == 0) {
        //         return String.format("""
        //             <div style="padding: 10px;">
        //                 <p>Waiting for more entries to create analysis...</p>
        //                 <p>Current entries: %d</p>
        //                 <p>Entries needed for first analysis: 7</p>
        //                 <p>Entries remaining: %d</p>
        //             </div>
        //             """, numEntries, 7 - numEntries);
        //     }

        //     StringBuilder analysis = new StringBuilder();


        //     for (int week = 0; week < numCompleteWeeks; week++) {
        //         int startIndex = week * 7;
        //         int endIndex = startIndex + 7;
        //         List<MoodEntry> weekEntries = entries.subList(startIndex, endIndex);

        //         // Calculate averages for this week
        //         double avgMood = weekEntries.stream()
        //             .mapToInt(e -> e.getMood())
        //             .average()
        //             .orElse(0.0);
                
        //         double avgSleep = weekEntries.stream()
        //             .mapToDouble(e -> e.getSleepHours())
        //             .average()
        //             .orElse(0.0);

        //         // Get date range
        //         String startDate = weekEntries.get(0).getDate();
        //         String endDate = weekEntries.get(6).getDate();

        //         analysis.append(String.format("""
        //             <div style="border: 2px solid #4CAF50; padding: 15px; margin: 15px 0; border-radius: 10px;">
        //                 <h3>Analysis Block %d</h3>
        //                 <p><strong>Date Range:</strong> %s to %s</p>
        //                 <ul>
        //                     <li><strong>Average Mood:</strong> %.1f/5</li>
        //                     <li><strong>Average Sleep:</strong> %.1f hours</li>
        //                     <li><strong>Sleep-Mood Correlation:</strong> %s</li>
        //                     <li><strong>Overall Assessment:</strong> %s</li>
        //                 </ul>
        //             </div>
        //             """,
        //             week + 1,
        //             startDate, endDate,
        //             avgMood,
        //             avgSleep,
        //             getSleepMoodCorrelation(avgSleep, avgMood),
        //             getOverallAssessment(avgMood, avgSleep)
        //         ));
        //     }

        //     return analysis.toString();
        // }

        // end of old Analysis method





















//
        // old getSleepMoodCorrelation method

        // private String getSleepMoodCorrelation(double avgSleep, double avgMood) {
        //     if (avgSleep >= 7 && avgMood >= 3.5) {
        //         return "Positive correlation - good sleep appears to contribute to better mood";
        //     } else if (avgSleep < 7 && avgMood < 3.5) {
        //         return "Low sleep may be affecting mood negatively";
        //     }
        //     return "Mixed correlation between sleep and mood";
        // }

        //end of old getSleepMoodCorrelation method







//


        //old getOverallAssessment method
        // private String getOverallAssessment(double avgMood, double avgSleep) {
        //     if (avgMood >= 4) {
        //         return "Excellent week! Keep up the positive habits.";
        //     } else if (avgMood >= 3) {
        //         return "Good week overall. Consider implementing some of the AI suggestions for even better results.";
        //     } else {
        //         return "This week had some challenges. Focus on sleep hygiene and try the suggested activities.";
        //     }
        // }
        //end of old getOverallAssessment method

