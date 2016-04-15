package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Database;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/14/16.
 */
public class Schools implements Initializable{

    @FXML
    private Button back;

    @FXML
    private Button submit;

    @FXML
    private TextField email;

    @FXML
    private Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);
        submit.setOnAction(event -> {
            if (!Database.verifySchool(Login.getName(), email.getText())) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Invalid school email!");
            } else {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: green;");
                error.setText("Successfully verified!");
            }
        });

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/functionality" +
                                ".fxml"));
                Stage current = (Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
