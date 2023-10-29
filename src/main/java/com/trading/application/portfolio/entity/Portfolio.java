package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Entity class representing portfolio information.
 */
@Component
public class Portfolio {

    /**
     * Returns a string representation of the Portfolio object.
     * @return A string representation of the Portfolio object.
     */
    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId='" + portfolioId + '\'' +
                ", portfolioName='" + portfolioName + '\'' +
                ", portfolioDescription='" + portfolioDescription + '\'' +
                ", portfolioValue=" + portfolioValue +
                ", unrealisedPnL=" + unrealisedPnL +
                ", dateCreated='" + dateCreated + '\'' +
                ", capital=" + capital +
                ", rebalancing=" + rebalancing +
                ", isPublic=" + isPublic +
                ", countryExposure=" + countryExposure +
                ", portStock=" + portStock +
                ", userId='" + userId + '\'' +
                '}';
    }

    /**
     * The Portfolio id.
     */
    private String portfolioId;
    /**
     * The Portfolio name.
     */
    private String portfolioName;
    /**
     * The Portfolio description.
     */
    private String portfolioDescription;
    /**
     * The Portfolio value.
     */
    private float portfolioValue;
    /**
     * The Unrealised pn l.
     */
    private float unrealisedPnL;
    /**
     * The Date created.
     */
    private String dateCreated;
    /**
     * The Capital.
     */
    private int capital;
    /**
     * The Rebalancing field.
     */
    private boolean rebalancing = false;

    /**
     * The Is publicf field.
     */
    private boolean isPublic = true;

    /**
     * This is a getter method that gets the {@code Portfolio} rebalancing field.
     *
     * @return the {@code Portfolio} rebalancing field.
     */
    public boolean isRebalancing() {
        return rebalancing;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} rebalancing field.
     *
     * @param rebalancing the rebalancing field.
     */
    public void setRebalancing(boolean rebalancing) {
        this.rebalancing = rebalancing;
    }

    /**
     * The Country exposure.
     */
    private Map<String,Integer> countryExposure;

    /**
     * The Port stock.
     */
    private Map<String, List<PortfolioStock>> portStock;

    /**
     * This is a getter method that gets the {@code Portfolio} port stock.
     *
     * @return the {@code Portfolio} port stock.
     */
    public Map<String, List<PortfolioStock>> getPortStock() {
        return portStock;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} port stock.
     *
     * @param portStock the port stock.
     */
    public void setPortStock(Map<String, List<PortfolioStock>> portStock) {
        this.portStock = portStock;
    }

    /**
     * The User id.
     */
    private String userId;

    /**
     * This is a getter method that gets the {@code Portfolio} user id.
     *
     * @return the {@code Portfolio} user id.
     */
    public String getUserId(){
        return userId;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} user id.
     *
     * @param userId the user id.
     */
    public void setUserId(String userId){
        this.userId = userId;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} portfolio id.
     *
     * @return the {@code Portfolio} portfolio id.
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} portfolio id.
     *
     * @param portfolioId the portfolio id.
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} portfolio name.
     *
     * @return the {@code Portfolio} portfolio name.
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} portfolio name.
     *
     * @param portfolioName the portfolio name.
     */
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} description.
     *
     * @return the {@code Portfolio} description.
     */
    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} portfolio description.
     *
     * @param portfolioDescription the portfolio description.
     */
    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} portfolio value.
     *
     * @return the {@code Portfolio} portfolio value.
     */
    public float getPortfolioValue() {
        return portfolioValue;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} portfolio value.
     *
     * @param portfolioValue the portfolio value.
     */
    public void setPortfolioValue(float portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} unrealised pnl.
     *
     * @return the {@code Portfolio} unrealised pnl.
     */
    public float getUnrealisedPnL() {
        return unrealisedPnL;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} unrealised pnl.
     *
     * @param unrealisedPnL the unrealised pnl.
     */
    public void setUnrealisedPnL(float unrealisedPnL) {
        this.unrealisedPnL = unrealisedPnL;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} date created.
     *
     * @return the {@code Portfolio} date created.
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} date created.
     *
     * @param dateCreated the date created
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} capital.
     *
     * @return the {@code Portfolio} capital.
     */
    public int getCapital() {
        return capital;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} capital.
     *
     * @param capital the capital
     */
    public void setCapital(int capital) {
        this.capital = capital;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} IsPublic.
     *
     * @return the {@code Portfolio} IsPublic
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} isPublic.
     *
     * @param aPublic the isPublic field
     */
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    /**
     * This is a getter method that gets the {@code Portfolio} country exposure.
     *
     * @return the country exposure
     */
    public Map<String, Integer> getCountryExposure() {
        return countryExposure;
    }

    /**
     * This is a setter method that sets the {@code Portfolio} country exposure.
     *
     * @param countryExposure the country exposure
     */
    public void setCountryExposure(Map<String, Integer> countryExposure) {
        this.countryExposure = countryExposure;
    }
}
