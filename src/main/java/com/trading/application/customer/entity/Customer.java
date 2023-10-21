package com.trading.application.customer.entity;

import org.springframework.stereotype.Component;

/**
 * The type Customer.
 */
@Component
public class Customer {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Email.
     */
    private String email;
    /**
     * The Id.
     */
    private String id;
    /**
     * The Picture.
     */
    private String picture;
    /**
     * The Updated at.
     */
    private String updatedAt;
    /**
     * The Total capital available.
     */
    private int totalCapitalAvailable;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets picture.
     *
     * @return the picture
     */
    public String getPicture() { return picture; }

    /**
     * Sets picture.
     *
     * @param picture the picture
     */
    public void setPicture(String picture) { this.picture = picture; }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public String getUpdatedAt() { return updatedAt; }

    /**
     * Sets updated at.
     *
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    /**
     * Gets total capital available.
     *
     * @return the total capital available
     */
    public int getTotalCapitalAvailable() {
        return totalCapitalAvailable;
    }

    /**
     * Sets total capital available.
     *
     * @param totalCapitalAvailable the total capital available
     */
    public void setTotalCapitalAvailable(int totalCapitalAvailable) {
        this.totalCapitalAvailable = totalCapitalAvailable;
    }
}
