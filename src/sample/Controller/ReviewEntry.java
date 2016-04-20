package sample.Controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by C. Shih on 4/19/2016.
 */
public class ReviewEntry {

    public SimpleStringProperty good = new SimpleStringProperty();
    public SimpleStringProperty neutral = new SimpleStringProperty();
    public SimpleStringProperty bad = new SimpleStringProperty();

    public String getGood() {
        return good.get();
    }

    public String getNeutral() {
        return neutral.get();
    }

    public String getBad() {
        return bad.get();
    }
}
