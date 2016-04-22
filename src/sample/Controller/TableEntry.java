package sample.Controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by jatin1 on 4/17/16.
 */
public class TableEntry {
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty time = new SimpleStringProperty();
    public SimpleStringProperty price1 = new SimpleStringProperty();
    public SimpleStringProperty price2 = new SimpleStringProperty();
    public SimpleStringProperty month = new SimpleStringProperty();
    public SimpleDoubleProperty revenue = new SimpleDoubleProperty();
    public SimpleIntegerProperty numRes = new SimpleIntegerProperty();
    public SimpleStringProperty train = new SimpleStringProperty();

    public String getName() {
        return name.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getPrice1() {
        return price1.get();
    }

    public String getPrice2() {
        return price2.get();
    }

    public String getMonth() { return month.get(); }

    public double getRevenue() {return revenue.get(); }

    public int getNumRes() { return numRes.get(); }

    public String getTrain() { return train.get(); }
}
