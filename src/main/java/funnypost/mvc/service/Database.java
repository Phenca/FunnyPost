package funnypost.mvc.service;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import funnypost.mvc.entities.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection connection;

    public Database() {
        try {
            connection = this.connect_to_database();
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    public Connection connect_to_database() {
        String url = "jdbc:mysql://127.0.0.1:3306/funnypost";
        String driver = "com.mysql.jdbc.Driver";
        String login = "root";
        String password = "";
        try {
            Class.forName(driver);
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection(url, login, password);
            System.out.println("Connected to database !");
            return connection;
        } catch (ClassNotFoundException | SQLException err) {
            System.err.println(err.getMessage());
        }
        return null;
    }

    public List<Post> get_posts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        PreparedStatement prepared_statement = connection.prepareStatement("SELECT * FROM post");
        ResultSet queryset = prepared_statement.executeQuery();
        while (queryset.next()) {
            int id = queryset.getInt("id");
            String title = queryset.getString("title");
            String body = queryset.getString("body");
            int user = queryset.getInt("user");
            posts.add(new Post(id, title, body, user));
        }
        return posts;
    }

    public void insert_into_database(ArrayList<Post> data) throws SQLException {
        String sql = "INSERT INTO post (id, title, body, user) VALUES (?, ?, ?, ?)";
        PreparedStatement prepared_statement = connection.prepareStatement(sql);
        List<Post> posts = get_posts();
        for (int i =0; i<posts.size(); i++) {
            try {
                prepared_statement.setInt(1, data.get(i).getId());
                prepared_statement.setString(2, data.get(i).getTitle());
                prepared_statement.setString(3, data.get(i).getBody());
                prepared_statement.setInt(4, data.get(i).getUserID());
                prepared_statement.executeUpdate();
            } catch (Exception _) {}
        }
    }

    public String delete_from_database(Integer id) throws SQLException {
        String sql = "DELETE FROM post WHERE id = ?";
        PreparedStatement prepared_statement = this.connection.prepareStatement(sql);
        prepared_statement.setInt(1, id);
        if (prepared_statement.executeUpdate() == 1) {
            return "Enregistrement id = " + id + " supprimé avec succès!";
        }
        return "Cet enregistrement n'existe pas";
    }

//    public void reset_post_table() throws SQLException {
//        String sql = "DELETE FROM post";
//        PreparedStatement prepared_statement = this.connection.prepareStatement(sql);
//        prepared_statement.executeUpdate();
//        System.out.println("Deleted records successfully!");
//    }
}
