package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Calendar c = Calendar.getInstance();
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
        // to build overservable list need to create observable list object
        //ObservatbleList row =
        String month1 = getMonth(c.get(Calendar.MONTH) - 3);
        String month2 =  getMonth(c.get(Calendar.MONTH) - 2);
        String month3 =  getMonth(c.get(Calendar.MONTH) - 1);
        System.out.println(month1);
        System.out.println(month2);
        System.out.println(month3);
    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
