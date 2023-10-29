package com.trading.application.portfoliostock.entity;

import org.springframework.stereotype.Component;

/**
 * The {@code PortfolioStock} class represents a stock in a portfolio. It includes details such as
 * quantity, date bought, stock bought price, and allocation.
 */
@Component
public class PortfolioStock {

    /**
     * The {@code quantity} of the stock in the portfolio.
     */
    private float quantity;

    /**
     * The {@code dateBought} represents the date when the stock was bought.
     */
    private String dateBought;

    /**
     * The {@code stockBoughtPrice} indicates the price at which the stock was bought.
     */
    private float stockBoughtPrice;

    /**
     * The {@code allocation} signifies the allocation of this stock in the portfolio.
     */
    private float allocation;

    /**
     * Constructs a new {@code PortfolioStock} with the provided details.
     *
     * @param quantity         The quantity of the stock.
     * @param dateBought       The date when the stock was bought.
     * @param stockBoughtPrice The price at which the stock was bought.
     * @param allocation       The allocation of this stock.
     */
    public PortfolioStock(float quantity, String dateBought, float stockBoughtPrice, float allocation) {
        this.quantity = quantity;
        this.dateBought = dateBought;
        this.stockBoughtPrice = stockBoughtPrice;
        this.allocation = allocation;
    }

    /**
     * Constructs a new {@code PortfolioStock} by copying the details from another {@code PortfolioStock} object.
     *
     * @param other Another {@code PortfolioStock} object to copy details from.
     */
    public PortfolioStock(PortfolioStock other) {
        this.quantity = other.quantity;
        this.dateBought = other.dateBought;
        this.stockBoughtPrice = other.stockBoughtPrice;
        this.allocation = other.allocation;
    }

    /**
     * Constructs an empty {@code PortfolioStock} object with default values.
     */
    public PortfolioStock() {
    }

    /**
     * Gets the allocation of this stock in the portfolio.
     *
     * @return The allocation value.
     */
    public float getAllocation() {
        return allocation;
    }

    /**
     * Sets the allocation of this stock in the portfolio.
     *
     * @param allocation The allocation value to set.
     */
    public void setAllocation(float allocation) {
        this.allocation = allocation;
    }

    /**
     * Gets the quantity of the stock in the portfolio.
     *
     * @return The {@code quantity} value.
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the stock in the portfolio.
     *
     * @param quantity The quantity value to set.
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price at which the stock was bought.
     *
     * @return The {@code stockBoughtPrice} value.
     */
    public float getStockBoughtPrice() {
        return stockBoughtPrice;
    }

    /**
     * Sets the price at which the stock was bought.
     *
     * @param stockBoughtPrice The stock bought price value to set.
     */
    public void setStockBoughtPrice(float stockBoughtPrice) {
        this.stockBoughtPrice = stockBoughtPrice;
    }

    /**
     * Gets the date when the stock was bought.
     *
     * @return The {@code dateBought} value.
     */
    public String getDateBought() {
        return dateBought;
    }

    /**
     * Sets the date when the stock was bought.
     *
     * @param dateBought The date bought value to set.
     */
    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }
}
