package com.trading.application.customer.entity;

import org.springframework.stereotype.Component;

@Component
public class Customer {
    private String name;
    private String email;
    private String id;
    private String picture;
    private String updatedAt;
    private int totalCapitalAvailable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public int getTotalCapitalAvailable() {
        return totalCapitalAvailable;
    }

    public void setTotalCapitalAvailable(int totalCapitalAvailable) {
        this.totalCapitalAvailable = totalCapitalAvailable;
    }
}
