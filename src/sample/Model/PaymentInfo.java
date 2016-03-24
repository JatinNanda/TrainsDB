package sample.Model;

/**
 * Created by jatin1 on 3/23/16.
 */
public class PaymentInfo {
    private int cardnumber;
    private int cvv;
    private String expdate;
    private String name;
    private String username;

    public PaymentInfo(int cardnumber, int cvv, String expdate, String name,
     String username) {
        this.cardnumber = cardnumber;
        this.cvv = cvv;
        this.expdate = expdate;
        this.name = name;
        this.username = username;
    }
}
