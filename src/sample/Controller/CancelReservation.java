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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/21/16.
 */
public class CancelReservation implements Initializable {
    @FXML
    private Button back;

    @FXML
    private Button submit;

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
    private Label error;

    @FXML
    private Label totalCost;

    @FXML
    private Label date;

    @FXML
    private Label refund;

    @FXML
    private TableView<TableEntryReserve> table;

    ObservableList backing = FXCollections.observableArrayList();

    private static double resPrice;
    private static double refundPrice;


    public void initialize(URL location, ResourceBundle resources) {
        submit.setDisable(false);
        date.setVisible(true);
        date.setText(String.valueOf(LocalDate.now()));
        totalCost.setVisible(true);
        resPrice = Database.getReservationPrice(CancelID.getID());
        totalCost.setText("$" + resPrice);

        //refund calculations
        ArrayList<LocalDate> departures = Database.getAllDepartures(CancelID
                        .getID());
        refundPrice = Calculations.getRefundedPrice(departures,
                resPrice);
        if (refundPrice < 0) {
            refundPrice = 0;
        }

        refund.setVisible(true);
        refund.setText("$" + refundPrice);


        table.setItems(backing);
        table.setSelectionModel(null);

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

        //repopulate with every ticket tied to this reservation
        for (Reserves toAdd : CancelID.getToDisplay()) {
            //add prices back
            toAdd.setSelectedPrice(Database.getClassPrice(toAdd.isClasstype(),
                    toAdd.getTrainNumber()) / 10.0);

            //check for past date
            LocalDate toCheck = LocalDate.parse(toAdd.getDepartureDate()
                    .substring(0, 10));
            if (toCheck.compareTo(LocalDate.now()) >= 0) {
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
        }

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/CancelID" +
                                ".fxml"));
                Stage current = (Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        submit.setOnAction(event -> {
            if (refundPrice == resPrice) {
                error.setText("Too late to cancel a reservation!");
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
            } else {
                error.setVisible(true);
                error.setText("Successfully cancelled the reservation");
                error.setStyle("-fx-text-fill: green;");
                //update db
                Database.cancelReservation(refundPrice, CancelID.getID());
            }
        });

    }
}
