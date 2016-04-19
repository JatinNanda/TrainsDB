package sample.Controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by esha on 4/18/2016.
 */
public class ScheduleEntry {
    public SimpleStringProperty train = new SimpleStringProperty();
    public SimpleStringProperty station = new SimpleStringProperty();
    public SimpleStringProperty arrival = new SimpleStringProperty();
    public SimpleStringProperty departure = new SimpleStringProperty();

    public String getTrain() {
        return train.get();
    }

    public String getStation() {
        return station.get();
    }

    public String getArrival () {
        return arrival.get();
    }

    public String getDeparture() {
        return departure.get();
    }
}
