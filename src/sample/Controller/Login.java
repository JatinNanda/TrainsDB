package sample.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private TextField password;

    @FXML
    private TextField invalid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalid.setVisible(false);
        login.setOnAction((event) -> {
            if (Database.loginAttempt(username.getText(), password.getText()
            )) {
                //success
                //load new screen
                System.out.println("Succesfully logged in as " + username
                        .getText());
            } else {
                //display error message
                invalid.setVisible(true);
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
}
