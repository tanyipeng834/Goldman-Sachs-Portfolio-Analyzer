package com.trading.application.portfolio.entity;

import com.trading.application.portfoliostock.entity.PortfolioStock;

import java.util.List;
import java.util.Map;

/**
 * The type Portfolio stocks request.
 */
public class PortfolioStocksRequest {

    /**
     * The Portfolio id.
     */
    private String portfolioId;
    /**
     * The User id.
     */
    private String userId;
    /**
     * The Portfolio name.
     */
    private String portfolioName;
    /**
     * The Portfolio description.
     */
    private String portfolioDescription;
    /**
     * The Add.
     */
    private Map<String, List<PortfolioStock>> add;
    /**
     * The Update.
     */
    private Map<String, Map<String, PortfolioStock>> update;
    /**
     * The Delete.
     */
    private Map<String, List<Integer>> delete;
    /**
     * The Is public.
     */
    private boolean isPublic;
    /**
     * The Capital.
     */
    private int capital;

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
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * Gets add.
     *
     * @return the add
     */
    public Map<String, List<PortfolioStock>> getAdd() {
        return add;
    }

    /**
     * Sets add.
     *
     * @param add the add
     */
    public void setAdd(Map<String, List<PortfolioStock>> add) {
        this.add = add;
    }

    /**
     * Gets update.
     *
     * @return the update
     */
    public Map<String, Map<String, PortfolioStock>> getUpdate() {
        return update;
    }

    /**
     * Sets update.
     *
     * @param update the update
     */
    public void setUpdate(Map<String, Map<String, PortfolioStock>> update) {
        this.update = update;
    }

    /**
     * Gets is public.
     *
     * @return the is public
     */
    public boolean getIsPublic() {
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
     * Gets delete.
     *
     * @return the delete
     */
    public Map<String, List<Integer>> getDelete() {
        return delete;
    }

    /**
     * Sets delete.
     *
     * @param delete the delete
     */
    public void setDelete(Map<String, List<Integer>> delete) {
        this.delete = delete;
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
}
