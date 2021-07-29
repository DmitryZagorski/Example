package com.product;

public class ProductCategory {
    private String name;

    public ProductCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name.toString();
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "name='" + name + '\'' +
                '}';
    }

    public static final ProductCategory food = new ProductCategory("Food");
    public static final ProductCategory clothes = new ProductCategory("Clothes");
    public static final ProductCategory other = new ProductCategory("Other");

}

