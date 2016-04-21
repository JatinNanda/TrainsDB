package sample.Model;

/**
 * Created by jatin1 on 3/23/16.
 */
public class Reserves {
    long reservationId;
    String trainNumber;
    boolean classtype;
    String departureDate;
    String name;
    int numBags;
    String departsFrom;
    String arrivesAt;
    double selectedPrice;
    double ticketPrice;

    public Reserves(String trainNumber, boolean
            classType, String departureDate, String name, int numBags,
                    String departsFrom, String arrivesAt) {
        this.trainNumber = trainNumber;
        this.classtype = classType;
        this.departureDate = departureDate;
        this.name = name;
        this.numBags = numBags;
        this.departsFrom = departsFrom;
        this.arrivesAt = arrivesAt;
    }

    public String getArrivesAt() {
        return arrivesAt;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public boolean isClasstype() {
        return classtype;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getName() {
        return name;
    }

    public int getNumBags() {
        return numBags;
    }

    public String getDepartsFrom() {
        return departsFrom;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long id) {this.reservationId = id; }

    public void setSelectedPrice(double price) {
        this.selectedPrice = price;
    }

    public double getTicketPrice() {
        return this.ticketPrice;
    }

    public double getSelectedPrice() {
        return this.selectedPrice;
    }
}
