package com.trading.application.stock.entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
//@Data
//
//@RedisHash("Stock")
@Component
public class Stock  {

    private String stockTicker;
    //private String stockName;
    private float stockCurrentPrice;
    private ArrayList<StockPrice> historicalStockPrice;
    private Date lastRefreshed;
    private String description;
    private String Exchange;
    private String Currency;
    private String Country;
    private String Sector;
    private String Industry;
    private String MarketCapitalization;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
