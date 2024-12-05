package funnypost.mvc;

import funnypost.mvc.entities.Post;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import funnypost.mvc.service.Model;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;


public class Controller {
    private Model model;

    @FXML
    private TableView<Post> post_table;

    @FXML
    private TableColumn<Post, Integer> id_column = new TableColumn<>("id");

    @FXML
    private TableColumn<Post, String> title_column = new TableColumn<>("title");

    @FXML
    private TableColumn<Post, String> body_column = new TableColumn<>("body");

    @FXML
    private TextField id_to_suppress;

    @FXML
    private Label error_text;

    @FXML
    public void initialize() throws SQLException, IOException {
        this.model = new Model();
        populate();
    }

    public void init_table(ActionEvent event) throws IOException, SQLException {    
        StringBuilder json = this.model.getWeb_service().get_posts();
        ArrayList<Post> posts = this.model.create_posts(json);
        this.model.getDatabase().insert_into_database(posts);
        populate();
    }


    public void suppress_post(ActionEvent event) {
        try {
            check_field(id_to_suppress, id_to_suppress.getText());
            String db_response = this.model.getDatabase().delete_from_database(Integer.valueOf(id_to_suppress.getText()));
            error_text.setText(db_response);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(action -> {
                try {
                    populate();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
            pause.play();
        } catch (IllegalArgumentException | SQLException err) {
            error_text.setText(err.getMessage());
        }
    }


    public void populate() throws SQLException, IOException {
        error_text.setText("");
        set_table_view();
    }

    public void set_table_view() throws SQLException {
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        title_column.setCellValueFactory(new PropertyValueFactory<>("title"));
        body_column.setCellValueFactory(new PropertyValueFactory<>("body"));
        ObservableList<Post> list = FXCollections.observableArrayList(this.model.getDatabase().get_posts());
        post_table.setItems(list);
    }

    private void check_field(TextField input, String input_name) {
        if (input.getText().isEmpty()) {
            throw new IllegalArgumentException("Le champ ne doit pas être vide");
        }
        if (!input.getText().matches("[0-9]*")) {
            throw new IllegalArgumentException("Le saisie du champ '" + input_name + "' doit être un nombre entier");
        }
        if (!(Float.parseFloat(input.getText()) >= 1 && Float.parseFloat(input.getText()) <= post_table.getItems().size())) {
            throw new IllegalArgumentException("Cet identifiant de post n'existe pas");
        }
    }

}