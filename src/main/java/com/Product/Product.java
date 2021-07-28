package com.Product;

public class Product {
    private String name;
    private int price;
    private int count;
    private String[] productCategory;

    public Product(String name, int price, int count, String[] productCategory) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.productCategory = productCategory;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public String[] getProductCategory() {
        return productCategory;
    }

}
