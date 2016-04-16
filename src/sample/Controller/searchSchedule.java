package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Database;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by esha on 4/14/2016.
 */
public class searchSchedule implements Initializable {
    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField trainNumberText;

    @FXML
    private TextField invalid;

    private static String trainId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalid.setVisible(false);
        searchButton.setOnAction((event) -> {
            if(Database.trainExists(trainNumberText.getText())){
                try {
                    trainId = trainNumberText.getText();
                    Parent root = FXMLLoader.load(getClass().getResource
                            ("../View/viewSchedule" +
                                    ".fxml"));
                    Stage current =(Stage) backButton.getScene().getWindow();
                    current.setTitle("GT Trains Application");
                    current.setScene(new Scene(root));
                    current.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //display error message
                invalid.setVisible(true);
                invalid.setText("Invalid train Number");
                invalid.setStyle("-fx-text-fill: red;");
            }

        });

        backButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/functionality" +
                                ".fxml"));
                Stage current = (Stage) backButton.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static String getTrainId() {
        return trainId;
    }

}
