package com.beveragebooker.customer_app.models;

public class MenuItem {

    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int milk;
    private int sugar;
    private int decaf;
    private int extras;
    private int frappe;
    private int heated;

    private String itemMilk;
    private String itemSugar;
    private String itemDecaf;
    private String itemVanilla;
    private String itemCaramel;
    private String itemChocolate;
    private String itemWhippedCream;
    private String itemFrappe;
    private String itemHeated;
    private String itemComment;


    public MenuItem(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public MenuItem(int id, String name, String description, double price, int quantity,
                    int milk, int sugar, int decaf, int extras, int frappe, int heated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.milk = milk;
        this.sugar = sugar;
        this.decaf = decaf;
        this.extras = extras;
        this.frappe = frappe;
        this.heated = heated;
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

    public int getDecaf() {
        return decaf;
    }

    public int getExtras() {
        return extras;
    }

    public int getFrappe() {
        return frappe;
    }

    public int getHeated() {
        return heated;
    }


    //Menu item options getters
    public String getItemMilk() {
        return itemMilk;
    }

    public String getItemSugar() {
        return itemSugar;
    }

    public String getItemDecaf() {
        return itemDecaf;
    }

    public String getItemVanilla() {
        return itemVanilla;
    }

    public String getItemCaramel() {
        return itemCaramel;
    }

    public String getItemChocolate() {
        return itemChocolate;
    }

    public String getItemWhippedCream() {
        return itemWhippedCream;
    }

    public String getItemFrappe() {
        return itemFrappe;
    }

    public String getItemHeated() {
        return itemHeated;
    }

    public String getItemComment() {
        return itemComment;
    }


    //Quantity methods
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
