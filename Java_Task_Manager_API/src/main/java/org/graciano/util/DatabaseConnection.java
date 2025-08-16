package org.graciano.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = DatabaseConfig.getProperty("db.url");
        String user = DatabaseConfig.getProperty("db.user");
        String password = DatabaseConfig.getProperty("db.password");

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        }catch (SQLException e){
            System.err.println("Connection to database failed" + e.getMessage());
            throw new SQLException("Failed to connect to the database.", e);
        }
    }

}
