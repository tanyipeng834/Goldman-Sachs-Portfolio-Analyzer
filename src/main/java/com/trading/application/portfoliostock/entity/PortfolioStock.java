package com.trading.application.portfoliostock.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PortfolioStock {

    private int quantity;
    private String dateBought;
    private float stockBoughtPrice;
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
