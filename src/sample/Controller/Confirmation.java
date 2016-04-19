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
import sample.Model.Calculations;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/19/16.
 */
public class Confirmation implements Initializable {

    @FXML
    private Button back;

    @FXML
    private TextField resID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resID.setText("" + Calculations.clear());

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/functionality" +
                                ".fxml"));
                Stage current =(Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
