package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;

import java.util.List;
import java.util.Map;

public class PortfolioStocksRequest {
    //    private Map<String, List<PortfolioStock>> added;
//    private Map<String, List<PortfolioStock>> deleted;
//    private Map<String, List<PortfolioStock>> updated;
    private List<PortfolioStock> added;
    private List<PortfolioStock> deleted;
    private List<PortfolioStock> updated;

    private String portfolioId;

    private String userId;

    private String portfolioName;

    private String portfolioDescription;

    private Map<String, PortfolioStock> addednew;
    private Map<String, Map<String, PortfolioStock>> updatednew;
    private List<String> deletednew;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Map<String, PortfolioStock> getAddednew() {
        return addednew;
    }

    public void setAddednew(Map<String, PortfolioStock> addednew) {
        this.addednew = addednew;
    }

    public Map<String, Map<String, PortfolioStock>> getUpdatednew() {
        return updatednew;
    }

    public void setUpdatednew(Map<String, Map<String, PortfolioStock>> updatednew) {
        this.updatednew = updatednew;
    }

    public List<String> getDeletednew() {
        return deletednew;
    }

    public void setDeletednew(List<String> deletednew) {
        this.deletednew = deletednew;
    }

    public List<PortfolioStock> getAdded() {
        return added;
    }

    public void setAdded(List<PortfolioStock> added) {
        this.added = added;
    }

    public List<PortfolioStock> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<PortfolioStock> deleted) {
        this.deleted = deleted;
    }

    public List<PortfolioStock> getUpdated() {
        return updated;
    }

    public void setUpdated(List<PortfolioStock> updated) {
        this.updated = updated;
    }
}
