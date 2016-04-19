package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Calculations;
import sample.Model.Reservation;
import sample.Model.Reserves;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/16/16.
 */
public class Bags implements Initializable {

    @FXML
    private Button back;

    @FXML
    private Button next;

    @FXML
    private TextField name;

    @FXML
    private ComboBox bagSelect;

    @FXML
    private Label error;

    @FXML
    private Label price1stclass;

    @FXML
    private Label price2ndclass;

    @FXML
    private RadioButton button1stclass;

    @FXML
    private RadioButton button2ndclass;

    private static Reserves newReservation;

    private static boolean isFirst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //radio button labels
        TableEntry t = selectDeparture.getDeparture();
        price1stclass.setText(t.getPrice1());
        price2ndclass.setText(t.getPrice2());

        //initialize ComboBox
        bagSelect.getItems().addAll("0", "1", "2", "3", "4");
        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/selectDeparture" +
                                ".fxml"));
                Stage current =(Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        next.setOnAction(event -> {
            error.setVisible(false);
            if (name.getText().equals("") ||
                    bagSelect
                    .getSelectionModel()
                    .getSelectedItem() == null || (!button1stclass.isSelected
                    () && !button2ndclass.isSelected())) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Fill out all fields!");
            } else {
                //add database functionality or store information
                //switch to reservation screen
                int bagNum = Integer.parseInt((String) bagSelect
                        .getSelectionModel()
                        .getSelectedItem());
                String passName = name.getText();

                //true if 1st class, false if 2nd class
                isFirst = button1stclass.isSelected();
                System.out.println(isFirst);

                //get more info
                ArrayList<String> resInfo = searchTrain.getMatches().get(
                selectDeparture
                        .getMatchingIndex());

                String completeDate = resInfo.get(5) + "\n" + selectDeparture
                .getTime();
                System.out.println(completeDate);
                newReservation = new Reserves(resInfo.get(0),
                        isFirst, completeDate, passName, bagNum, resInfo
                        .get(7), resInfo.get(6));

                Reserves r = newReservation;
                String price = Bags.getSelectedPrice();
                price = price.substring(1, price.length());
                r.setSelectedPrice(Double.parseDouble(price));
                Calculations.getReservations().add(r);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource
                            ("../View/MakeReservation" +
                                    ".fxml"));
                    Stage current =(Stage) back.getScene().getWindow();
                    current.setTitle("GT Trains Application");
                    current.setScene(new Scene(root));
                    current.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Reserves getReservation() {
        return newReservation;
    }

    public static String getSelectedPrice() {
        TableEntry t = selectDeparture.getDeparture();
        return isFirst ? t.getPrice1() : t.getPrice2();
    }
}
