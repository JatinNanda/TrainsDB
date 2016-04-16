package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize ComboBox
        bagSelect.getItems().addAll("0", "1", "2", "3", "4");
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

        next.setOnAction(event -> {
            error.setVisible(false);
            if (name.getText() == null || name.getText().equals("") ||
                    bagSelect
                    .getSelectionModel()
                    .getSelectedItem() == null) {
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red;");
                error.setText("Fill out all fields!");
            } else {
                //add database functionality or store information
                //switch to reservation screen
                int bagNum = Integer.parseInt((String) bagSelect
                        .getSelectionModel()
                        .getSelectedItem());
                System.out.println(bagNum);
                String passName = name.getText();
            }
        });
    }
}
