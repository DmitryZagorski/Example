package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector implements InterfaceSQL {

    private static final String URL = "jdbc:mysql://localhost:3306/delivery?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";

    Connection connection;


    @Override
    public Connection getConnection() {
        try {
            if (connection != null) {
                return connection;
            } else {
                connection = DriverManager.getConnection(URL, NAME, PASSWORD);

                if (!connection.isClosed()) {
                    System.out.println("...connected to SQL...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
