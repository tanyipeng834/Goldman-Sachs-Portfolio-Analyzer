package com.trading.application.portfoliostock.service;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.repository.PortfolioStockRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * The type Portfolio stock service.
 */
@Service
public class PortfolioStockService {

    /**
     * The Portfolio stock repository.
     */
    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    /**
     * Add new stock string.
     *
     * @param portfolioId    the portfolio id
     * @param userId         the user id
     * @param stockTicker    the stock ticker
     * @param portfolioStock the portfolio stock
     * @param request        the request
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String addNewStock(String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.addNewStock(portfolioId, userId, stockTicker, portfolioStock, request);
    }

    /**
     * Update stock string.
     *
     * @param indexToUpdate  the index to update
     * @param portfolioId    the portfolio id
     * @param userId         the user id
     * @param stockTicker    the stock ticker
     * @param portfolioStock the portfolio stock
     * @param request        the request
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String updateStock(int indexToUpdate, String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.updateStock( indexToUpdate, portfolioId, userId, stockTicker, portfolioStock, request);
    }

    /**
     * Delete stock string.
     *
     * @param portfolioId    the portfolio id
     * @param userId         the user id
     * @param stocksToDelete the stocks to delete
     * @param request        the request
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String deleteStock(String portfolioId, String userId, Map<String, List<Integer>> stocksToDelete, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.deleteStock(portfolioId, userId, stocksToDelete, request);
    }

}
