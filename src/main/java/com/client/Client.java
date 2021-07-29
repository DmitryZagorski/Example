package com.client;

public class Client {
    private int id;
    public static int ID;
    private String name;
    private int phoneNumber;

    public Client(String name, int phoneNumber) {
        this.name = name;
        this.id = ID++;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
