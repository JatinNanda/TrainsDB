package sample.Model;

import java.sql.*;


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
}
