package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Database;
import sample.Model.Reserves;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jatin1 on 4/19/16.
 */


public class CancelID implements Initializable {

    @FXML
    private TextField resID;

    @FXML
    private Button submit;

    @FXML
    private Button back;

    @FXML
    private Label error;

    private static ArrayList<Reserves> toDisplay;

    private static String resId;

    private static double totPrice;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setVisible(false);

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

        submit.setOnAction(event -> {
            resId = resID.getText();
            toDisplay = Database.getSelectedReservations
                    (resId);
            totPrice = Database.getReservationPrice(resId);
            if (toDisplay.isEmpty()) {
                error.setVisible(true);
                error.setText("Either this reservation is cancelled, it " +
                        "doesn't exist under your name, or it has already " +
                        "happened!");
                error.setStyle("-fx-text-fill: red;");
            } else {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource
                            ("../View/CancelReservation" +
                                    ".fxml"));
                    Stage current = (Stage) submit.getScene().getWindow();
                    current.setTitle("GT Trains Application");
                    current.setScene(new Scene(root));
                    current.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String getID() {
        return resId;
    }
    public static ArrayList<Reserves> getToDisplay() {
        return toDisplay;
    }
    public static double getTotPrice() {
        return totPrice;
    }
}
