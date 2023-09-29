package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;

import java.util.List;

public class PortfolioStocksRequest {
    //    private Map<String, List<PortfolioStock>> added;
//    private Map<String, List<PortfolioStock>> deleted;
//    private Map<String, List<PortfolioStock>> updated;
    private List<PortfolioStock> added;
    private List<PortfolioStock> deleted;
    private List<PortfolioStock> updated;

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
