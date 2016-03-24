package sample.Model;

/**
 * Created by jatin1 on 3/23/16.
 */
public class Reservation {
    private int reservationid;
    private boolean isCancelled;
    private String username;
    private int cardNumber;

    public Reservation(int reservationid, boolean isCancelled, String
            username, int cardNumber) {
        this.reservationid = reservationid;
        this.isCancelled = isCancelled;
        this.username = username;
        this.cardNumber = cardNumber;
    }
}
