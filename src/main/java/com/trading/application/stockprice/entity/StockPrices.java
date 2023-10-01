package com.trading.application.stockprice.entity;

import java.io.Serializable;
import java.util.List;

public class StockPrices implements Serializable {
    private List<StockPrice> stockPriceList;



    public StockPrices(List<StockPrice> stockPriceList) {
        this.stockPriceList = stockPriceList;
    }

    // Getter and setter methods for stockPriceList
    public List<StockPrice> getStockPriceList() {
        return stockPriceList;
    }

    public void setStockPriceList(List<StockPrice> stockPriceList) {
        this.stockPriceList = stockPriceList;
    }

    // Add any other properties as needed
}
