package sample.Model;

import java.time.LocalTime;

/**
 * Created by jatin1 on 3/23/16.
 */
public class Stop {
    String trainNumber;
    String stationName;
    LocalTime arrivalTime;
    LocalTime departureTime;

    public Stop(String trainNumber, String stationName, LocalTime
            arrivalTime, LocalTime departureTime) {
        this.trainNumber = trainNumber;
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

}
