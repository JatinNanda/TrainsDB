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
import java.util.regex.Pattern;

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
            String numText = cardNum.getText();
            String CVVtext = cardCVV.getText();
            if (nameStr.equals("") || numText.equals("") || expDT == null || CVVtext.equals("")) {
                error.setText("Please fill in all the fields!");
                error.setVisible(true);
                success.setVisible(false);
            } else if (numText.length() != 16 || (Pattern.matches("[0-9]+", numText) == false)) {
                success.setVisible(false);
                error.setText("Invalid card");
                error.setVisible(true);
            } else if (CVVtext.length() != 3 || (Pattern.matches("[0-9]+", CVVtext) == false)) {
                success.setVisible(false);
                error.setText("Invalid CVV");
                error.setVisible(true);
            } else {
                error.setVisible(false);
                String exp = expDT.format(formatter);
                //int num = Integer.parseInt(numText);
                int CVV = Integer.parseInt(CVVtext);
                boolean succ = Database.addCard(numText, CVV, exp, nameStr, Login.getName());
                if (!succ) {
                    success.setVisible(false);
                    error.setText("Card already added");
                    error.setVisible(true);
                } else {
                    cardRem.getItems().clear();
                    populateDropdown();
                    if (succ) success.setVisible(true);
                }
            }
        });

        removeCardButt.setOnAction(event -> {
            ArrayList<LocalDate> dates = Database.getRelevantDates((String)
                    cardRem.getValue());
            System.out.println("SIZE OF DATES" + dates.size());
            boolean blockRemove = false;
            for (LocalDate l : dates) {
                if (l.isAfter(LocalDate.now())) {
                    blockRemove = true;
                }
            }
            if (!blockRemove) {
                String cardVal = (String) cardRem.getValue();
                boolean work = Database.removeUserCard(cardVal, Login.getName());
                if (work) {
                    cardRem.getItems().clear();
                    populateDropdown();
                }
            } else {
                error.setVisible(true);
                error.setText("That card is being used!");
                error.setStyle("-fx-text-fill: red;");
            }
        });

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/MakeReservation" +
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
        ArrayList<String> cards = Database.getUserCards(Login.getName());
        for (String card : cards) {
            cardRem.getItems().add(card);
        }
    }
}
