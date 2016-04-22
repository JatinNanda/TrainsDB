package sample.Model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by jatin1 on 4/16/16.
 * Used to separate out math shenanigans and keep a hold of reservations
 */
public class Calculations {

    private static LinkedList<Reserves> reservations = new
            LinkedList<Reserves>();

    public static long findDuration(ArrayList<String> toProcess) {
        //assuming structure is followed
        //index 2 is departure
        //index 1 is arrival
        LocalTime d = LocalTime.parse(toProcess.get(2));
        LocalTime a = LocalTime.parse(toProcess.get(1));
        return Duration.between(d, a).toHours();
    }

    public static double getTotalPrice(boolean isStudent) {
        double sum = 0.0;
        for (Reserves r: reservations) {
            sum += r.getSelectedPrice();
            int bagCount = r.getNumBags() - 2;
            if (bagCount < 0) {
                bagCount = 0;
            }
            sum += 30 * bagCount;
        }
        if (isStudent) {
            sum *= 0.8;
        }
        return sum;
    }

    public static void generateID() {
        //assign same id to all tickets on this reservation
        long id = generateNumber();
        System.out.println(id);
        for (Reserves r: reservations) {
            r.setReservationId(id);
        }
    }

    //returns refunded price based on earliest date in the reservation
    public static double getRefundedPrice(ArrayList<LocalDate> dates, double
     base) {
        //find minimum local date
        Collections.sort(dates);
        LocalDate min = dates.get(0);
        System.out.println(String.valueOf(min));

        //time since today
        Period between = Period.between(LocalDate.now(), min);
        int diffDays = between.getDays();
        System.out.println(diffDays);

        if (diffDays > 7) {
            return base * 0.8 - 50.0;
        } else if (diffDays > 1) {
            return base * 0.5 -50.0;
        } else {
            return base;
        }
    }

    //generate random 10 digit number
    public static long generateNumber() {
        return (long)(Math.random()*100000);
    }

    public static long clear() {
        long ret = reservations.get(0).getReservationId();
        reservations = new LinkedList<Reserves>();
        return ret;
    }

    public static LinkedList<Reserves> getReservations() {
        return reservations;
    }

    public static void resetReservations() {
        reservations = new LinkedList<Reserves>();
    }

    public static void printList() {
        int i = 0;
        for (Reserves r: reservations) {
            System.out.println("index " + i + ": " + r);
        }
    }
}
