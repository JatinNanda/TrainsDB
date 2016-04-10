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
  public static void main(String[] args) {
    try {
      //setUp();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
