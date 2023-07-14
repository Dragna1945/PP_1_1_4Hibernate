package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL ="jdbc:mysql://localhost:3306/users";
    private static final String USERNAME ="root";
    private static final String PASS ="Qz45909";
    public static Connection getConnecion () {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println(" Connection OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Connection ERR");
        }
        return connection;
    }
}
