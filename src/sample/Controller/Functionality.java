package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/14/16.
 */
public class Functionality implements Initializable {

    @FXML
    Button schedule;

    @FXML
    Button newReservation;

    @FXML
    Button updateReservation;

    @FXML
    Button cancelReservation;

    @FXML
    Button review;

    @FXML
    Button school;

    @FXML
    Button logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logout.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/login" +
                                ".fxml"));
                Stage current =(Stage) logout.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        school.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/schools" +
                                ".fxml"));
                Stage current =(Stage) logout.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
