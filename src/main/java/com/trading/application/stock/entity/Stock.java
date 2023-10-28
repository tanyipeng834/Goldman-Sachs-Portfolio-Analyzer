package com.trading.application.stock.entity;

import com.trading.application.stockprice.entity.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Entity class representing stock information.
 */
@RequiredArgsConstructor
//@Data
//
@RedisHash("Stock")
@Component
public class Stock implements Serializable {

    /**
     * The Stock ticker.
     */
    private String stockTicker;
    /**
     * The Stock current price.
     */
    private float stockCurrentPrice;
    /**
     * The Historical stock price.
     */
    private ArrayList<StockPrice> historicalStockPrice;
    /**
     * The Last refreshed.
     */
    private Date lastRefreshed;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Exchange.
     */
    private String exchange;
    /**
     * The Currency.
     */
    private String currency;
    /**
     * The Country.
     */
    private String country;
    /**
     * The Sector.
     */
    private String sector;
    /**
     * The Industry.
     */
    private String industry;
    /**
     * The Market capitalization.
     */
    private String marketCapitalization;

    /**
     * This is a constructor that instantiates a new Stock.
     *
     * @param description          the description
     * @param exchange             the exchange
     * @param currency             the currency
     * @param country              the country
     * @param sector               the sector
     * @param industry             the industry
     * @param marketCapitalization the market capitalization
     */
    public Stock(String description, String exchange, String currency, String country, String sector, String industry, String marketCapitalization) {
        this.description = description;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.marketCapitalization = marketCapitalization;
    }

    /**
     * This is a constructor that instantiates a new Stock.
     *
     * @param stockTicker          the stock ticker
     * @param stockCurrentPrice    the stock current price
     * @param historicalStockPrice the historical stock price
     * @param lastRefreshed        the last refreshed
     */
    public Stock(String stockTicker, float stockCurrentPrice, ArrayList<StockPrice> historicalStockPrice, Date lastRefreshed) {
        this.stockTicker = stockTicker;
        this.stockCurrentPrice = stockCurrentPrice;
        this.historicalStockPrice = historicalStockPrice;
        this.lastRefreshed = lastRefreshed;
    }


    /**
     * This is a getter method to access the {@code Stock} historical stock price.
     *
     * @return the {@code Stock} historical stock price
     */
    public ArrayList<StockPrice> getHistoricalStockPrice() {
        return historicalStockPrice;
    }

    /**
     * This is a setter method that sets the {@code Stock} historical stock price.
     *
     * @param historicalStockPrice the historical stock price
     */
    public void setHistoricalStockPrice(ArrayList<StockPrice> historicalStockPrice) {
        this.historicalStockPrice = historicalStockPrice;
    }

    /**
     * This is a getter method that gets the {@code Stock} last refreshed.
     *
     * @return the last refreshed
     */
    public Date getLastRefreshed() {
        return lastRefreshed;
    }

    /**
     * This is a setter method that sets the {@code Stock} last refreshed.
     *
     * @param lastRefreshed the last refreshed
     */
    public void setLastRefreshed(Date lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    /**
     * This is a getter method to get a stock ticker.
     *
     * @return the stock ticker of a {@code Stock}
     */
    public String getStockTicker() {
        return stockTicker;
    }

    /**
     * This a setter method that sets the stock ticker.
     *
     * @param stockTicker the stock ticker
     */
    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    /**
     * This is a getter method that gets the {@code Stock} current price.
     *
     * @return the {@code Stock} current price
     */
    public float getStockCurrentPrice() {
        return stockCurrentPrice;
    }

    /**
     * This is a setter method that sets the {@code Stock} current price.
     *
     * @param stockCurrentPrice the stock current price
     */
    public void setStockCurrentPrice(float stockCurrentPrice) {
        this.stockCurrentPrice = stockCurrentPrice;
    }

    /**
     * This is a getter method that gets the {@code Stock} description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * This is a setter method that sets the {@code Stock} description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This is a getter method that gets the {@code Stock} exchange.
     *
     * @return the exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * This is a setter method that sets the {@code Stock} exchange.
     *
     * @param exchange the exchange
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * This is a getter method that gets the {@code Stock} currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * This is a setter method that sets the {@code Stock} currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * This is a getter method that gets the country the {@code Stock} belongs to.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * This is a setter method that sets the country the {@code Stock} belongs to.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This is a getter method that gets the sector the {@code Stock} belongs to.
     *
     * @return the sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * This is a setter method that sets the sector the {@code Stock} belongs to.
     *
     * @param sector the sector
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * This is a getter method that gets the industry the {@code Stock} belongs to.
     *
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * This is a setter method that sets the industry the {@code Stock} belongs to.
     *
     * @param industry the industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * This is a getter method that gets the {@code Stock} market capitalization.
     *
     * @return the market capitalization
     */
    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    /**
     * This is a setter method that sets the market capitalization of the {@code Stock}.
     *
     * @param marketCapitalization the market capitalization
     */
    public void setMarketCapitalization(String marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

}
