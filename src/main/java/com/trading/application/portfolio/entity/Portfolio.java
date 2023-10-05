package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Portfolio {

    private String portfolioId;
    private String portfolioName;
    private String portfolioDescription;
    private float portfolioValue;
    private float unrealisedPnL;
    private String dateCreated;
    private int capital;
    private boolean isPublic = true;
    private Map<String,Integer> countryExposure;

    private Map<String, List<PortfolioStock>> portStock;

    public Map<String, List<PortfolioStock>> getPortStock() {
        return portStock;
    }

    public void setPortStock(Map<String, List<PortfolioStock>> portStock) {
        this.portStock = portStock;
    }

    private String userId;

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

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

    public float getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(float portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public float getUnrealisedPnL() {
        return unrealisedPnL;
    }

    public void setUnrealisedPnL(float unrealisedPnL) {
        this.unrealisedPnL = unrealisedPnL;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Map<String, Integer> getCountryExposure() {
        return countryExposure;
    }

    public void setCountryExposure(Map<String, Integer> countryExposure) {
        this.countryExposure = countryExposure;
    }
}
