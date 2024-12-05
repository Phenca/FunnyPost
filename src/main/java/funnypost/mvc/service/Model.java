package funnypost.mvc.service;

import java.util.ArrayList;

import org.json.*;

import funnypost.mvc.entities.Post;

public class Model {
    private Database database;
    private WebService web_service;

    public Model() {
        this.database = new Database();
        this.web_service = new WebService();
    }

    public Database getDatabase() {
        return database;
    }

    public WebService getWeb_service() {
        return web_service;
    }

    public ArrayList<Post> create_posts(StringBuilder json) {
        ArrayList<Post> data = new ArrayList<>();
        JSONArray arr = new JSONArray(json.toString());
        for (int i = 0; i<arr.length(); i++) {
            JSONObject object = arr.getJSONObject(i);
            data.add(new Post(
                    object.getInt("id"),
                    object.getString("title"),
                    object.getString("body"),
                    object.getInt("userId")
            ));
        }
        return data;
    }
}
