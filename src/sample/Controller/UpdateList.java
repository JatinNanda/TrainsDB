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
import sample.Model.Reserves;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
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
    private TableView<TableEntryReserve> table1;

    ObservableList backing1 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        LocalDate departure = LocalDate.parse((toAdd.getTime().substring(10)));
        verify.setOnAction(event -> {
            if (combo.getValue() == null) {
                error.setVisible(true);
                error.setText("Please select a date!");
                error.setStyle("-fx-text-fill: red;");
            } else {
                //calculate date between
//                long diff = departure.getTime() - LocalDate.now().get);
//                backing1.add(UpdateReservation.getEntry());
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
                        ("../View/UpdateReservation" +
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
