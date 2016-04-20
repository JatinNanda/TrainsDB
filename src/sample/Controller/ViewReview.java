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
import sample.Model.Review;

import javax.xml.crypto.Data;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by C. Shih on 4/19/2016.
 */
public class ViewReview implements Initializable {

    @FXML
    private Button back;

    @FXML
    private TableColumn goodCol;

    @FXML
    private TableColumn neutralCol;

    @FXML
    private TableColumn badCol;

    @FXML
    private TableView<ReviewEntry> table;


    ObservableList backing = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        table.setItems(backing);
        goodCol.setCellValueFactory(new PropertyValueFactory<ReviewEntry,
                String>("good"));
        neutralCol.setCellValueFactory(new PropertyValueFactory<ReviewEntry,
                String>("neutral"));
        badCol.setCellValueFactory(new PropertyValueFactory<ReviewEntry,
                String>("bad"));

        ArrayList<String> goodCom = Database.getGoodReview(searchSchedule.getTrainId());
        ArrayList<String> neutralCom = Database.getNeutralReview(searchSchedule.getTrainId());
        ArrayList<String> badCom = Database.getBadReview(searchSchedule.getTrainId());

        int goodNum = goodCom.size();
        int neutralNum = neutralCom.size();
        int badNum = badCom.size();

        int totRow = Math.max(goodNum, (Math.max(neutralNum, badNum)));

        for (int i = 0; i < totRow; i++) {
            ReviewEntry t = new ReviewEntry();

            String goodItem = "";
            String neutralItem = "";
            String badItem = "";

            if (i < goodNum) {
                goodItem = goodCom.get(i);
            }
            if (i < neutralNum) {
                neutralItem = neutralCom.get(i);
            }
            if (i < badNum) {
                badItem = badCom.get(i);
            }


            t.good.set(goodItem);
            t.neutral.set(neutralItem);
            t.bad.set(badItem);

            backing.add(t);
        }







        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/viewSchedule.fxml"));
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


