package com.Shop;

import com.InterfaceSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShopService {

    private InterfaceSQL interfaceSQL;
    public ShopService(InterfaceSQL interfaceSQL){
        this.interfaceSQL = interfaceSQL;
    }

    public void addShop() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));





        System.out.println("Enter the name of the shop, you want to add.");
        String a = reader.readLine();
        int count = 0;
        for (int i = 0; i < shops.size(); i++) {
            if ((shops.get(i).getName()).equals(a)) {
                count++;
            }
        }
        if (count == 0) {
            shops.add(new Shop(a, new ArrayList<>()));
        }
        if (count > 0) {
            System.out.println("That shop already exist.");
        }

        shopDataBase.writeShopsToFile(shops);
    }

    public void printShopList() {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        System.out.println("The shop list: ");
        for (Shop shop : shops) {
            System.out.println(shop.getName());
        }
    }

    public void addProductsToShop() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the name of the shop, where you want to add the product.");
        String a = bufferedReader.readLine();
        for (Shop shop : shops) {
            if (a.equals(shop.getName())) {
                System.out.println("com.Product name: ");
                String b = bufferedReader.readLine();
                System.out.println("com.Product price: ");
                int c = Integer.parseInt(bufferedReader.readLine());
                System.out.println("com.Product count");
                int d = Integer.parseInt(bufferedReader.readLine());
                System.out.println("com.Product Category: \n" +
                        "1 - Food\n" +
                        "2 - Clothes\n" +
                        "3 - Other");
                String e = bufferedReader.readLine();
                String[] array = e.split(",");
                List<String> middleAr = new ArrayList<>();
                for (int i = 0; i < array.length; i++) {
                    if (array[i].trim().equals("1")){
                        middleAr.add(ProductCategory.food.getName());
                    }
                    if (array[i].trim().equals("2")){
                        middleAr.add(ProductCategory.clothes.getName());
                    }
                    if (array[i].trim().equals("3")){
                        middleAr.add(ProductCategory.other.getName());
                    }
                    else  {
                        System.out.println("Wrong enter. Try one more time.");
                    }
                }
                int l = middleAr.size();
                String[] prCat = new String[l];
                prCat = middleAr.toArray(prCat);
                shop.getProductList().add(new Product(b,c,d,prCat));
            }
            else {
                System.out.println("Wrong name.");
            }
        }
        shopDataBase.writeShopsToFile(shops);
    }

    public void deleteShopFromShopList() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the name of the shop, you want to delete.");
        String a = reader.readLine();
        int count = 0;
        for (Shop shop : shops) {
            if (a.equals(shop.getName())) {
                shops.remove(shop);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("That shop isn't exist.");
        }
        shopDataBase.writeShopsToFile(shops);
    }

    public void deleteProductsFromShop() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the name of the shop, where you want to delete the product.");
        String a = bufferedReader.readLine();
        for (Shop shop : shops) {
            if (a.equals(shop.getName())) {
                System.out.println("com.Product name: ");
                String b = bufferedReader.readLine();
                int count = 0;
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    if ((shop.getProductList().get(i).getName()).equals(b)) {
                        shop.getProductList().remove(i);
                        count++;
                    }
                }
                if (count == 0) {
                    System.out.println("com.Product with such name doesn't exist in that list.");
                }
            }
        }
        shopDataBase.writeShopsToFile(shops);
    }

    public void printProductListInTheShop() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the shop name, where to print the product list.");
        String a = bf.readLine();
        for (Shop shop : shops) {
            if (a.equals(shop.getName())) {
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    System.out.println(shop.getProductList().get(i).getName() + " - " + shop.getProductList().get(i).getPrice() + " euro." +
                            " ("+shop.getProductList().get(i).getCount()+" ones)");
                }
            }
        }
        System.out.println("Do you want to sort products?\n" +
                "'1' - yes\n" +
                "'2' - no");
        String a1 = bf.readLine();
        if (a1.equals("1")) {
            System.out.println("'1' - by price\n" +
                    "'2' - by name");
            String a2 = bf.readLine();
            if (a2.equals("1")) {
                List<Shop> shopsByName = new ArrayList<>(shops);
                for (Shop shop : shopsByName) {
                    shop.getProductList().sort(Comparator.comparing(com.Product::getPrice).thenComparing(com.Product::getName));
                }
                for (Shop shop : shopsByName) {
                    for (int i = 0; i < shop.getProductList().size(); i++) {
                        System.out.println(shop.getProductList().get(i).getName() + "\n" +
                                " : " + shop.getProductList().get(i).getPrice() + " dollars" + "\n" +
                                " : " + shop.getProductList().get(i).getCount() + " ones" + "\n" +
                                " ( " + shop.getName() + " ) " + "\n");
                    }
                }
            }
            if (a2.equals("2")) {
                List<Shop> shopsByName = new ArrayList<>(shops);
                for (Shop shop : shopsByName) {
                    shop.getProductList().sort(Comparator.comparing(com.Product::getName).thenComparing(com.Product::getPrice));
                }
                for (Shop shop : shopsByName) {
                    for (int i = 0; i < shop.getProductList().size(); i++) {
                        System.out.println(shop.getProductList().get(i).getName() + "\n" +
                                " : " + shop.getProductList().get(i).getPrice() + " dollars" + "\n" +
                                " : " + shop.getProductList().get(i).getCount() + " ones" + "\n" +
                                " ( " + shop.getProductList().get(i).getName() + " ) " + "\n");
                    }
                }
            }
        }
    }

    public void changePriceOfProductInTheShop() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the shop name, where to change price of the product.");
        String a = reader.readLine();
        for (Shop shop : shops) {
            if (shop.getName().equals(a)) {
                System.out.println("com.Product name:");
                String b = reader.readLine();
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    if (b.equals(shop.getProductList().get(i).getName())) {
                        System.out.println("Enter new price.");
                        int c = Integer.parseInt(reader.readLine());
                        shop.getProductList().get(i).setPrice(c);
                        System.out.println("Price is changed.");
                    }
                }
            }
        }
        shopDataBase.writeShopsToFile(shops);
    }

    public void changeCountOfProductInTheShop() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the shop name, where to change count of the product.");
        String a = reader.readLine();
        for (Shop shop : shops) {
            if (shop.getName().equals(a)) {
                System.out.println("com.Product name:");
                String b = reader.readLine();
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    if (b.equals(shop.getProductList().get(i).getName())) {
                        System.out.println("Enter new count.");
                        int c = Integer.parseInt(reader.readLine());
                        shop.getProductList().get(i).setCount(c);
                        System.out.println("Count is changed.");
                    }
                }
            }
        }
        shopDataBase.writeShopsToFile(shops);
    }

    public void findProductInShopsByName() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the name of product to find it in the shops.");
        String a = reader.readLine();
        System.out.println("Enter the price to find the product by price or press \"ENTER\" to find only by name.");
        String b = reader.readLine();
        if (b.equals("")) {
            for (Shop shop : shops) {
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    if ((shop.getProductList().get(i).getName()).equals(a)) {
                        System.out.println(shop.getProductList().get(i).getName() + " - "
                                + shop.getProductList().get(i).getPrice() + " dollars" + " ( " +
                                shop.getProductList().get(i).getCount() + " ones" + " )" +
                                " :: " + shop.getName());
                    }
                }
            }
        } else {
            int c = Integer.parseInt(b);
            for (Shop shop : shops) {
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    if (shop.getProductList().get(i).getName().equals(a) &&
                            shop.getProductList().get(i).getPrice() == c) {
                        System.out.println(shop.getProductList().get(i).getName() + " - "
                                + shop.getProductList().get(i).getPrice() + " dollars" +
                                shop.getProductList().get(i).getCount() + " ones");
                    }
                }
            }
        }

    }

    public void findProductInShopsByProductCategory() throws IOException {
        List<Shop> shops = shopDataBase.getShopsFromFile();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\"Enter the product category to find it in the shops.\n" +
                "'1' - Food\n" +
                "'2' - Clothes\n" +
                "'3' - Other");
        String a = reader.readLine();
        if (a.equals("1")) {
            for (Shop shop : shops) {
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    for (int j = 0; j < shop.getProductList().get(i).getProductCategory().length; j++) {
                        if (shop.getProductList().get(i).getProductCategory()[j].equals("Food")) {
                            System.out.println(shop.getName() + "\n" +
                                    " : " + shop.getProductList().get(i).getName() + "\n" +
                                    " - " + shop.getProductList().get(i).getPrice() + " dollars" + "\n" +
                                    " - " + shop.getProductList().get(i).getCount() + " ones " + "\n");
                        }
                    }
                }
            }
        }
        if (a.equals("2")) {
            for (Shop shop : shops) {
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    for (int j = 0; j < shop.getProductList().get(i).getProductCategory().length; j++) {
                        if (shop.getProductList().get(i).getProductCategory()[j].equals("Clothes")) {
                            System.out.println(shop.getName() + "\n" +
                                    " : " + shop.getProductList().get(i).getName() + "\n" +
                                    " - " + shop.getProductList().get(i).getPrice() + " dollars" + "\n" +
                                    " - " + shop.getProductList().get(i).getCount() + " ones " + "\n");
                        }
                    }
                }
            }
        }
        if (a.equals("3")) {
            for (Shop shop : shops) {
                for (int i = 0; i < shop.getProductList().size(); i++) {
                    for (int j = 0; j < shop.getProductList().get(i).getProductCategory().length; j++) {
                        if (shop.getProductList().get(i).getProductCategory()[j].equals("Other")) {
                            System.out.println(shop.getName() + "\n" +
                                    " : " + shop.getProductList().get(i).getName() + "\n" +
                                    " - " + shop.getProductList().get(i).getPrice() + " dollars" + "\n" +
                                    " - " + shop.getProductList().get(i).getCount() + " ones " + "\n");
                        }
                    }
                }
            }
        }
    }

    public void printAllProductsInTheShops(){
        List<Shop> shops = shopDataBase.getShopsFromFile();

        for (Shop shop : shops) {
            for (Product product : shop.getProductList()) {
                System.out.println(product.getName() + ", Price: "+ product.getPrice()+" euro"+
                        ", Count: "+product.getCount() + " ones" +" (" +shop.getName()+")");
            }
        }
    }

    //  SQL



}



