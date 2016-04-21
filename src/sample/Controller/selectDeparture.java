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

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/17/16.
 */
public class selectDeparture implements Initializable {

    @FXML
    private Button back;

    @FXML
    private Button next;

    @FXML
    private TableView<TableEntry> table;

    @FXML
    private TableColumn nameColumn;

    @FXML
    private TableColumn timeColumn;

    @FXML
    private TableColumn price1column;

    @FXML
    private TableColumn price2column;

    @FXML
    private Label error;

    private static int matchingIndex;

    private static TableEntry toSave;

    ObservableList backing = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        table.setItems(backing);
        nameColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("time"));
        price1column.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("price1"));
        price2column.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("price2"));

        for (ArrayList<String> a: searchTrain.getMatches()) {
            TableEntry t = new TableEntry();
            t.name.set(a.get(0));
            t.time.set(a.get(2) + " - " + a.get(1) + "\n" + Calculations
                    .findDuration(a) + " " +
                    "Hours");
            //formatting money
            NumberFormat f = NumberFormat.getCurrencyInstance();
            t.price1.set(f.format(Double.valueOf(a.get(3)) / 10));
            t.price2.set(f.format(Double.valueOf(a.get(4)) / 10));

            //adds to table
            backing.add(t);
        }

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/searchTrain" +
                                ".fxml"));
                Stage current = (Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        next.setOnAction(event -> {
            error.setVisible(false);
            toSave = table.getSelectionModel().getSelectedItem();
            if (toSave == null) {
                error.setVisible(true);
                error.setText("Select a departure!");
                error.setStyle("-fx-text-fill: red;");
            } else {
                try {
                    int i = 0;
                    for (ArrayList<String> l : searchTrain.getMatches()) {
                        if ((l.get(0).equals(toSave.getName()))) {
                            matchingIndex = i;
                            System.out.println(l.get(0));
                            break;
                        }
                        i++;
                    }
                    Parent root = FXMLLoader.load(getClass().getResource
                            ("../View/bags" +
                                    ".fxml"));
                    Stage current = (Stage) back.getScene().getWindow();
                    current.setTitle("GT Trains Application");
                    current.setScene(new Scene(root));
                    current.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static TableEntry getDeparture() {
        return toSave;
    }
    //index in matches that has more data about the reservation
    public static String getTime() {
        return toSave.getTime();
    }
    public static int getMatchingIndex() {
        return matchingIndex;
    }
}
