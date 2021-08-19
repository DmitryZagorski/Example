package com.shop;

import com.InterfaceSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShopService {

    private InterfaceSQL interfaceSQL;

    public ShopService(InterfaceSQL interfaceSQL) {
        this.interfaceSQL = interfaceSQL;
    }

    public void addShop() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the name of the shop you want to add.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.shops where name = '" + shopName + "';");
            if (resultSet.next()) {
                System.out.println("Shop with that name already exist.");
            } else {
                statement.execute("insert into delivery.shops (name) values ('" + shopName + "');");
                System.out.println("New shop was added successfully.");
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

    public void printShopList() throws SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("The shop list:");
            resultSet = statement.executeQuery("select * from delivery.shops;");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " "+resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
    }

    public void addProductsToShop() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();

            System.out.println("Enter the name of the shop, where you want to add the product.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.shops where name = '" + shopName + "';");
            if (resultSet.next()) {
                System.out.println("Enter the name of product.");
                String productName = reader.readLine();
                System.out.println("Enter the price of product.");
                String productPrice = reader.readLine();
                System.out.println("Enter the count of product.");
                String productCount = reader.readLine();
                statement.execute("insert into delivery.products (name, price, count) values ('" + productName + "'," + productPrice + productCount + ")");
                statement.execute("insert into delivery.shops_product (shop_name, product_name) values ('" + shopName + "','" + productName + "')");
                System.out.println("Enter the categories of product throw ','");
                String productCategory = reader.readLine();
                String[] array = productCategory.split(",");
                for (int i = 0; i < array.length; i++) {
                    if (array[i].trim().equals("1")) {
                        statement.execute("insert into delivery.categories_products (categories_name, product_name) values ('Food','" + productName + "')");
                    }
                    if (array[i].trim().equals("2")) {
                        statement.execute("insert into delivery.categories_products (categories_name, product_name) values ('Clothes','" + productName + "')");
                    }
                    if (array[i].trim().equals("3")) {
                        statement.execute("insert into delivery.categories_products (categories_name, product_name) values ('Others','" + productName + "')");
                    } else {
                        System.out.println("Wrong enter. Try one more time.");
                    }
                }
            } else {
                System.out.println("That shop doesn't exist.");
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

    public void deleteShopFromShopList() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the name of the shop, you want to delete.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.shops where name = '" + shopName + "'");
            if (resultSet.next()) {
                statement.execute("delete from delivery.shops where name = '" + shopName + "'");
            } else {
                System.out.println("That shop isn't exist.");
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

    public void deleteProductsFromShop() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the name of the shop, where you want to delete the product.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.shops where name = '" + shopName + "'");
            if (resultSet.next()) {
                System.out.println("Enter the name of product.");
                String productName = reader.readLine();
                resultSet1 = statement.executeQuery("select * from delivery.product where shop = '" + shopName + "'");
                if (resultSet1.next()) {
                    statement.execute("delete from delivery.products where name = '" + productName + "' and shop = '" + shopName + "';");
                } else {
                    System.out.println("Product with such name doesn't exist in that shop.");
                }
            } else {
                System.out.println("That shop doesn't exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
            resultSet1.close();
            reader.close();
        }
    }

    public void printProductListInTheShop() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the shop name, where to print the product list.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.products where shop = '" + shopName + "'");
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int count = resultSet.getInt("count");
                int price = resultSet.getInt("price");
                System.out.println(id + " " + name + price + "euro" + count + "ones");
                System.out.println("Do you want to sort products?\n" +
                        "'1' - yes\n" +
                        "'2' - no");
                String response = reader.readLine();
                if (response.equals("1")) {
                    System.out.println("1 - by price" +
                            "2 - by name");
                    if (response.equals("1")) {
                        ResultSet resultSet1 = statement.executeQuery("select * from delivery.products where shop = '" + shopName + "' order by price;");
                        int id1 = resultSet1.getInt("id");
                        String name1 = resultSet1.getString("name");
                        int count1 = resultSet1.getInt("count");
                        int price1 = resultSet1.getInt("price");
                        System.out.println(id1 + name1 + price1 + "euro" + count1 + "ones");
                    }
                    if (response.equals("2")) {
                        ResultSet resultSet2 = statement.executeQuery("select * from delivery.products where shop = '" + shopName + "' order by name;");
                        int id2 = resultSet2.getInt("id");
                        String name2 = resultSet2.getString("name");
                        int count2 = resultSet2.getInt("count");
                        int price2 = resultSet2.getInt("price");
                        System.out.println(id2 + name2 + price2 + "euro" + count2 + "ones");
                    }
                }
            } else {
                System.out.println("That shop doesn't exist.");
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

    public void changeCountOfProductInTheShop() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the shop name, where to change count of the product.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.products where name = '" + shopName + "'");
            if (resultSet.next()) {
                System.out.println("Product name: ");
                String productName = reader.readLine();
                ResultSet resultSet1 = statement.executeQuery("select * from delivery.products where name = '" + productName + "'");
                if (resultSet1.next()) {
                    System.out.println("Enter new count: ");
                    String newCount = reader.readLine();
                    statement.executeUpdate("update delivery.products set count = " + newCount + "");
                    System.out.println("Count is changed");
                } else {
                    System.out.println("That product doesn't exist");
                }
            } else {
                System.out.println("That shop doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            reader.close();
        }
    }

    public void changePriceOfProductInTheShop() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the shop name, where to change price of the product.");
            String shopName = reader.readLine();
            resultSet = statement.executeQuery("select * from delivery.products where name = '" + shopName + "'");
            if (resultSet.next()) {
                System.out.println("Product name: ");
                String productName = reader.readLine();
                ResultSet resultSet1 = statement.executeQuery("select * from delivery.products where name = '" + productName + "'");
                if (resultSet1.next()) {
                    System.out.println("Enter new price: ");
                    String newPrice = reader.readLine();
                    statement.executeUpdate("update delivery.products set price = " + newPrice + "");
                    System.out.println("Price is changed");
                } else {
                    System.out.println("That product doesn't exist");
                }
            } else {
                System.out.println("That shop doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            reader.close();
        }
    }

    public void findProductInShopsByName() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("Enter the name of product to find it in the shops.");
            String productName = reader.readLine();
            System.out.println("Enter the price to find the product by price or press \"ENTER\" to find only by name.");
            String productPrice = reader.readLine();
            if (productPrice.equals("")) {
                resultSet = statement.executeQuery("select * from delivery.products where name = '" + productName + "'");
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    int count = resultSet.getInt("count");
                    System.out.println(id + " " + name + " " + price + " euro " + count + " ones.");
                } else {
                    System.out.println("That product doesn't exist in shops");
                }
            } else {
                resultSet = statement.executeQuery("select * from delivery.products where name = '" + productName + "' & price = " + productPrice + " ");
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    int count = resultSet.getInt("count");
                    System.out.println(id + " " + name + " " + price + " euro " + count + " ones.");
                } else {
                    System.out.println("Product with that price doesn't exist.");
                }
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

    public void findProductInShopsByProductCategory() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            System.out.println("\"Enter the product category to find it in the shops.\n" +
                    "'1' - Food\n" +
                    "'2' - Clothes\n" +
                    "'3' - Other");
            String category = reader.readLine();
            if (category.equals("1")) {
                resultSet = statement.executeQuery("select * from delivery.categories_products where categories_name = 'Food';");
                String productName = resultSet.getString("name");
                resultSet = statement.executeQuery("select * from delivery.products where name = '" + productName + "'");
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int count = resultSet.getInt("count");
                System.out.println("Products with Food category: " +
                        "" + id + " " + productName + " " + price + " euro " + count + " ones.");
            }
            if (category.equals("2")) {
                resultSet = statement.executeQuery("select * from delivery.categories_products where categories_name = 'Clothes';");
                String productName = resultSet.getString("name");
                resultSet = statement.executeQuery("select * from delivery.products where name = '" + productName + "'");
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int count = resultSet.getInt("count");
                System.out.println("Products with Clothes category: " +
                        "" + id + " " + productName + " " + price + " euro " + count + " ones.");
            }
            if (category.equals("1")) {
                resultSet = statement.executeQuery("select * from delivery.categories_products where categories_name = 'Other';");
                String productName = resultSet.getString("name");
                resultSet = statement.executeQuery("select * from delivery.products where name = '" + productName + "'");
                int id = resultSet.getInt("id");
                int price = resultSet.getInt("price");
                int count = resultSet.getInt("count");
                System.out.println("Products with Other category: " +
                        "" + id + " " + productName + " " + price + " euro " + count + " ones.");
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

    public void printAllProductsInTheShops() throws SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = interfaceSQL.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from delivery.products;");
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            int count = resultSet.getInt("count");
            System.out.println(id + " " + name + " " +
                    price + " euro " + count + " ones");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }

    }
}



