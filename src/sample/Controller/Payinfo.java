package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Database;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * Created by Raymond on 4/16/2016.
 */
public class Payinfo implements Initializable{
    @FXML
    TextField cardName;

    @FXML
    TextField cardNum;

    @FXML
    DatePicker expDate;

    @FXML
    TextField cardCVV;

    @FXML
    Button addCardButt;

    @FXML
    Button removeCardButt;

    @FXML
    Button back;

    @FXML
    Label error;

    @FXML
    Label success;

    @FXML
    Label removed;

    @FXML
    ChoiceBox cardRem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        error.setVisible(false);
        success.setVisible(false);
        //create the list on init
        /*
        ArrayList<Integer> cards = Database.getUserCards(Login.getName());
        for (int card : cards) {
            cardRem.getItems().add(card);
        }*/
        populateDropdown();
        //add card to database
        addCardButt.setOnAction(event -> {
            String nameStr = cardName.getText();
            LocalDate expDT = expDate.getValue();
            String exp = expDT.format(formatter);
            String numText = cardNum.getText();
            String CVVtext = cardCVV.getText();
            if (nameStr == null || numText == null || exp == null || CVVtext == null) {
                error.setVisible(true);
                success.setVisible(false);
                error.setText("Please fill in all the fields!");
            } else {
                error.setVisible(false);
                int num = Integer.parseInt(numText);
                int CVV = Integer.parseInt(CVVtext);
                boolean succ = Database.addCard(num, CVV, exp, nameStr, Login.getName());
                cardRem.getItems().clear();
                populateDropdown();
                if (succ) success.setVisible(true);
            }
        });

        removeCardButt.setOnAction(event -> {
            int cardVal = (int)cardRem.getValue();
            boolean work = Database.removeUserCard(cardVal, Login.getName());
            if (work) {
                cardRem.getItems().clear();
                populateDropdown();
            }
        });

        //THIS DIRECTORY NEEDS TO BE CHANGED TO WHATEVER THE NAME IS MADE
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
    public void populateDropdown() {
        ArrayList<Integer> cards = Database.getUserCards(Login.getName());
        for (int card : cards) {
            cardRem.getItems().add(card);
        }
    }
}
