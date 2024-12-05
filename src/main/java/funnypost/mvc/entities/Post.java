package funnypost.mvc.entities;

public class Post {
    private int id;
    private String title;
    private String body;
    private int user_id;

    public Post(int id, String title, String body, int user_id) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }


//    @Override
//    public String toString() {
//        return id + ";" + title + ";" + body ;
//    }
}
