package com.Order;

import com.Product.Product;

public class Order {
    private int id;
    public static int ID;
    private String clientName;
    private int clientPhoneNumber;
    private String shopName;
    private Product product;
    private int productCount;

    public Order(String clientName, int clientPhoneNumber, String shopName, Product product, int productCount) {
        this.id = ID++;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.shopName = shopName;
        this.product = product;
        this.productCount = productCount;
    }

    public int getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(int clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

