package com.trading.application.portfoliostock.entity;

import org.springframework.stereotype.Component;

/**
 * The type Portfolio stock.
 */
@Component
public class PortfolioStock {

    /**
     * The Quantity.
     */
    private float quantity;
    /**
     * The Date bought.
     */
    private String dateBought;
    /**
     * The Stock bought price.
     */
    private float stockBoughtPrice;
    /**
     * The Allocation.
     */
    private float allocation;

    /**
     * Instantiates a new Portfolio stock.
     *
     * @param quantity         the quantity
     * @param dateBought       the date bought
     * @param stockBoughtPrice the stock bought price
     * @param allocation       the allocation
     */
    public PortfolioStock(int quantity, String dateBought, float stockBoughtPrice, float allocation) {
        this.quantity = quantity;
        this.dateBought = dateBought;
        this.stockBoughtPrice = stockBoughtPrice;
        this.allocation = allocation;
    }

    /**
     * Instantiates a new Portfolio stock.
     *
     * @param other the other
     */
    public PortfolioStock(PortfolioStock other) {
        this.quantity = other.quantity;
        this.dateBought = other.dateBought;
        this.stockBoughtPrice = other.stockBoughtPrice;
        this.allocation = other.allocation;
    }

    /**
     * Instantiates a new Portfolio stock.
     */
    public PortfolioStock() {
    }

    /**
     * Gets allocation.
     *
     * @return the allocation
     */
    public float getAllocation() {
        return allocation;
    }

    /**
     * Sets allocation.
     *
     * @param allocation the allocation
     */
    public void setAllocation(float allocation) {
        this.allocation = allocation;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets stock bought price.
     *
     * @return the stock bought price
     */
    public float getStockBoughtPrice() {
        return stockBoughtPrice;
    }

    /**
     * Sets stock bought price.
     *
     * @param stockBoughtPrice the stock bought price
     */
    public void setStockBoughtPrice(float stockBoughtPrice) {
        this.stockBoughtPrice = stockBoughtPrice;
    }

    /**
     * Gets date bought.
     *
     * @return the date bought
     */
    public String getDateBought() {
        return dateBought;
    }

    /**
     * Sets date bought.
     *
     * @param dateBought the date bought
     */
    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }
}
