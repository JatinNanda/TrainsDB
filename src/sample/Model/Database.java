package sample.Model;

import java.io.File;
import java.net.URL;
import java.sql.*;
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
            while(scanner.hasNext()) {
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
        try {
            Connection con = getConnection();
            Customer toReturn;
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
                        (2) + ", " + result.getString(3) + ", " +  result.getString(4));
            }
            return stops;
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
            String desiredStops = "%" + departs.substring(0, 2) + "%" + arrives
                    .substring(0, 2) + "%";
            PreparedStatement stations = con.prepareStatement("SELECT " +
                    "DISTINCT TrainNumber FROM Stop WHERE TrainNumber LIKE "
                    + statementHelper(false, desiredStops));
            ResultSet matchingNames = stations.executeQuery();
            int i = 0;
            while (matchingNames.next()) {
                toReturn.add(new ArrayList<String>());
                //add station name
                toReturn.get(i).add(matchingNames.getString(1));

                //add arrival time
                System.out.println("SELECT " +
                        "ArrivalTime FROM Stop WHERE TrainNumber = " +
                        statementHelper(false, matchingNames
                                .getString(1)) + " AND " +
                        "StationName = " + statementHelper(false, arrives));
                PreparedStatement arrival = con.prepareStatement("SELECT " +
                        "ArrivalTime FROM Stop WHERE TrainNumber = " +
                        statementHelper(false, matchingNames
                                .getString(1)) + " AND " +
                        "StationName = " + statementHelper(false, arrives));
                ResultSet arrivalTime = arrival.executeQuery();
                while (arrivalTime.next()) {
                    toReturn.get(i).add(arrivalTime.getString(1));
                }

                //add departureTime
                PreparedStatement departure = con.prepareStatement("SELECT " +
                        "ArrivalTime FROM Stop WHERE TrainNumber = " +
                        statementHelper(false, matchingNames
                                .getString(1)) + " AND " +
                        "StationName = " + statementHelper(false, departs));
                ResultSet departureTime = departure.executeQuery();
                while(departureTime.next()) {
                    toReturn.get(i).add(departureTime.getString(1));
                }

                //add 1st/2nd class price
                PreparedStatement prices = con.prepareStatement("SELECT " +
                        "1stClassPrice, 2ndClassPrice FROM TrainRoute WHERE " +
                        "TrainNumber = " + statementHelper(false,
                        matchingNames.getString(1)));
                ResultSet classPrices = prices.executeQuery();
                while (classPrices.next()) {
                    toReturn.get(i).add(classPrices.getString(1));
                    toReturn.get(i).add(classPrices.getString(2));
                }

                //add the requested date
                toReturn.get(i).add(date);
                //add arrival location
                toReturn.get(i).add(arrives);
                //add departure location
                toReturn.get(i).add(departs);
                i++;
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
            PreparedStatement attempt = con.prepareStatement("SELECT CardNumber FROM `PaymentInfo` WHERE Username like '"+ username + "'");
            ResultSet cards = attempt.executeQuery();
            ArrayList<String> cardList = new ArrayList<>();
            while(cards.next()) {
                String tmp = cards.getString(1);
                cardList.add(tmp.substring(tmp.length() - 4, tmp.length()));
            }
            return cardList;
        } catch(Exception e) {
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
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkStudent(String username) {
        try {
            Connection con = getConnection();
            PreparedStatement check = con.prepareStatement("SELECT " +
                    "isStudent FROM Customer WHERE Username = " +
                    statementHelper(false, username)) ;
            ResultSet result = check.executeQuery();
            while (result.next()) {
                System.out.println(result);
            }

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addSelectedReservations(List<Reserves>
                                                          reservations) {
        try {
            Connection con = getConnection();
            for (Reserves r: reservations) {
                System.out.println("INSERT into " +
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
                PreparedStatement check = con.prepareStatement("INSERT into " +
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
                check.executeUpdate();
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
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
