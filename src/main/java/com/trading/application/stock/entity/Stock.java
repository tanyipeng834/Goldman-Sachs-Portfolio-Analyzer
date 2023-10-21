package com.trading.application.stock.entity;

import com.trading.application.stockprice.entity.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Stock.
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
     * Instantiates a new Stock.
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
     * Instantiates a new Stock.
     *
     * @param stockTicker          the stock ticker
     * @param stockCurrentPrice    the stock current price
     * @param historicalStockPrice the historical stock price
     * @param lastRefreshed        the last refreshed
     */
    public Stock(String stockTicker, float stockCurrentPrice, ArrayList<StockPrice> historicalStockPrice, Date lastRefreshed) {
        this.stockTicker = stockTicker;
        //this.stockName = stockName;
        this.stockCurrentPrice = stockCurrentPrice;
        this.historicalStockPrice = historicalStockPrice;
        this.lastRefreshed = lastRefreshed;
    }


    /**
     * Gets historical stock price.
     *
     * @return the historical stock price
     */
    public ArrayList<StockPrice> getHistoricalStockPrice() {
        return historicalStockPrice;
    }

    /**
     * Sets historical stock price.
     *
     * @param historicalStockPrice the historical stock price
     */
    public void setHistoricalStockPrice(ArrayList<StockPrice> historicalStockPrice) {
        this.historicalStockPrice = historicalStockPrice;
    }

    /**
     * Gets last refreshed.
     *
     * @return the last refreshed
     */
    public Date getLastRefreshed() {
        return lastRefreshed;
    }

    /**
     * Sets last refreshed.
     *
     * @param lastRefreshed the last refreshed
     */
    public void setLastRefreshed(Date lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }




//    private ArrayList<Float> incomeStatement;
//    private ArrayList<Float> cashFlow;
//    private String companyOverview;
//    private String industry;
//    to put daily, weekly, and monthly

;

    /**
     * Gets stock ticker.
     *
     * @return the stock ticker
     */
    public String getStockTicker() {
        return stockTicker;
    }

    /**
     * Sets stock ticker.
     *
     * @param stockTicker the stock ticker
     */
    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    /**
     * Gets stock current price.
     *
     * @return the stock current price
     */
    public float getStockCurrentPrice() {
        return stockCurrentPrice;
    }

    /**
     * Sets stock current price.
     *
     * @param stockCurrentPrice the stock current price
     */
    public void setStockCurrentPrice(float stockCurrentPrice) {
        this.stockCurrentPrice = stockCurrentPrice;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets exchange.
     *
     * @return the exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * Sets exchange.
     *
     * @param exchange the exchange
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets sector.
     *
     * @return the sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * Sets sector.
     *
     * @param sector the sector
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * Gets industry.
     *
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * Sets industry.
     *
     * @param industry the industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * Gets market capitalization.
     *
     * @return the market capitalization
     */
    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    /**
     * Sets market capitalization.
     *
     * @param marketCapitalization the market capitalization
     */
    public void setMarketCapitalization(String marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

//    private String Exchange;
//    private String Currency;
//    private String Country;
//    private String Sector;
//    private String Industry;
//    private String MarketCapitalization;
//     "Exchange": "NYSE",
//             "Currency": "USD",
//             "Country": "USA",
//             "Sector": "TECHNOLOGY",
//             "Industry": "COMPUTER & OFFICE EQUIPMENT",
//             "MarketCapitalization": "133835899000",
//             "EBITDA": "12985000000",
//             "PERatio": "62.78",
//             "PEGRatio": "1.276",
//             "BookValue": "24.37",
//             "DividendPerShare": "6.61",
//             "DividendYield": "0.0451",
//             "EPS": "2.34",
//             "RevenuePerShareTTM": "66.75",
//             "ProfitMargin": "0.0335",
//             "OperatingMarginTTM": "0.141",
//             "ReturnOnAssetsTTM": "0.0411",
//             "ReturnOnEquityTTM": "0.104",
//             "RevenueTTM": "60524999000",
//             "GrossProfitTTM": "32688000000",
//             "DilutedEPSTTM": "2.34",
//             "QuarterlyEarningsGrowthYOY": "0.126",
//             "QuarterlyRevenueGrowthYOY": "-0.004",
//             "AnalystTargetPrice": "137.05",
//             "TrailingPE": "62.78",
//             "ForwardPE": "15.55",
//             "PriceToSalesRatioTTM": "2.108",
//             "PriceToBookRatio": "6.75",
//             "EVToRevenue": "2.969",
//             "EVToEBITDA": "25.81",
//             "Beta": "0.85",
//             "52WeekHigh": "151.93",
//             "52WeekLow": "110.02",
//             "50DayMovingAverage": "143.4",
//             "200DayMovingAverage": "135.68",
//             "SharesOutstanding": "911006000",
//             "DividendDate": "2023-09-09",
//             "ExDividendDate": "2023-08-09"
}
