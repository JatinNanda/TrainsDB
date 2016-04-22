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
import org.omg.CORBA.TIMEOUT;
import sample.Model.Customer;
import sample.Model.Database;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/14/16.
 */
public class Register implements Initializable {

    @FXML
    TextField username;

    @FXML
    PasswordField pass;

    @FXML
    PasswordField confirmPass;

    @FXML
    TextField email;

    @FXML
    Button register;

    @FXML
    Button back;

    @FXML
    Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);
        error.setStyle("-fx-text-fill: red;");
        register.setOnAction((event) -> {
            String userStr = username.getText();
            String emailStr = email.getText();
            String passStr = pass.getText();
            String confirmPassStr = confirmPass.getText();
            if (userStr == null || emailStr == null || passStr == null ||
                    confirmPassStr ==
                            null) {
                error.setVisible(true);
                error.setText("Please fill in all the fields!");
            } else if (!passStr.equals(confirmPassStr)) {
                error.setVisible(true);
                error.setText("Make sure the passwords match!");
            } else {
                Customer c = Database.register(userStr, emailStr, passStr,
                        confirmPassStr);
                if (c == null) {
                    error.setVisible(true);
                    error.setText("Username or email is already in use");
                } else {
                    error.setVisible(true);
                    error.setStyle("-fx-text-fill: green;");
                    error.setText("SUCCESS");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource
                                ("../View/login" +
                                        ".fxml"));
                        Stage current = (Stage) register.getScene().getWindow();
                        current.setTitle("GT Trains Application");
                        current.setScene(new Scene(root));
                        current.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/login" +
                                ".fxml"));
                Stage current = (Stage) register.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
