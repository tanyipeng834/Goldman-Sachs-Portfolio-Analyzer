package com.trading.application.stock.entity;



import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
//@Data
//
@RedisHash("Stock")
@Component
public class Stock implements Serializable  {

    private String stockTicker;

    private float stockCurrentPrice;
    private ArrayList<StockPrice> historicalStockPrice;
    private Date lastRefreshed;
    public Stock(String stockTicker, float stockCurrentPrice, ArrayList<StockPrice> historicalStockPrice, Date lastRefreshed) {
        this.stockTicker = stockTicker;
        //this.stockName = stockName;
        this.stockCurrentPrice = stockCurrentPrice;
        this.historicalStockPrice = historicalStockPrice;
        this.lastRefreshed = lastRefreshed;
    }

    public ArrayList<StockPrice> getHistoricalStockPrice() {
        return historicalStockPrice;
    }

    public void setHistoricalStockPrice(ArrayList<StockPrice> historicalStockPrice) {
        this.historicalStockPrice = historicalStockPrice;
    }

    public Date getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(Date lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }




//    private ArrayList<Float> incomeStatement;
//    private ArrayList<Float> cashFlow;
//    private String companyOverview;
//    private String industry;
//    to put daily, weekly, and monthly

;    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

//    public String getStockName() {
//        return stockName;
//    }

//    public void setStockName(String stockName) {
//        this.stockName = stockName;
//    }

    public float getStockCurrentPrice() {
        return stockCurrentPrice;
    }

    public void setStockCurrentPrice(float stockCurrentPrice) {
        this.stockCurrentPrice = stockCurrentPrice;
    }
}
