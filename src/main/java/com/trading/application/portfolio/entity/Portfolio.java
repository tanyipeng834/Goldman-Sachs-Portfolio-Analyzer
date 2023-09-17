package com.trading.application.portfolio.entity;

import org.springframework.stereotype.Component;

@Component
public class Portfolio {

    private String portfolioId;
    private String portfolioName;
    private String portfolioDescription;
    private float portfolioValue;
    private float unrealisedPnL;
    private String dateCreated;

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




}
