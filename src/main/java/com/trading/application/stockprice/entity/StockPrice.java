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


/**
 * The type Stock price.
 */
public class StockPrice implements Serializable {
    /**
     * The Open price.
     */
    @JsonProperty("1. open")
    private float openPrice;
    /**
     * The High price.
     */
    @JsonProperty("2. high")
    private float highPrice;
    /**
     * The Low price.
     */
    @JsonProperty("3. low")
    private float lowPrice;
    /**
     * The Close price.
     */
    @JsonProperty("4. close")
    private float closePrice;
    /**
     * The Stock date.
     */
    private Date stockDate;
    /**
     * The Volume.
     */
    @JsonProperty("5. volume")
    private long volume;

    /**
     * Instantiates a new Stock price.
     */
    public StockPrice() {

    }

    /**
     * Instantiates a new Stock price.
     *
     * @param openPrice  the open price
     * @param highPrice  the high price
     * @param lowPrice   the low price
     * @param closePrice the close price
     * @param stockDate  the stock date
     * @param volume     the volume
     */
    public StockPrice(float openPrice, float highPrice, float lowPrice, float closePrice, Date stockDate, long volume) {
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.stockDate = stockDate;
        this.volume = volume;
    }

    /**
     * Gets low price.
     *
     * @return the low price
     */
    public float getLowPrice() {
        return lowPrice;
    }

    /**
     * Sets low price.
     *
     * @param lowPrice the low price
     */
    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
    }


    /**
     * Gets stock date.
     *
     * @return the stock date
     */
    public Date getStockDate() {
        return stockDate;
    }

    /**
     * Sets stock date.
     *
     * @param stockDate the stock date
     */
    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }


    /**
     * Gets open price.
     *
     * @return the open price
     */
    public float getOpenPrice() {
        return openPrice;
    }

    /**
     * Sets open price.
     *
     * @param openPrice the open price
     */
    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    /**
     * Gets high price.
     *
     * @return the high price
     */
    public float getHighPrice() {
        return highPrice;
    }

    /**
     * Sets high price.
     *
     * @param highPrice the high price
     */
    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    /**
     * Gets close price.
     *
     * @return the close price
     */
    public float getClosePrice() {
        return closePrice;
    }

    /**
     * Sets close price.
     *
     * @param closePrice the close price
     */
    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    /**
     * Gets volume.
     *
     * @return the volume
     */
    public long getVolume() {
        return volume;
    }

    /**
     * Sets volume.
     *
     * @param volume the volume
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }








}