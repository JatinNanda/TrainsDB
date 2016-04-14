package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    TextField pass;

    @FXML
    TextField confirmPass;

    @FXML
    TextField email;

    @FXML
    Button register;

    @FXML
    Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);
        register.setOnAction((event) -> {
            Customer c = Database.register(username.getText(), email
                            .getText(),
                    pass.getText(),
                    confirmPass.getText());
            if (c == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
            } else {
                System.out.println("SUCCESS");
            }
        });
    }
}
