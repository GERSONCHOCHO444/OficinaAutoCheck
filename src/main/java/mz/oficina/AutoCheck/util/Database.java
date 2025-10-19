package mz.oficina.AutoCheck.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/autocheck";
    private static final String USER = "root";       // troque pelo seu usuário do MySQL
    private static final String PASSWORD = "";       // troque pela senha do MySQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
