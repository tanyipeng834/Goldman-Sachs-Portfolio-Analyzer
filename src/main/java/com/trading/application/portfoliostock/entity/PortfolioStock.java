package com.trading.application.portfoliostock.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PortfolioStock {

    private float quantity;
    private String dateBought;
    private float stockBoughtPrice;

    private float allocation;

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
