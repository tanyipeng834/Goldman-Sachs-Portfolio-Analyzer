package com.trading.application.portfolio.entity;

/**
 * The type Portfolio request.
 */
public class PortfolioRequest {
    /**
     * The Portfolio name.
     */
    private String portfolioName;
    /**
     * The Portfolio description.
     */
    private String portfolioDescription;
    /**
     * The Portfolio stocks.
     */
    private PortfolioStocksRequest portfolioStocks;

    /**
     * Gets portfolio name.
     *
     * @return the portfolio name
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * Sets portfolio name.
     *
     * @param portfolioName the portfolio name
     */
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    /**
     * Gets portfolio description.
     *
     * @return the portfolio description
     */
    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    /**
     * Sets portfolio description.
     *
     * @param portfolioDescription the portfolio description
     */
    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
    }

    /**
     * Gets portfolio stocks.
     *
     * @return the portfolio stocks
     */
    public PortfolioStocksRequest getPortfolioStocks() {
        return portfolioStocks;
    }

    /**
     * Sets portfolio stocks.
     *
     * @param portfolioStocks the portfolio stocks
     */
    public void setPortfolioStocks(PortfolioStocksRequest portfolioStocks) {
        this.portfolioStocks = portfolioStocks;
    }
}
