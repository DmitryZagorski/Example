package com.client;

import com.InterfaceSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientService {

    private InterfaceSQL interfaceSQL;

    public ClientService(InterfaceSQL interfaceSQL) {
        this.interfaceSQL = interfaceSQL;
    }

    public void printClientList() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from delivery.clients;");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " - " + resultSet.getInt("phoneNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
    }

    public void deleteClient() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the phone number of client you want to remove. ( 9 digits)");
            int phoneNumber = Integer.parseInt(reader.readLine());
            resultSet = statement.executeQuery("select * from delivery.clients where phoneNumber=" + phoneNumber + ";");
            if (resultSet.next()) {
                statement.execute("delete from delivery.clients where phoneNumber = " + phoneNumber + ";");
            } else {
                System.out.println("Client with that phone number doesn't exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
            reader.close();
        }
    }

    public void addClient() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the phone number of client you want to add. ( 9 digits)");
            int phoneNumber = Integer.parseInt(reader.readLine());
            resultSet = statement.executeQuery("select * from delivery.clients where phoneNumber=" + phoneNumber + ";");
            if (resultSet.next()) {
                System.out.println("Client with that phone number already exist.");
            } else {
                System.out.println("Enter the name of client you want to add.");
                String readerName = reader.readLine();
                statement.execute("insert into delivery.clients (name, phoneNumber) values ('" + readerName + "'" + phoneNumber + ");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
            reader.close();
        }
    }

    public void changeNameOfClientByPhoneNumber() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the phone number of client you want to change name.(9 digits)");
            int readPhoneNumber = Integer.parseInt(reader.readLine());
            resultSet = statement.executeQuery("select * from delivery.clients where phoneNumber=" + readPhoneNumber + ";");
            if (resultSet.next()) {
                System.out.println("Enter new name of client you want to change name");
                String newName = reader.readLine();
                statement.executeUpdate("update delivery.clients set name =" + "'" + newName + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
            reader.close();
        }
    }

    public void changePhoneNumberOfClientByPhoneNumber() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the phone number of client you want to change phone number.(9 digits)");
            int readPhoneNumber = Integer.parseInt(reader.readLine());
            resultSet = statement.executeQuery("select * from delivery.clients where phoneNumber=" + readPhoneNumber + ";");
            if (resultSet.next()) {
                int newPhoneNumber = Integer.parseInt(reader.readLine());
                statement.executeUpdate("update delivery.clients set phoneNumber =" + newPhoneNumber + ";");
            } else {
                System.out.println("Client with that phone number doesn't exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
            reader.close();
        }
    }
}

