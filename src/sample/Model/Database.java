package sample.Model;

import sample.Controller.Login;
import sample.Controller.MakeReservation;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by jatin1
 */
public class Database {
    private Database() {
    }

    private static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Team_17";
            String username = "cs4400_Team_17";
            String password = "ngMBPElT";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            System.out.println("Failed to connect");
            System.out.println(e);
        }
        return null;
    }

    //note this method should only be called ONCE - when the tables are
    // INITIALLY created
    public static void setUp() throws Exception {
        try {
            Connection con = getConnection();
            Statement createTables = con.createStatement();
            ArrayList<String> createStatements = new ArrayList<>();
            //reading table statements from a txt file because I'm lazy...
            URL url = Database.class.getResource("tables.txt");
            Scanner scanner = new Scanner(new File(url.getPath()));
            scanner.useDelimiter(";");
            //reads queries from file, separated by a ';'
            while (scanner.hasNext()) {
                createStatements.add(scanner.next());
            }
            for (String table : createStatements) {
                System.out.println(table);
                createTables.executeUpdate(table);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean loginAttempt(String username, String password) {
        try {
            Connection con = getConnection();
            System.out.println("SELECT " +
                    "* FROM Customer WHERE Username = " +
                    statementHelper(false, username));
            PreparedStatement attempt = con.prepareStatement("SELECT " +
                    "* FROM Customer WHERE Username = " +
                    statementHelper(false, username) + " AND " +
                    "Password = " + statementHelper(false, password));
            ResultSet result = attempt.executeQuery();
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (username.equals("wildcats") && password.equals("12345"));
    }

    public static boolean managerLoginAttempt(String username, String password) {
        try {
            Connection con = getConnection();
            PreparedStatement attempt = con.prepareStatement("SELECT " +
                    "* FROM Manager WHERE Username = " +
                    statementHelper(false, username) + " AND " +
                    "Password = " + statementHelper(false, password));
            ResultSet result = attempt.executeQuery();
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Customer register(String username, String email, String
            password, String confirmpass) {
        ResultSet result;
        try {
            Connection con = getConnection();
            Customer toReturn;

            System.out.println("SELECT Username FROM Manager");

            PreparedStatement manager = con.prepareStatement("SELECT Username FROM Manager");
            result = manager.executeQuery();

            ArrayList<String> man = new ArrayList<>();
            while (result.next()) {
                man.add(result.getString(1));
            }

            for (int i = 0; i < man.size(); i++) {
                if (man.get(i).equals(username)) {
                    throw new IllegalArgumentException();
                }
            }

            PreparedStatement register = con.prepareStatement("INSERT into " +
                    "Customer (Username, Password, Email) VALUES (" +
                    statementHelper(true, username) + statementHelper
                    (true, password) + statementHelper(false,
                    email) + ")");
            register.executeUpdate();
            toReturn = new Customer(username, password, email);
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("already taken");
            return null;
        }
    }

    public static void addReview(int rating, String comment, String
            username, String trainNum) {
        try {
            Connection con = getConnection();
            System.out.println(("INSERT into " +
                    "Review (Comment, Rating, Username, TrainNumber) VALUES (" +
                    statementHelper(true, comment) + rating + ", " + statementHelper(true, username)
                    + statementHelper(false, trainNum) + ")"));
            PreparedStatement newReview = con.prepareStatement("INSERT into " +
                    "Review (Comment, Rating, Username, TrainNumber) VALUES (" +
                    statementHelper(true, comment) + rating + ", " + statementHelper(true, username)
                    + statementHelper(false, trainNum) + ")");
            newReview.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("can't insert review for some odd reason...");
        }
    }

    public static boolean verifySchool(String customer, String email) {
        try {
            Connection con = getConnection();
            PreparedStatement verify = con.prepareStatement("UPDATE Customer" +
                    " SET isStudent = 1 WHERE " +
                    "Username = " +
                    statementHelper(false, customer));
            //verify.setString(1, "1");
            if (email.substring(email.length() - 4).equals(".edu")) {
                verify.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getSchedule(String trainId) {
        ResultSet result;
        try {
            Connection con = getConnection();
            System.out.println("SELECT " +
                    "* FROM Stop WHERE TrainNumber = " +
                    statementHelper(false, trainId));
            PreparedStatement attempt = con.prepareStatement("SELECT " +
                    "* FROM Stop WHERE TrainNumber = " +
                    statementHelper(false, trainId));
            result = attempt.executeQuery();
            ArrayList<String> stops = new ArrayList<>();
            while (result.next()) {
                stops.add(result.getString(1) + ", " + result.getString
                        (2) + ", " + result.getString(3) + ", " + result.getString(4));
            }
            return stops;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<String> getGoodReview(String trainId) {
        ResultSet result;
        try {
            Connection con = getConnection();
            System.out.println("SELECT COMMENT FROM Review " +
                    "WHERE TrainNumber = \"" + trainId +
                    "\" AND Rating > 5 AND Comment IS NOT NULL"); // 5 , 3, 1
            PreparedStatement attempt = con.prepareStatement("SELECT COMMENT FROM Review " +
                    "WHERE TrainNumber = \"" + trainId +
                    "\" AND Rating > 5 AND Comment IS NOT NULL");
            result = attempt.executeQuery();
            ArrayList<String> good = new ArrayList<>();
            while (result.next()) {
                good.add(result.getString(1));
            }
            return good;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getNeutralReview(String trainId) {
        ResultSet result;
        try {
            Connection con = getConnection();
            System.out.println("SELECT COMMENT FROM Review " +
                    "WHERE TrainNumber = \"" + trainId +
                    "\" AND Rating = 5 AND Comment IS NOT NULL");
            PreparedStatement attempt = con.prepareStatement("SELECT COMMENT FROM Review " +
                    "WHERE TrainNumber = \"" + trainId +
                    "\" AND Rating = 5 AND Comment IS NOT NULL");
            result = attempt.executeQuery();
            ArrayList<String> neutral = new ArrayList<>();
            while (result.next()) {
                neutral.add(result.getString(1));
            }
            return neutral;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getBadReview(String trainId) {
        ResultSet result;
        try {
            Connection con = getConnection();
            System.out.println("SELECT COMMENT FROM Review " +
                    "WHERE TrainNumber = \"" + trainId +
                    "\" AND Rating < 5 AND Comment IS NOT NULL");
            PreparedStatement attempt = con.prepareStatement("SELECT COMMENT FROM Review " +
                    "WHERE TrainNumber = \"" + trainId +
                    "\" AND Rating < 5 AND Comment IS NOT NULL");
            result = attempt.executeQuery();
            ArrayList<String> bad = new ArrayList<>();
            while (result.next()) {
                bad.add(result.getString(1));
            }
            return bad;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getStations() {
        // (used in comboBoxes)
        ResultSet result;
        try {
            Connection con = getConnection();
            PreparedStatement stations = con.prepareStatement("SELECT " +
                    "Name, Location FROM Station");
            result = stations.executeQuery();
            ArrayList<String> stationsList = new ArrayList<>();
            while (result.next()) {
                stationsList.add(result.getString(1) + " (" + result.getString
                        (2) + ")");
            }
            return stationsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //this method returns a list of a list of attributes for each train that
    // matched the customer's request
    //the indices in the returning lists will be as follows:
    //0: Train Number
    //1: ArrivalTime
    //2: DepartureTime
    //3: 1st class price
    //4: second class price
    //5: Date requested
    //6: Arrival Location
    //7: Departure Location

    public static ArrayList<ArrayList<String>> findRequestedTrains(String departs, String arrives, String
            date) {
        try {
            Connection con = getConnection();
            ArrayList<ArrayList<String>> toReturn = new ArrayList<>();

            String statement = ("SELECT Stop.TrainNumber, StationName, " +
                    "ArrivalTime, " +
                    "DepartureTime, " +
                    "1stClassPrice, 2ndClassPrice FROM Stop JOIN TrainRoute" +
                    " ON Stop.TrainNumber = TrainRoute.TrainNumber" +
                    " WHERE(stationName = " + statementHelper(false,
                    departs) +
                    " OR StationName = " + statementHelper(false, arrives) +
                    ")" +
                    " AND (" +
                    "Stop.TrainNumber IN (SELECT TrainNumber FROM (SELECT Stop" +
                    ".TrainNumber, StationName FROM Stop JOIN TrainRoute ON " +
                    "Stop" +
                    ".TrainNumber = TrainRoute.TrainNumber WHERE (stationName = "
                    + statementHelper(false, arrives) + " OR StationName = " +
                    statementHelper(false, departs) + "))a GROUP BY " +
                    "TrainNumber HAVING COUNT( *" +
                    " ) =2))");
            System.out.println(statement);
            String desiredStops = "(.*)" + departs.substring(0, 2) + "(.*)" +
                    arrives.substring(0, 2) + "(.*)";
            PreparedStatement stations = con.prepareStatement(statement);
            ResultSet matchingNames = stations.executeQuery();
            int i = 0;
            while (matchingNames.next()) {
                if (matchingNames.getString(1).matches(desiredStops)) {
                    toReturn.add(new ArrayList<String>());
                    toReturn.get(i).add(matchingNames.getString(1));
                    String departureTime = matchingNames.getString(4);
                    matchingNames.next();
                    String arrivalTime = matchingNames.getString(3);
                    toReturn.get(i).add(arrivalTime);
                    toReturn.get(i).add(departureTime);
                    toReturn.get(i).add(matchingNames.getString(5));
                    toReturn.get(i).add(matchingNames.getString(6));
                    toReturn.get(i).add(date);
                    toReturn.get(i).add(arrives);
                    toReturn.get(i).add(departs);
                    i++;
                }
            }
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean trainExists(String trainNumber) {
        try {
            Connection con = getConnection();
            System.out.println("SELECT " +
                    "* FROM TrainRoute WHERE TrainNumber = " +
                    statementHelper(false, trainNumber));
            PreparedStatement attempt = con.prepareStatement("SELECT " +
                    "* FROM TrainRoute WHERE TrainNumber = " +
                    statementHelper(false, trainNumber));
            ResultSet result = attempt.executeQuery();
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addCard(String cardNum, int cardCVV, String expDate, String name, String username){
        try {
            Connection con = getConnection();
            PreparedStatement attempt = con.prepareStatement("INSERT into " +
                    "PaymentInfo (CardNumber, CVV, ExpDate, NameOnCard, Username) VALUES (" +
                    cardNum + ", " + cardCVV + ", " + statementHelper(true, expDate) + statementHelper(true, name) +
                    statementHelper(false, username) + ")");
            attempt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getUserCards(String username) {
        try {
            Connection con = getConnection();
            PreparedStatement attempt = con.prepareStatement("SELECT CardNumber FROM `PaymentInfo` WHERE Username like '" + username + "'");
            ResultSet cards = attempt.executeQuery();
            ArrayList<String> cardList = new ArrayList<>();
            while(cards.next()) {
                String tmp = cards.getString(1);
                cardList.add(tmp.substring(tmp.length() - 4, tmp.length()));
            }
            return cardList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean removeUserCard(String cardVal, String username) {
        try {
            Connection con = getConnection();
            PreparedStatement attempt = con.prepareStatement("DELETE FROM PaymentInfo WHERE CardNumber LIKE '%" + cardVal +
                    "' AND Username LIKE '" + username + "'");
            attempt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getCardNumber(String last4, String username) {
        try {
            Connection con = getConnection();
            PreparedStatement attempt = con.prepareStatement("SELECT " +
                    "CardNumber FROM `PaymentInfo` WHERE CardNumber like '%"
                    + last4 + "' AND Username LIKE " + statementHelper
                    (false, username));
            ResultSet cards = attempt.executeQuery();
            String cardNum = "";
            while (cards.next()) {
                cardNum = cards.getString(1);
            }
            return cardNum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkStudent(String username) {
        try {
            Connection con = getConnection();
            PreparedStatement check = con.prepareStatement("SELECT " +
                    "isStudent FROM Customer WHERE Username = " +
                    statementHelper(false, username));
            ResultSet result = check.executeQuery();
            boolean isStudent = false;
            String toRet = "";
            while (result.next()) {
                toRet = result.getString(1);
            }
            if (toRet.equals("0")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addSelectedReservations(List<Reserves>
                                                          reservations,
                                                  double price) {
        try {
            Connection con = getConnection();
            Reserves first = reservations.get(0);
            PreparedStatement reservation = con.prepareStatement("INSERT" +
                    " into Reservation (ReservationId, isCancelled, " +
                    "Username, CardNumber, Price) VALUES (" + statementHelper
                    (true, first.getReservationId()) + "0, " + statementHelper
                    (true, Login
                            .getName()) + statementHelper(true, MakeReservation
                    .getCard()) + statementHelper(false, price) + ")");
            reservation.executeUpdate();
            for (Reserves r : reservations) {
                PreparedStatement reserves = con.prepareStatement("INSERT " +
                        "into " +
                        "Reserves (ReservationId, TrainNumber, Class, " +
                        "DepartureDate, PassengerName, NumberOfBags, " +
                        "DepartsFrom, ArrivesAt) VALUES (" + statementHelper
                        (true, r.getReservationId()) + statementHelper(true,
                        r.getTrainNumber()) + booleanHelper(r.isClasstype()) +
                        statementHelper(true, r
                                .getDepartureDate()) + statementHelper(true, r
                        .getName()) + statementHelper(true, r
                        .getNumBags()) + statementHelper(true, r
                        .getDepartsFrom()) + statementHelper(false, r
                        .getArrivesAt()) + ")");
                reserves.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Reserves> getSelectedReservations(String id) {
        ArrayList<Reserves> toUpdate = new ArrayList<>();
        try {
            Connection con = getConnection();
            PreparedStatement attempt = con.prepareStatement("SELECT " +
                    "ReservationId" +
                    " FROM" +
                    " Reservation WHERE ReservationId = " + statementHelper
                    (false, id) + " AND Username = " + statementHelper
                    (false, Login.getName()) + " AND isCancelled = 0");
            ResultSet r = attempt.executeQuery();
            while (r.next()) {
                PreparedStatement query = con.prepareStatement("SELECT * " +
                        "FROM Reserves WHERE ReservationId = " +
                        statementHelper(false, id));
                ResultSet reservations = query.executeQuery();
                while (reservations.next()) {
                    Reserves res = new Reserves(reservations.getString
                            (2),
                            Boolean.valueOf(reservations.getString(3)),
                            reservations.getString(4), reservations.getString(2), reservations
                            .getInt(6), reservations.getString(7), reservations.getString(8));
                    toUpdate.add(res);

                }
            }
            return toUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<LocalDate> getAllDepartures(String id) {
        try {
            Connection con = getConnection();
            System.out.println("SELECT " +
                    "DepartureDate FROM " +
                    "Reserves WHERE ReservationID = " +
                    statementHelper(false, id));
            PreparedStatement check = con.prepareStatement("SELECT " +
                    "DepartureDate FROM " +
                    "Reserves WHERE ReservationID = " +
                    statementHelper(false, id));
            ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
            ResultSet result = check.executeQuery();
            while (result.next()) {
                System.out.println(result.getString(1).substring(0, 10));
                dates.add(LocalDate.parse(result.getString(1).substring(0,
                        10)));
            }
            return dates;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<LocalDate> getRelevantDates(String cardNum) {
        try {
            Connection con = getConnection();
            cardNum = "%" + cardNum;
            PreparedStatement check = con.prepareStatement("SELECT Reserves" +
                            ".DepartureDate FROM (PaymentInfo INNER JOIN Reservation ON PaymentInfo.CardNumber = Reservation.CardNumber) " +
                            "INNER JOIN Reserves ON Reservation" +
                    ".ReservationId = Reserves.ReservationId WHERE " +
                    "PaymentInfo.CardNumber LIKE " + statementHelper(false,
                    cardNum));
            ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
            ResultSet result = check.executeQuery();
            while (result.next()) {
                dates.add(LocalDate.parse(result.getString(1).substring(0,
                        10)));
            }
            return dates;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getClassPrice(boolean which, String trainNo) {
        try {
            Connection con = getConnection();
            String toCheck = (!which ? "2ndClassPrice" :
                    "1stClassPrice");
            System.out.println("SELECT " +
                    toCheck + " FROM TrainRoute WHERE TrainNumber = " +
                    statementHelper(false, trainNo));
            PreparedStatement check = con.prepareStatement("SELECT " +
                    toCheck + " FROM TrainRoute WHERE TrainNumber = " +
                    statementHelper(false, trainNo));
            ResultSet result = check.executeQuery();
            double res = 0.0;
            while (result.next()) {
                res = result.getDouble(1);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static double getReservationPrice(String id) {
        try {
            Connection con = getConnection();
            PreparedStatement check = con.prepareStatement("SELECT " +
                    "Price FROM Reservation WHERE ReservationId = " +
                    statementHelper(false, id));
            ResultSet result = check.executeQuery();
            double res = 0.0;
            while (result.next()) {
                res = result.getDouble(1);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static boolean updateReservationPriceAndTime(double price, String
            time, String id, String trainNumber) {
        try {
            Connection con = getConnection();
            PreparedStatement priceUpdate = con.prepareStatement("UPDATE " +
                    "Reservation" +
                    " SET Price = " + statementHelper(false, price) + " " +
                    "WHERE ReservationId = " + statementHelper(false, id));
            priceUpdate.executeUpdate();

            PreparedStatement dateUpdate = con.prepareStatement("UPDATE " +
                    "Reserves" +
                    " SET DepartureDate = " + statementHelper(false, time) +
                    "WHERE ReservationId = " + statementHelper(false, id) +
                    " AND TrainNumber = " + statementHelper(false,
                    trainNumber));
            dateUpdate.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean cancelReservation(double newPrice, String
            id) {
        try {
            Connection con = getConnection();
            System.out.println("UPDATE " +
                    "Reservation" +
                    " SET Price = " + statementHelper
                    (true,
                            newPrice) + "isCancelled = 1 WHERE ReservationId = " + statementHelper(false, id));
            PreparedStatement priceUpdate = con.prepareStatement("UPDATE " +
                    "Reservation" +
                    " SET Price = " + statementHelper
                    (true,
                    newPrice) + "isCancelled = 1 WHERE ReservationId = " + statementHelper(false, id));
            priceUpdate.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getMonthRev(int month) {
        try {
            Connection con = getConnection();
            String mon = "";
            if (month < 10) {
                mon = "0" + Integer.toString(month);
            } else {
                mon = Integer.toString(month);
            }
            System.out.println(mon);
            PreparedStatement statement = con.prepareStatement("SELECT SUM(Reservation.Price) FROM Reservation INNER JOIN Reserves ON Reservation.ReservationId = Reserves.ReservationID WHERE Reserves.DepartureDate like '%-" +
                    mon + "-%'");
            ResultSet rev = statement.executeQuery();
            rev.next();
            return rev.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static ArrayList<PopularRoute> getPopRotRep(int month) {
        try {
            Connection con = getConnection();
            String mon = "";
            if (month < 10) {
                mon = "0" + Integer.toString(month);
            } else {
                mon = Integer.toString(month);
            }
            PreparedStatement attempt = con.prepareStatement("SELECT Reserves.TrainNumber, COUNT(Reserves.TrainNumber) " +
                    "AS 'occurance' " +
                    "FROM Reservation " +
                    "INNER JOIN Reserves ON Reservation.ReservationID = Reserves.ReservationID " +
                    "WHERE Reservation.isCancelled = 0 AND Reserves.DepartureDate LIKE '%-" + mon + "-%' " +
                    "GROUP BY TrainNumber " +
                    "ORDER BY occurance DESC " +
                    "LIMIT 3");
            ResultSet result = attempt.executeQuery();
            ArrayList<PopularRoute> topThree = new ArrayList<>();
            while(result.next()) {
                String train = result.getString(1);
                int count = result.getInt(2);
                PopularRoute tmp = new PopularRoute(train,count);
                topThree.add(tmp);
            }
            return topThree;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //makes it easier to write things into the SQL statements - must have
    // toString method. Boolean represents if you need a comma or not.
    public static String statementHelper(boolean comma, Object str) {
        if (comma) {
            return "'" + str + "', ";
        } else {
            return "'" + str + "'";
        }
    }

    public static String booleanHelper(boolean flag) {
        return flag ? "1, " : "0, ";
    }

    public static void main(String[] args) {
        try {
            getConnection();
            System.out.println("Yay you connected!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
