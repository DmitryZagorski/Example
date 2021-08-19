package com.order;

import com.InterfaceSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderService {

    private InterfaceSQL interfaceSQL;

    public OrderService(InterfaceSQL interfaceSQL) {
        this.interfaceSQL = interfaceSQL;
    }

    public void addOrderToOrderList() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter client's name.");
            String clientsName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.clients where name = '" + clientsName + "'");
            if (resultSet.next()) {
                System.out.println("Enter client's phone number.(9 digits)");
                String phoneNumber = reader.readLine();
                resultSet = statement.executeQuery("select * from delivery.clients where phoneNumber = '" + phoneNumber + "'");
                if (resultSet.next()) {
                    resultSet = statement.executeQuery("select * from delivery.products");

                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    int count = resultSet.getInt("count");
                    String shop = resultSet.getString("shop");
                    System.out.println(id + " " + name + " " +
                            price + " euro " + count + " ones " + "( " + shop + " )");
                    System.out.println();
                    System.out.println("Enter the shop name.");
                    String shopName = reader.readLine();
                    resultSet = statement.executeQuery("select * from delivery.products where shop = '" + shopName + "'");
                    if (resultSet.next()) {
                        int id1 = resultSet.getInt("id");
                        String name1 = resultSet.getString("name");
                        int price1 = resultSet.getInt("price");
                        int count1 = resultSet.getInt("count");
                        System.out.println(id1 + " " + name1 + " " +
                                price1 + " euro " + count1 + " ones ");
                        System.out.println("Enter the product.");
                        String name2 = reader.readLine();
                        resultSet = statement.executeQuery("select * from delivery.products where name = '" + name2 + "'");
                        if (resultSet.next()) {
                            int id2 = resultSet.getInt("id");
                            int price2 = resultSet.getInt("price");
                            int count2 = resultSet.getInt("count");
                            System.out.println(id2 + " " + name2 + " " +
                                    price2 + " euro " + count2 + " ones ");
                            System.out.println();
                            System.out.println("Enter the count of product");
                            int count3 = Integer.parseInt(reader.readLine());
                            resultSet = statement.executeQuery("select count from delivery.products where name = '" + name2 + "'");
                            if (resultSet.next()) {
                                int lastCount = resultSet.getInt("count");
                                if (count3 <= lastCount) {
                                    int sum = price2 * count3;
                                    statement.executeUpdate("insert into delivery.orders (clientsName, clientsNumber, choosedShop, choosedProduct, choosedCount, sum) values " +
                                            "('" + clientsName + "' + '" + phoneNumber + "'+ '" + shopName + "' + '" + name2 + "'+ " + count3 + " + " + sum + ")");
                                    System.out.println("The order is added.");
                                } else {
                                    System.out.println("Too much count.");
                                }

                            } else {
                                System.out.println("Wrong count.");
                            }

                        } else {
                            System.out.println("Wrong name of product.");
                        }
                    } else {
                        System.out.println("Wrong shop name.");
                    }
                } else {
                    System.out.println("Wrong clients phone");
                }
            } else {
                System.out.println("Wrong clients name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            reader.close();
            resultSet.close();
        }
    }


    public void deleteOrderFromOrderList() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the phone number of client you want to remove.(9 digits)");
        String clientsNumber = reader.readLine();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from delivery.orders where clientsNumber = '" + clientsNumber + "'");
            if (resultSet.next()) {
                statement.executeUpdate("delete from delivery.orders where clientsNumber = '" + clientsNumber + "'");
                System.out.println("Order is deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            reader.close();
        }
    }

    public void printListOfOrders() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from delivery.orders");
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String clientsName = resultSet.getString("clientsName");
                String clientsNumber = resultSet.getString("clientsNumber");
                String choosedShop = resultSet.getString("choosedShop");
                String choosedProduct = resultSet.getString("choosedProduct");
                int choosedCount = resultSet.getInt("choosedCount");
                int sum = resultSet.getInt("sum");
                System.out.println("Clients name: " + clientsName + "\n Phone number: " + clientsNumber +
                        "\n com.Shop name: " + choosedShop + "\n com.Product.com.Product name: " + choosedProduct +
                        "\n com.Product.com.Product count: " + choosedCount + "\n SUM: " +
                        sum + " euro" + "\n");
            } else {
                System.out.println("No orders...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }
    }
}
