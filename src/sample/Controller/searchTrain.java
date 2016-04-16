package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.Model.Database;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by esha on 4/15/2016.
 */
public class searchTrain implements Initializable {
    @FXML
    private ComboBox departBox;
    ObservableList<String> stations = FXCollections.observableArrayList("yellow", "Green", "ehite");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departBox.setItems(stations);

    }

}
