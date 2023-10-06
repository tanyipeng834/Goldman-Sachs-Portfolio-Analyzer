package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;

import java.util.List;
import java.util.Map;

public class PortfolioStocksRequest {

    private String portfolioId;

    private String userId;

    private String portfolioName;

    private String portfolioDescription;

    private Map<String, PortfolioStock> add;
    private Map<String, Map<String, PortfolioStock>> update;
    private List<String> delete;

    private Map<String, Map<String, PortfolioStock>> newdelete;

    private boolean isPublic;

    private int capital;

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

    public Map<String, PortfolioStock> getAdd() {
        return add;
    }

    public void setAdd(Map<String, PortfolioStock> add) {
        this.add = add;
    }

    public Map<String, Map<String, PortfolioStock>> getUpdate() {
        return update;
    }

    public void setUpdate(Map<String, Map<String, PortfolioStock>> update) {
        this.update = update;
    }

    public List<String> getDelete() {
        return delete;
    }

    public void setDelete(List<String> delete) {
        this.delete = delete;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Map<String, Map<String, PortfolioStock>> getNewdelete() {
        return newdelete;
    }

    public void setNewdelete(Map<String, Map<String, PortfolioStock>> newdelete) {
        this.newdelete = newdelete;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }
}
