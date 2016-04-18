package sample.Model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by jatin1 on 4/16/16.
 * Used to separate out math shenanigans
 */
public class Calculations {
    public static long findDuration(ArrayList<String> toProcess) {
        //assuming structure is followed
        //index 2 is departure
        //index 1 is arrival
        LocalTime d = LocalTime.parse(toProcess.get(2));
        LocalTime a = LocalTime.parse(toProcess.get(1));
        return Duration.between(d, a).toHours();
    }
}
