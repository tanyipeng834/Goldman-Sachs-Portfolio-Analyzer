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
 * The {@code PortfolioStockService} class provides services related to portfolio stocks.
 */
@Service
public class PortfolioStockService {

    /**
     * The {@code PortfolioStockRepository} is responsible for handling interactions with portfolio stock data.
     */
    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    /**
     * Add a new stock to the portfolio.
     *
     * @param portfolioId    The ID of the portfolio.
     * @param userId         The ID of the user.
     * @param stockTicker    The stock ticker symbol.
     * @param portfolioStock The portfolio stock to add.
     * @param request        The HTTP request.
     * @return A message indicating the result of the operation.
     * @throws ExecutionException   If an execution exception occurs.
     * @throws InterruptedException If an interruption occurs.
     */
    public String addNewStock(String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.addNewStock(portfolioId, userId, stockTicker, portfolioStock, request);
    }

    /**
     * Update an existing stock in the portfolio.
     *
     * @param indexToUpdate  The index of the stock to update.
     * @param portfolioId    The ID of the portfolio.
     * @param userId         The ID of the user.
     * @param stockTicker    The stock ticker symbol.
     * @param portfolioStock The portfolio stock with updated information.
     * @param request        The HTTP request.
     * @return A message indicating the result of the operation.
     * @throws ExecutionException   If an execution exception occurs.
     * @throws InterruptedException If an interruption occurs.
     */
    public String updateStock(int indexToUpdate, String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.updateStock(indexToUpdate, portfolioId, userId, stockTicker, portfolioStock, request);
    }

    /**
     * Delete specific stocks from the portfolio.
     *
     * @param portfolioId    The ID of the portfolio.
     * @param userId         The ID of the user.
     * @param stocksToDelete A map of stock tickers and indices to delete.
     * @param request        The HTTP request.
     * @return A message indicating the result of the operation.
     * @throws ExecutionException   If an execution exception occurs.
     * @throws InterruptedException If an interruption occurs.
     */
    public String deleteStock(String portfolioId, String userId, Map<String, List<Integer>> stocksToDelete, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.deleteStock(portfolioId, userId, stocksToDelete, request);
    }

}
