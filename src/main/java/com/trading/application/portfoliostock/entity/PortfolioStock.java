package com.trading.application.portfoliostock.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PortfolioStock {

    private float quantity;
    private String dateBought;
    private float stockBoughtPrice;

    private float allocation;

    public PortfolioStock(int quantity, String dateBought, float stockBoughtPrice, float allocation) {
        this.quantity = quantity;
        this.dateBought = dateBought;
        this.stockBoughtPrice = stockBoughtPrice;
        this.allocation = allocation;
    }

    public PortfolioStock(PortfolioStock other) {
        this.quantity = other.quantity;
        this.dateBought = other.dateBought;
        this.stockBoughtPrice = other.stockBoughtPrice;
        this.allocation = other.allocation;
    }
    public PortfolioStock() {
        // Default constructor
    }

    public float getAllocation() {
        return allocation;
    }

    public void setAllocation(float allocation) {
        this.allocation = allocation;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getStockBoughtPrice() {
        return stockBoughtPrice;
    }

    public void setStockBoughtPrice(float stockBoughtPrice) {
        this.stockBoughtPrice = stockBoughtPrice;
    }

    public String getDateBought() {
        return dateBought;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }
}
