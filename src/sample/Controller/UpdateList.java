package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Database;
import sample.Model.Reserves;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/19/16.
 */
public class UpdateList implements Initializable {
    @FXML
    private Button another;

    @FXML
    private Button back;

    @FXML
    private Button verify;

    @FXML
    private TableColumn timeCol;

    @FXML
    private TableColumn departsCol;

    @FXML
    private TableColumn arrivesCol;

    @FXML
    private TableColumn classCol;

    @FXML
    private TableColumn priceCol;

    @FXML
    private TableColumn bagCol;

    @FXML
    private TableColumn passengerCol;

    @FXML
    private TableColumn trainCol;

    @FXML
    private TableView<TableEntryReserve> table;

    ObservableList backing = FXCollections.observableArrayList();

    @FXML
    private TableColumn timeCol1;

    @FXML
    private TableColumn departsCol1;

    @FXML
    private TableColumn arrivesCol1;

    @FXML
    private TableColumn classCol1;

    @FXML
    private TableColumn priceCol1;

    @FXML
    private TableColumn bagCol1;

    @FXML
    private TableColumn passengerCol1;

    @FXML
    private TableColumn trainCol1;

    @FXML
    private Label error;

    @FXML
    private DatePicker combo;

    @FXML
    private Label costUpdated;

    @FXML
    private TableView<TableEntryReserve> table1;

    ObservableList backing1 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verify.setDisable(false);
        table.setItems(backing);
        table1.setItems(backing1);
        trainCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("trainName"));
        departsCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("departs"));
        arrivesCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("arrives"));
        priceCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("price"));
        timeCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("time"));
        classCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("classType"));
        bagCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("bags"));
        passengerCol.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("name"));
        trainCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("trainName"));
        departsCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("departs"));
        arrivesCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("arrives"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("price"));
        timeCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("time"));
        classCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("classType"));
        bagCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("bags"));
        passengerCol1.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("name"));
        TableEntryReserve toAdd = UpdateReservation.getEntry();
        backing.add(toAdd);
        String restOfTime = toAdd.getTime().substring(10);
        System.out.println((toAdd.getTime().substring(0, 10)));
        LocalDate departure = LocalDate.parse(toAdd.getTime().substring(0,
                10));
        verify.setOnAction(event -> {
            if (combo.getValue() == null) {
                error.setVisible(true);
                error.setText("Please select a date!");
                error.setStyle("-fx-text-fill: red;");
            } else {
                //calculate days till train
                Period between = Period.between(LocalDate.now(), departure);
                int diffDays = between.getDays();
                System.out.println("Diff:" + diffDays);
                LocalDate selected = combo.getValue();
                //if more than one day before and the new date is not in the
                // past
                if (selected.compareTo(departure) == 0) {
                    error.setVisible(true);
                    error.setText("Can't select the same date!");
                    error.setStyle("-fx-text-fill: red;");
                } else if (diffDays > 1 && (selected.compareTo(LocalDate.now
                        ()) >
                        0)) {
                    costUpdated.setVisible(true);
                    //database stuff here
                    //it's okay to update
                    error.setVisible(true);
                    error.setText("Updated successfully!");
                    error.setStyle("-fx-text-fill: green;");
                    TableEntryReserve updated = UpdateReservation.getEntry();
                    String newTime = String.valueOf(selected) + restOfTime;
                    updated.time.set(newTime);
                    backing1.add(updated);
                    //update the cost
                    double newCost = (UpdateID.getTotPrice() + 50.00);
                    costUpdated.setText("$" + newCost);
                    String trainName = updated.getTrainName();
                    //update database to reflect
                    Database.updateReservationPriceAndTime(newCost, newTime,
                            UpdateID.getID(), trainName);
                    verify.setDisable(true);
                } else {
                    error.setVisible(true);
                    error.setText("Cannot update a day before the " +
                            "reservation/make a reservation in the past");
                    error.setStyle("-fx-text-fill: red;");
                }
            }
        });

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

        another.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/UpdateID" +
                                ".fxml"));
                Stage current = (Stage) another.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
