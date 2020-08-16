package com.beveragebooker.customer_app.models;

public class MenuItem {

    private int id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private int milk;

    private int sugar;


    public MenuItem(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public MenuItem(int id, String name, String description, double price, int quantity,
                    int milk, int sugar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.milk = milk;
        this.sugar = sugar;

    }

    public MenuItem(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public MenuItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public int getMilk() {
        return milk;
    }

    public int getSugar() {
        return sugar;
    }


    //Quantity methods
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
