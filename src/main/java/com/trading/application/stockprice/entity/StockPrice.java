package com.trading.application.stockprice.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;




public class StockPrice implements Serializable {
    @JsonProperty("1. open")
    private float openPrice;
    @JsonProperty("2. high")
    private float highPrice;
    @JsonProperty("3. low")
    private float lowPrice;
    @JsonProperty("4. close")
    private float closePrice;
    private Date stockDate;
    @JsonProperty("5. volume")
    private long volume;

    public StockPrice() {

    }

    public StockPrice(float openPrice, float highPrice, float lowPrice, float closePrice, Date stockDate, long volume) {
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.stockDate = stockDate;
        this.volume = volume;
    }

    public float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
    }



    public Date getStockDate() {
        return stockDate;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }



    public float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }








}