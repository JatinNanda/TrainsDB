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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Model.Calculations;
import sample.Model.Database;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by esha on 4/15/2016.
 */
public class searchTrain implements Initializable {
    @FXML
    private ComboBox departsBox;

    @FXML
    private ComboBox arrivesBox;

    @FXML
    private DatePicker dateBox;

    @FXML
    private Button back;

    @FXML
    private Button find;

    @FXML
    private Label error;

    private static ArrayList<ArrayList<String>> matches;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //populate comboBoxes
        ArrayList<String> stations = Database.getStations();
        departsBox.getItems().addAll(stations);
        arrivesBox.getItems().addAll(stations);

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

        find.setOnAction(event -> {
            error.setVisible(false);
           if (departsBox.getSelectionModel().isEmpty() || arrivesBox
                   .getSelectionModel().isEmpty() || dateBox.getValue() ==
                   null) {
               error.setVisible(true);
               error.setStyle("-fx-text-fill: red;");
               error.setText("Fill out all the fields!");
           } else if (dateBox.getValue().compareTo(LocalDate.now()) <= 0) {
               error.setVisible(true);
               error.setStyle("-fx-text-fill: red;");
               error.setText("Cannot select a date from the past!");
           } else {
               //gets rid of the parenthesis lol
               String departs = ((String) departsBox.getSelectionModel()
                       .getSelectedItem()).replaceAll("\\(.*\\)", "");
               String arrives = ((String) arrivesBox.getSelectionModel()
                       .getSelectedItem()).replaceAll("\\(.*\\)", "");
               String date = dateBox.getValue().toString();
               matches = Database.findRequestedTrains(departs.trim(), arrives,
                       date);
               if (matches.isEmpty()) {
                   error.setVisible(true);
                   error.setStyle("-fx-text-fill: red;");
                   error.setText("No trains found");
               } else {
                   for (ArrayList<String> a : matches) {
                       System.out.println(Calculations.findDuration(a));
                       System.out.println("*************");
                       for (String s: a) {
                           System.out.println(s);
                       }
                   }
                   try {
                       Parent root = FXMLLoader.load(getClass().getResource
                               ("../View/selectDeparture" +
                                       ".fxml"));
                       Stage current = (Stage) back.getScene().getWindow();
                       current.setTitle("GT Trains Application");
                       current.setScene(new Scene(root));
                       current.show();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }
        });
    }

    //this method returns a list of a list of attributes for each train that
    // matched the customer's request
    //the indices in the returning lists will be as follows:
    //0: Train Number
    //1: ArrivalTime
    //2: DepartureTime
    //3: 1st class price
    //4: second class price
    //5: Date requested
    //6: Arrival Location
    //7: Departure Location
    public static ArrayList<ArrayList<String>> getMatches() {
        return matches;
    }

}
