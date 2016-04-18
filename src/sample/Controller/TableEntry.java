package sample.Controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by jatin1 on 4/17/16.
 */
public class TableEntry {
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty time = new SimpleStringProperty();
    public SimpleStringProperty price1 = new SimpleStringProperty();
    public SimpleStringProperty price2 = new SimpleStringProperty();

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
}
