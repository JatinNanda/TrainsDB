package sample.Model;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
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

    public static boolean verifySchool(String email) {
        try {
            Connection con = getConnection();
            boolean flag = true;
            PreparedStatement verify = con.prepareStatement("UPDATE Customer" +
                    " SET isStudent = " + flag);
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

    //makes it easier to write things into the SQL statements - must have
    // toString method. Boolean represents if you need a comma or not.
    public static String statementHelper(boolean comma, Object str) {
        if (comma) {
            return "'" + str + "', ";
        } else {
            return "'" + str + "'";
        }
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
