package com.trading.application.customer.entity;

import org.springframework.stereotype.Component;

@Component
public class Customer {

    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String dateJoined;

    private int totalCapitalAvailable;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public int getTotalCapitalAvailable() {
        return totalCapitalAvailable;
    }

    public void setTotalCapitalAvailable(int totalCapitalAvailable) {
        this.totalCapitalAvailable = totalCapitalAvailable;
    }
}
