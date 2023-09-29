package com.trading.application.portfoliostock.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PortfolioStock {

    private String stockTicker;
    // current stock price
    private float stockPrice;
    private String portfolioId;
    private String userId;
    private int quantity;
    private String dateBought;
    private float stockBoughtPrice;
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public float getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
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
