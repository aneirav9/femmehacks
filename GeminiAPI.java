//updated gem class for properly parsing json response
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GeminiAPI {
    private static final String API_KEY = "AIzaSyBjrtnVJb_ONj8f3QpRg1nXU8lqcq30ur8";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;
    
    public static String getSuggestions(int mood, String note) {
        try {
            String prompt = String.format(
                "Based on a user who reported mood level %d/5 and wrote: '%s', " +
                "suggest 2-3 specific activities, resources, or media that could help improve their wellbeing or address how they're feeling. Acknowledge their sentiments in a friendly manner. Respond as a nice person giving advice or feedback. " +
                "Format the response with bullet points and include relevant links to sites to improve wellbeing based on their input, such as a link to a fun website game to make them happy or a link to a happy upbeat song on YouTube. Be lighthearted. Format correctly.", 
                mood, note);

            String jsonRequest = String.format("""
                {
                    "contents": [{
                        "parts": [{
                            "text": "%s"
                        }]
                    }]
                }""", prompt.replace("\"", "\\\""));  // Escape quotes in the prompt

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

            HttpResponse<String> response = client.send(request, 
                HttpResponse.BodyHandlers.ofString());
                
            if (response.statusCode() == 200) {
                // Extract just the text content from the response
                String responseBody = response.body();
                // Return a default message if something goes wrong
                return responseBody.contains("text") ? 
                    responseBody.split("\"text\": \"")[1].split("\"")[0] :
                    "Unable to generate suggestions at this time.";
            } else {
                return "Error getting suggestions: " + response.statusCode();
            }
            
        } catch (Exception e) {
            e.printStackTrace();  // This will help us see what went wrong
            return "Unable to get suggestions at this time: " + e.getMessage();
        }
    }
}








// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.nio.charset.StandardCharsets;

// public class GeminiAPI {
//     private static final String API_KEY = "AIzaSyBjrtnVJb_ONj8f3QpRg1nXU8lqcq30ur8"; // You'll need to put your Gemini API key here
//     private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;
    
//     public static String getSuggestions(int mood, String note) {
//         try {
//             String prompt = String.format(
//                 "Based on a user who reported mood level %d/5 and wrote: '%s', " +
//                 "suggest 2-3 specific activities, resources, or media that could help improve their wellbeing. " +
//                 "Format the response with bullet points and include relevant links.", 
//                 mood, note);

//             String jsonRequest = String.format("""
//                 {
//                     "contents": [{
//                         "parts": [{
//                             "text": "%s"
//                         }]
//                     }]
//                 }""", prompt);

//             HttpClient client = HttpClient.newHttpClient();
//             HttpRequest request = HttpRequest.newBuilder()
//                 .uri(URI.create(API_URL))
//                 .header("Content-Type", "application/json")
//                 .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
//                 .build();

//             HttpResponse<String> response = client.send(request, 
//                 HttpResponse.BodyHandlers.ofString());
                
//             // For now, just return the raw response
//             return response.body();
            
//         } catch (Exception e) {
//             return "Unable to get suggestions at this time: " + e.getMessage();
//         }
//     }
// }