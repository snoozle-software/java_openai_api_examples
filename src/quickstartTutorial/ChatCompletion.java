package quickstartTutorial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatCompletion {

    public static void main(String[] args) {
        try {
            
            // OpenAI API endpoint for chat completions
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            
            // Establish connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            // Set your OpenAI API key as authorization
            // You can get your OPENAI_API_KEY at https://platform.openai.com/api-keys
            conn.setRequestProperty("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"));
            conn.setDoOutput(true);

            // JSON data to send in the request
            String data = "{\"model\": \"gpt-3.5-turbo\",\"messages\": "
            		+ "[{\"role\": \"system\",\"content\": \"You are a poetic assistant, skilled in explaining complex programming concepts with creative flair.\"},"
            		+ "{\"role\": \"user\",\"content\": \"Compose a poem that explains the concept of recursion in programming.\"}]}";

            // Send data in the request body
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            // Check HTTP response code
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // Read response from server
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            // Disconnect connection
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
