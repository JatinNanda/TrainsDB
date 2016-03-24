package sample.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            String url = "jdbc:mysql://localhost:8889/WildCats";
            String username = "root";
            String password = "wildcats123";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Jatin probably doesn't have the server running right now");
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
            Scanner scanner = new Scanner(new File("tables.txt"));
            scanner.useDelimiter(";");
            //reads queries from file, separated by a ';'
            while(scanner.hasNext()) {
                createStatements.add(scanner.next());
            }
            for (String table : createStatements) {
                createTables.executeQuery(table);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
