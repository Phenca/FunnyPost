package funnypost.mvc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

public class WebService {

    public StringBuilder get_posts() throws IOException, SQLException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return to_json(conn);
    }

    public StringBuilder to_json(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder json = new StringBuilder();
        String input;
        while((input = in.readLine()) != null) {
            json.append(input);
        }
        in.close();
        return json;
    }
}
