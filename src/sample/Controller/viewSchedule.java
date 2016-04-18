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
import javafx.stage.Stage;
import sample.Model.Database;
import sample.Model.Stop;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import javax.swing.text.html.ListView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by esha on 4/15/2016.
 */
public class viewSchedule implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn UserId;

    @FXML
    private TableColumn UserName;

    @FXML
    private TableColumn Active;

    private ObservableList data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            data = FXCollections.observableArrayList();
            ResultSet result = Database.getSchedule(searchSchedule.getTrainId());
//            ResultSetMetaData rsmd = result.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//            while (result.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = result.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//            }
            while (result.next()) {
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                    row.add(result.getString(i));
                    System.out.println(row);
                }
                data.add(row);
            }
            //tableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

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


    }


