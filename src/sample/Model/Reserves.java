package sample.Model;

import java.time.LocalDate;

/**
 * Created by jatin1 on 3/23/16.
 */
public class Reserves {
    int reservationId;
    String trainNumber;
    boolean classtype;
    LocalDate departureDate;
    String name;
    int numBags;
    String departsFrom;
    String arrivesAt;

    public Reserves(int reservationId, String trainNumber, boolean
            classType, LocalDate departureDate, String name, int numBags,
                    String departsFrom, String arrivesAt) {
        this.reservationId = reservationId;
        this.trainNumber = trainNumber;
        this.classtype = classType;
        this.departureDate = departureDate;
        this.name = name;
        this.numBags = numBags;
        this.departsFrom = departsFrom;
        this.arrivesAt = arrivesAt;
    }
}
