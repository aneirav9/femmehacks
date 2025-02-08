import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Webserver 
{
    // Store our mood entries
    private static ArrayList<MoodEntry> entries = new ArrayList<>();
    
    public static void main(String[] args) throws IOException 
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server running at http://localhost:8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException 
        {
            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>MoodMate</title>
                    <style>
                        body { font-family: Arial; max-width: 800px; margin: 0 auto; padding: 20px; }
                        .form-group { margin: 20px 0; }
                        button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; }
                    </style>
                </head>
                <body>
                    <h1>Welcome to MoodMate!</h1>
                    <form method="post">
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
                        <button type="submit">Save Entry</button>
                    </form>
                    
                    <h2>Previous Entries:</h2>
                    """ + getPreviousEntries() + """
                </body>
                </html>
                """;

            // Handle form submission
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) 
            {
                // Add code to handle form submission here
                // For now, just redirect back to the main page
                exchange.getResponseHeaders().set("Location", "/");
                exchange.sendResponseHeaders(302, -1);
                return;
            }

            exchange.sendResponseHeaders(200, html.length());
            try (OutputStream os = exchange.getResponseBody()) 
            {
                os.write(html.getBytes());
            }
        }

        private String getPreviousEntries() {
            if (entries.isEmpty()) 
            {
                return "<p>No entries yet</p>";
            }
            
            StringBuilder sb = new StringBuilder();
            for (MoodEntry entry : entries) 
            {
                sb.append(String.format("""
                    <div style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                        <strong>Mood: %d/5</strong><br>
                        Note: %s
                    </div>
                    """, entry.getMood(), entry.getNote()));
            }
            return sb.toString();
        }
    }
    
}















// import com.sun.net.httpserver.HttpServer;
// import com.sun.net.httpserver.HttpHandler;
// import com.sun.net.httpserver.HttpExchange;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.InetSocketAddress;

// public class Webserver {

//     public static void main(String[] args) throws IOException {
//         HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//         server.createContext("/", new Webserver.MyHandler()); // Note: Webserver.MyHandler
//         server.setExecutor(null); // creates a default executor
//         server.start();
//         System.out.println("Server started on port 8000");
//     }

//     static class MyHandler implements HttpHandler { // Static inner class
//         @Override
//         public void handle(HttpExchange exchange) throws IOException {
//             String response = "Hello World!";
//             exchange.sendResponseHeaders(200, response.length());
//             OutputStream os = exchange.getResponseBody();
//             os.write(response.getBytes());
//             os.close();
//         }
//     }
// }