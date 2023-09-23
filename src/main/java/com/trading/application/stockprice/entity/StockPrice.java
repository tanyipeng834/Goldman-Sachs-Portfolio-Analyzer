package com.trading.application.stockprice.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@RequiredArgsConstructor
@Data

@RedisHash("StockPrice")
@Component
public class StockPrice implements Serializable {
    // will be cached. doing daily first
    private String stockTicker;
    // date that api is called under [Meta Data] [" 3. Last Refreshed]
    private String updatedDate;

    private String dailyPrice;

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(String dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}



