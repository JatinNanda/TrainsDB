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

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.ResourceBundle;



/**
 * Created by Raymond on 4/19/2016.
 */
public class ViewRevRep implements Initializable {
    @FXML
    Button back;

    @FXML
    TableView<TableEntry> table;

    @FXML
    TableColumn monthColumn;

    @FXML
    TableColumn revenueColumn;

    ObservableList backing = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Calendar c = Calendar.getInstance();
        table.setItems(backing);
        monthColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                String>("month"));
        revenueColumn.setCellValueFactory(new PropertyValueFactory<TableEntry,
                Double>("revenue"));
        String monthStr;
        double rev;
        for (int i =  3; i > 0; i--) {
            monthStr = getMonth(c.get(Calendar.MONTH) - i);
            rev = Database.getMonthRev(c.get(Calendar.MONTH) - (i - 1));
            TableEntry t = new TableEntry();
            t.month.set(monthStr);
            t.revenue.set(rev);
            backing.add(t);
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

        /*String month1 = getMonth(c.get(Calendar.MONTH) - 3);
        String month2 =  getMonth(c.get(Calendar.MONTH) - 2);
        String month3 =  getMonth(c.get(Calendar.MONTH) - 1);
        double rev1 = Database.getMonthRev(c.get(Calendar.MONTH) - 2);
        double rev2 = Database.getMonthRev(c.get(Calendar.MONTH) - 1);
        double rev3 = Database.getMonthRev(c.get(Calendar.MONTH));*/

    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
