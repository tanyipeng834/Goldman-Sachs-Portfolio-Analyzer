package com.trading.application.stock.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Stock {
    private String stockTicker;
    private String stockName;
    private float stockCurrentPrice;
    private ArrayList<Float> incomeStatement;
    private ArrayList<Float> cashFlow;
    private String companyOverview;
    private String industry;
//    to put daily, weekly, and monthly

;    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public float getStockCurrentPrice() {
        return stockCurrentPrice;
    }

    public void setStockCurrentPrice(float stockCurrentPrice) {
        this.stockCurrentPrice = stockCurrentPrice;
    }
}
