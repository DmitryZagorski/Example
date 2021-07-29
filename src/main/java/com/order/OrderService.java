package com.order;

import com.client.Client;
import com.InterfaceSQL;
import com.shop.Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private InterfaceSQL interfaceSQL;

    public OrderService(InterfaceSQL interfaceSQL) {
        this.interfaceSQL = interfaceSQL;
    }

    public void addOrderToOrderList() throws IOException {
        List<Order> orders = orderDataBase.getOrdersFromFile();
        List<Shop> shops = new ShopJsonDataBase().getShopsFromFile();
        List<Client> clients = new ClientJsonDataBase().getClientsFromFile();
        List<Order> middle = new ArrayList<>();
        middle.clear();
        int middleInt = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter client's name.");
        String a = reader.readLine();
        int orderListSize = orders.size();
        for (Client client : clients) {
            if (!(a.equals(client.getName()))) {
            } else {
                System.out.println("Enter client's phone number.(9 digits)");
                int b = Integer.parseInt(reader.readLine());
                for (Client client1 : clients) {
                    if (b != client1.getPhoneNumber()) {
                    } else {
                        System.out.println("All products in all shops: ");
                        for (Shop shop : shops) {
                            for (Product product : shop.getProductList()) {
                                System.out.println(product.getName() +
                                        " - " + product.getPrice() + " euro" + " - " + product.getCount() + " ones" + " : " +
                                        shop.getName());
                            }
                        }
                        System.out.println("Enter shop name.");
                        String c = reader.readLine();
                        for (Shop shop : shops) {
                            if (!(shop.getName().equals(c))) {
                            } else {
                                System.out.println("Enter the name of the product: ");
                                String d = reader.readLine();
                                for (Product product : shop.getProductList()) {
                                    if (!(d.equals(product.getName()))) {
                                    } else {
                                        System.out.println("Enter the count of that product: ");
                                        int f = Integer.parseInt(reader.readLine());
                                        if (product.getCount() < f) {
                                        } else {
                                            orders.add(new Order(a, b, c, product, f));
                                            middle.add(new Order(a, b, c, product, f));
                                            middleInt = f;
                                            System.out.println("The order is added.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (orderListSize != orders.size()) {
            for (Shop shop : shops) {
                for (Product product : shop.getProductList()) {
                    if (shop.getName().equals(middle.get(0).getShopName()) &&
                            product.getName().equals(middle.get(0).getProduct().getName())) {
                        product.setCount(product.getCount() - middleInt);
                    }
                }
            }
        } else {
            System.out.println("Something wrong.");
        }

        orderDataBase.writeOrdersToFile(orders);
        new ShopJsonDataBase().writeShopsToFile(shops);
    }

    public void deleteOrderFromOrderList() throws IOException {
        List<Order> orders = orderDataBase.getOrdersFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the phone number of client you want to remove.(9 digits)");
        String a = reader.readLine();
        for (Order order : orders) {
            if (order.getClientPhoneNumber() == Integer.parseInt(a)) {
                orders.remove(order);
            } else {
                System.out.println("There is no orders with that phone number.");
            }
        }

        orderDataBase.writeOrdersToFile(orders);
    }

    public void printListOfOrders() {
        List<Order> orders = orderDataBase.getOrdersFromFile();

        System.out.println("List of all orders: ");
        for (Order order : orders) {
            System.out.println("Clients name: " + order.getClientName() + "\n Phone number: " + order.getClientPhoneNumber() +
                    "\n com.Shop name: " + order.getShopName() + "\n com.Product.com.Product name: " + order.getProduct().getName() + " (" + order.getProduct().getPrice() + " euro)" +
                    "\n com.Product.com.Product count: " + order.getProductCount() + "\n SUM: " +
                    (order.getProduct().getPrice() * order.getProductCount()) + " euro" + "\n");
        }
    }

}
