package com.trading.application.stockprice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * The type Stock prices.
 */
public class StockPrices implements Serializable {
    /**
     * The Stock price list.
     */
    private List<StockPrice> stockPriceList;


    /**
     * Instantiates a new Stock prices.
     *
     * @param stockPriceList the stock price list
     */
    public StockPrices(List<StockPrice> stockPriceList) {
        this.stockPriceList = stockPriceList;
    }

    /**
     * Gets stock price list.
     *
     * @return the stock price list
     */
// Getter and setter methods for stockPriceList
    public List<StockPrice> getStockPriceList() {
        return stockPriceList;
    }

    /**
     * Sets stock price list.
     *
     * @param stockPriceList the stock price list
     */
    public void setStockPriceList(List<StockPrice> stockPriceList) {
        this.stockPriceList = stockPriceList;
    }

    // Add any other properties as needed
}
