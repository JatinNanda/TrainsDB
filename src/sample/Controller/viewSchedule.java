package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Calculations;
import sample.Model.Database;
import sample.Model.Stop;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import javax.swing.text.html.ListView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by esha on 4/15/2016.
 */
public class viewSchedule implements Initializable {

    @FXML
    private Button review;

    @FXML
    private Button back;

    @FXML
    private Button next;

    @FXML
    private javafx.scene.control.TableView<ScheduleEntry> table;

    @FXML
    private javafx.scene.control.TableColumn trainCol;

    @FXML
    private javafx.scene.control.TableColumn stationCol;

    @FXML
    private javafx.scene.control.TableColumn arrivalCol;

    @FXML
    private javafx.scene.control.TableColumn departCol;

    ObservableList backing = FXCollections.observableArrayList();
    public void initialize(URL location, ResourceBundle resources) {
        table.setItems(backing);
        trainCol.setCellValueFactory(new PropertyValueFactory<ScheduleEntry,
                String>("train"));
        stationCol.setCellValueFactory(new PropertyValueFactory<ScheduleEntry,
                String>("station"));
        arrivalCol.setCellValueFactory(new PropertyValueFactory<ScheduleEntry,
                Integer>("arrival"));
        departCol.setCellValueFactory(new PropertyValueFactory<ScheduleEntry,
                Integer>("departure"));
        int i = 1;
        for (String a: Database.getSchedule(searchSchedule.getTrainId())) {
            String[] tokens = a.split(",", -1);
            System.out.println(tokens[0]);
            System.out.println(tokens[1]);
            System.out.println(tokens[2]);
            ScheduleEntry t = new ScheduleEntry();
            if(i==1) {
                t.train.set(tokens[0]);
            }
            t.station.set(tokens[1]);
            t.arrival.set(tokens[2]);
            t.departure.set(tokens[3]);
            backing.add(t);
            i++;
        }

        review.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/viewreview1.fxml"));
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

    }


}



