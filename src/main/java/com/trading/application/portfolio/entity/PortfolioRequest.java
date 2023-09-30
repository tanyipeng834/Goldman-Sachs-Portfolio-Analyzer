package com.trading.application.portfolio.entity;

public class PortfolioRequest {
    private String portfolioName;
    private String portfolioDescription;
    private PortfolioStocksRequest portfolioStocks;

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
    }

    public PortfolioStocksRequest getPortfolioStocks() {
        return portfolioStocks;
    }

    public void setPortfolioStocks(PortfolioStocksRequest portfolioStocks) {
        this.portfolioStocks = portfolioStocks;
    }
}
