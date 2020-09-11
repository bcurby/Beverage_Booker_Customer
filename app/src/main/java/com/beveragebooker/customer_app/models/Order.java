package com.beveragebooker.customer_app.models;

public class Order {

    private int orderID;
    private int cartID;
    private int orderStatus;
    private int orderTime;

    public  Order() {

    }

    public Order(int orderID, int cartID, int orderStatus) {
        this.orderID = orderID;
        this.cartID = cartID;
        this.orderStatus = orderStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCartID() {
        return cartID;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public int getOrderTime() {
        return orderTime;
    }
}
