package sample.Controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by jatin1 on 4/17/16.
 */
public class TableEntryReserve {
    public SimpleStringProperty trainName = new SimpleStringProperty();
    public SimpleStringProperty time = new SimpleStringProperty();
    public SimpleStringProperty departs = new SimpleStringProperty();
    public SimpleStringProperty arrives = new SimpleStringProperty();
    public SimpleStringProperty classType = new SimpleStringProperty();
    public SimpleStringProperty price = new SimpleStringProperty();
    public SimpleStringProperty bags = new SimpleStringProperty();
    public SimpleStringProperty name = new SimpleStringProperty();


    public String getTrainName() {
        return trainName.get();
    }

    public String getName() {
        return name.get();
    }

    public String getTime() {
        return time.get();
    }
    public String getDeparts() {
        return departs.get();
    }

    public String getArrives() {
        return arrives.get();
    }


    public String getPrice() {
        return price.get();
    }

    public String getClassType() {
        return classType.get();
    }


    public String getBags() {
        return bags.get();
    }
}
