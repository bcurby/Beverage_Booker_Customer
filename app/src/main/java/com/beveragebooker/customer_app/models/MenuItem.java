package com.beveragebooker.customer_app.models;

public class MenuItem {

    private int id;

    private String name;

    private String description;

    private double price;

    private int quantity;


    public MenuItem(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public MenuItem(int id, double price) {
        this.id = id;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }


    //Quantity methods
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        setQuantity(getQuantity() + 1);

    }
}
