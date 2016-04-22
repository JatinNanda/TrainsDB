package sample.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Calculations;
import sample.Model.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField invalid;

    //current customer logged in - not sure if necessary - use getter method
    // to get
    private static String currentName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalid.setVisible(false);
        Calculations.resetReservations();
        login.setOnAction((event) -> {
            if (Database.managerLoginAttempt(username.getText(), password
                    .getText())) {
                currentName = username.getText();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource
                            ("../View/managerFront" +
                                    ".fxml"));
                    Stage current = (Stage) register.getScene().getWindow();
                    current.setTitle("GT Trains Application");
                    current.setScene(new Scene(root));
                    current.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (Database.loginAttempt(username.getText(), password.getText()
            )) {
                //success
                //load new screen
                currentName = username.getText();
                invalid.setVisible(true);
                invalid.setText("Succesfully logged in!");
                invalid.setStyle("-fx-text-fill: green;");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource
                            ("../View/functionality" +
                                    ".fxml"));
                    Stage current = (Stage) register.getScene().getWindow();
                    current.setTitle("GT Trains Application");
                    current.setScene(new Scene(root));
                    current.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //display error message
                invalid.setVisible(true);
                invalid.setText("Invalid username or password");
                invalid.setStyle("-fx-text-fill: red;");
            }
        });
        register.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/register" +
                                ".fxml"));
                Stage current =(Stage) register.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static String getName() {
        return currentName;
    }
}
