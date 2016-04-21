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
 * Created by Raymond on 4/18/2016.
 */
public class ManagerFront implements Initializable{
    @FXML
    Button viewRevRep;

    @FXML
    Button viewPopRotRep;

    @FXML
    Button logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewRevRep.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/viewRevRep" +
                                ".fxml"));
                Stage current = (Stage) viewRevRep.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        viewPopRotRep.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/popularRouteReport" +
                                ".fxml"));
                Stage current = (Stage) viewPopRotRep.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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
    }
}
