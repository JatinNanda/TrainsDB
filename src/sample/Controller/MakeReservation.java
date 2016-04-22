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
import sample.Model.Calculations;
import sample.Model.Database;
import sample.Model.Reserves;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;


/**
 * Created by jatin1 on 4/18/16.
 */


public class MakeReservation implements Initializable {

    @FXML
    private Button back;

    @FXML
    private Button submit;

    @FXML
    private Button addCard;

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
    private ComboBox combo;

    @FXML
    private Button again;

    @FXML
    private Button remove;

    @FXML
    private Label discount;

    @FXML
    private Label cost;

    @FXML
    private Label error;

    @FXML
    private TableView<TableEntryReserve> table;

    private static String cardNum;

    ObservableList backing = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        combo.getItems().addAll(Database.getUserCards(Login.getName()));

        table.setItems(backing);
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

        NumberFormat f = NumberFormat.getCurrencyInstance();

        //repopulate with every reservation
        for (Reserves toAdd: Calculations.getReservations()) {
            TableEntryReserve t = new TableEntryReserve();
            t.trainName.set(toAdd.getTrainNumber());
            t.time.set(toAdd.getDepartureDate());
            t.departs.set(toAdd.getDepartsFrom());
            t.arrives.set(toAdd.getArrivesAt());
            t.classType.set(toAdd.isClasstype() ? "First Class" : "Second Class");
            t.bags.set("" + toAdd.getNumBags());
            t.name.set(toAdd.getName());

            String formattedPrice = f.format(Double.valueOf(toAdd
                    .getSelectedPrice()));
            t.price.set(formattedPrice);

            backing.add(t);
        }
        boolean isStudent = Database.checkStudent(Login.getName());
        if (isStudent) {
            discount.setText("The student discount is being applied (20% " +
                    "off)!");
        } else {
            discount.setText("No student discount applied!");
        }

        cost.setText(f.format((Calculations.getTotalPrice(isStudent))));



        addCard.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/payinfo.fxml"));
                Stage current = (Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/selectDeparture.fxml"));
                Stage current = (Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        again.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/searchTrain.fxml"));
                Stage current = (Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        remove.setOnAction(event -> {
            error.setVisible(false);
            if (table.getSelectionModel().isEmpty()) {
                error.setVisible(true);
                error.setText("Select something to remove!");
                error.setStyle("-fx-text-fill: red;");
            } else {
                TableEntryReserve toRemove = (TableEntryReserve) table
                        .getSelectionModel().getSelectedItem();
                int i = table.getSelectionModel().getSelectedIndex();
                backing.remove(toRemove);
                //reconfigures backing array
                Calculations.getReservations().remove(i);
                //recalculate price
                cost.setText(f.format((Calculations.getTotalPrice(isStudent))));

            }
        });

        submit.setOnAction(event -> {
            Calculations.printList();
            error.setVisible(false);
            if (table.getItems().isEmpty()) {
                error.setVisible(true);
                error.setText("Add at least one reservation!");
                error.setStyle("-fx-text-fill: red;");
            } else if (combo.getSelectionModel().isEmpty()) {
                error.setVisible(true);
                error.setText("Select a card to pay with!");
                error.setStyle("-fx-text-fill: red;");
            } else {
                error.setVisible(true);
                //make the transaction
                cardNum = Database.getCardNumber((String) combo
                        .getSelectionModel()
                        .getSelectedItem(), Login.getName());
                Calculations.generateID();
                if (Database.addSelectedReservations(Calculations
                        .getReservations(), Double.parseDouble(cost
                        .getText().substring(1, cost.getText().length())))) {
                    error.setStyle("-fx-text-fill: green;");
                    error.setText("Success!!");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource
                                ("../View/confirmation" +
                                        ".fxml"));
                        Stage current =(Stage) back.getScene().getWindow();
                        current.setTitle("GT Trains Application");
                        current.setScene(new Scene(root));
                        current.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    error.setStyle("-fx-text-fill: red;");
                    error.setText("Remove duplicate tickets!");
                }
            }
        });
    }

    public static String getCard() {
        return cardNum;
    }
}
