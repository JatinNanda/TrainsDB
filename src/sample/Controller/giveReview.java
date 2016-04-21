package sample.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Customer;
import sample.Model.Database;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by C. Shih on 4/16/2016.
 */
public class giveReview implements Initializable{

    @FXML
    private TextField trainNum;

    @FXML
    private Button submit;

    @FXML
    private TextField comment;

    @FXML
    private TextField noTrainRating;


    @FXML
    private ComboBox rating;

    @FXML
    private Button back;


    private int ratingToInt(String rating) {
        if (rating.equals("Very Good")) {
            System.out.println("rating: 10");
            return 10;
        } else if (rating.equals("Good")) {
            System.out.println("rating: 7");
            return 7;
        } else if (rating.equals("Neutral")) {
            System.out.println("rating: 5");
            return 5;
        } else if (rating.equals("Bad")) {
            System.out.println("rating: 3");
            return 3;
        } else if (rating.equals("Very Bad")) {
            System.out.println("rating: 1");
            return 1;
        } else { // SHOULD NEVER COME HERE!!!
            return -1;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rating.getItems().addAll("Very Good", "Good", "Neutral", "Bad", "Very Bad");
        // very good = 10, good = 7, neutral = 5, bad = 3, very bad = 1

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/functionality" +
                                ".fxml"));
                Stage current =(Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        submit.setOnAction((event) -> {
            if (Database.trainExists(trainNum.getText())) {
                if (rating.getSelectionModel().getSelectedItem() != null) {
                    // puts into database, then goes back to functionality page
                    String ratingInsert = (String) rating.getSelectionModel().getSelectedItem();
                    System.out.println("rating: " + ratingInsert);
                    System.out.println("comment: " + comment.getText());
                    String insertComment = comment.getText();
                    if (comment.getText().length() < 1) {
//                        System.out.println("HEREEEE");
                        insertComment = null;
                    }
                    Database.addReview(ratingToInt((String) rating.getSelectionModel().getSelectedItem()), insertComment,
                            Login.getName(), trainNum.getText());

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource
                                ("../View/functionality" +
                                        ".fxml"));
                        Stage current = (Stage) submit.getScene().getWindow();
                        current.setTitle("GT Trains Application");
                        current.setScene(new Scene(root));
                        current.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    noTrainRating.setVisible(true);
                    noTrainRating.setText("Select Rating");
                    noTrainRating.setStyle("-fx-text-fill: red;");
                }

            } else {
                //display error message
                noTrainRating.setVisible(true);
                noTrainRating.setText("Invalid Train");
                noTrainRating.setStyle("-fx-text-fill: red;");
            }
        });
    }

}
