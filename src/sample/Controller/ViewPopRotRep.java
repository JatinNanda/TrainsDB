package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Raymond on 4/19/2016.
 */
public class ViewPopRotRep implements Initializable {
    @FXML
    Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/managerFront" +
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
