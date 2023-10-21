package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * The type Portfolio.
 */
@Component
public class Portfolio {
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
     * The Rebalancing.
     */
    private boolean rebalancing = false;

    /**
     * The Is public.
     */
    private boolean isPublic = true;

    /**
     * Is rebalancing boolean.
     *
     * @return the boolean
     */
    public boolean isRebalancing() {
        return rebalancing;
    }

    /**
     * Sets rebalancing.
     *
     * @param rebalancing the rebalancing
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
     * Gets port stock.
     *
     * @return the port stock
     */
    public Map<String, List<PortfolioStock>> getPortStock() {
        return portStock;
    }

    /**
     * Sets port stock.
     *
     * @param portStock the port stock
     */
    public void setPortStock(Map<String, List<PortfolioStock>> portStock) {
        this.portStock = portStock;
    }

    /**
     * The User id.
     */
    private String userId;

    /**
     * Get user id string.
     *
     * @return the string
     */
    public String getUserId(){
        return userId;
    }

    /**
     * Set user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId){
        this.userId = userId;
    }

    /**
     * Gets portfolio id.
     *
     * @return the portfolio id
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Sets portfolio id.
     *
     * @param portfolioId the portfolio id
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

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
     * Gets portfolio value.
     *
     * @return the portfolio value
     */
    public float getPortfolioValue() {
        return portfolioValue;
    }

    /**
     * Sets portfolio value.
     *
     * @param portfolioValue the portfolio value
     */
    public void setPortfolioValue(float portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    /**
     * Gets unrealised pn l.
     *
     * @return the unrealised pn l
     */
    public float getUnrealisedPnL() {
        return unrealisedPnL;
    }

    /**
     * Sets unrealised pn l.
     *
     * @param unrealisedPnL the unrealised pn l
     */
    public void setUnrealisedPnL(float unrealisedPnL) {
        this.unrealisedPnL = unrealisedPnL;
    }

    /**
     * Gets date created.
     *
     * @return the date created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets date created.
     *
     * @param dateCreated the date created
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets capital.
     *
     * @return the capital
     */
    public int getCapital() {
        return capital;
    }

    /**
     * Sets capital.
     *
     * @param capital the capital
     */
    public void setCapital(int capital) {
        this.capital = capital;
    }

    /**
     * Is public boolean.
     *
     * @return the boolean
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Sets public.
     *
     * @param aPublic the a public
     */
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    /**
     * Gets country exposure.
     *
     * @return the country exposure
     */
    public Map<String, Integer> getCountryExposure() {
        return countryExposure;
    }

    /**
     * Sets country exposure.
     *
     * @param countryExposure the country exposure
     */
    public void setCountryExposure(Map<String, Integer> countryExposure) {
        this.countryExposure = countryExposure;
    }
}
