package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Database;
import sample.Model.PopularRoute;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by Raymond on 4/19/2016.
 */
public class ViewPopRotRep implements Initializable {
    @FXML
    Button back;

    @FXML
    TableView<TableEntry> table;

    @FXML
    TableColumn monthColumn;

    @FXML
    TableColumn trainNumColumn;

    @FXML
    TableColumn numResColumn;

    ObservableList backing = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Calendar c = Calendar.getInstance();
        table.setItems(backing);
        monthColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("month"));
        trainNumColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("train"));
        numResColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Integer>("numRes"));
        String monthStr;
        boolean dummy;
        ArrayList<PopularRoute> res;
        for (int i = 3; i > 0; i--) {
            monthStr = getMonth(c.get(Calendar.MONTH) - i);
            System.out.println(monthStr);
            res = Database.getPopRotRep(c.get(Calendar.MONTH) - (i-1));
            dummy = false;
            for (PopularRoute a : res) {
                TableEntry t = new TableEntry();
                System.out.println(a);
                if (dummy) {
                    System.out.println("test");
                    t.month.set("");
                } else {
                    System.out.println("test2");
                    t.month.set(monthStr);
                }
                dummy = true;
                t.train.set(a.getTrain());
                t.numRes.set(a.getCount());
                backing.add(t);
            }
        }

        back.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource
                        ("../View/managerFront" +
                                ".fxml"));
                Stage current =(Stage) back.getScene().getWindow();
                current.setTitle("GT Trains Application");
                current.setScene(new Scene(root));
                current.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

}
