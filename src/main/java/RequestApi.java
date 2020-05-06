import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestApi {
    private String api_url = "http://chat.local/";

    public String sendRequest(String method_url) throws IOException {
        URL url = new URL(api_url+method_url);

        //=== Send request
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            BufferedReader br = new BufferedReader(in);
            String output;
            String json_messages = "";
            while ((output = br.readLine()) != null) {
                json_messages = json_messages + output;
                System.out.println(json_messages);
            }
            conn.disconnect();

        return json_messages;
    }

    private static byte[] createPostData(Map<String,Object> data) throws IOException {
        //=== POST data
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : data.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            return postDataBytes;
    }

    public String sendRequest(String method_url, Map<String,Object> data) throws IOException {
        URL url = new URL(api_url+method_url);

        //=== POST data
            byte[] postDataBytes = createPostData(data);

        //=== Send request
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            BufferedReader br = new BufferedReader(in);
            String output;
            String json_messages = "";
            while ((output = br.readLine()) != null) {
                json_messages = json_messages + output;
                System.out.println(json_messages);
            }
            conn.disconnect();

        return json_messages;
    }
}
