package com;

import com.Client.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnector implements InterfaceSQL {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";

    Connection connection;


    @Override
    public Connection getConnection() {
        try {
            if (connection != null) {
                return connection;
            }
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);

            if (!connection.isClosed()){
                 System.out.println("...connected to SQL...");
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Client> getListOfClientsFromSQL() {
        getConnection();
        List<Client> clients = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from delivery.clients");
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getString("name"), resultSet.getInt("phoneNumber")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

}
