package com.beveragebooker.customer_app;

public class User {

    private int id;
    private String email, firstName, LastName, phone;


    public User(int id, String email, String firstName, String lastName, String phone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        LastName = lastName;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhone() {
        return phone;
    }
}
