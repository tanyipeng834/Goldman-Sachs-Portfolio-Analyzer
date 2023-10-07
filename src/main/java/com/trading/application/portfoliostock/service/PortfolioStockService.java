package com.trading.application.portfoliostock.service;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.repository.PortfolioStockRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PortfolioStockService {

    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    // NEW
    // add new stock to portStock
    public String addNewStock(String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.addNewStock(portfolioId, userId, stockTicker, portfolioStock, request);
    }

    //NEW
    public String updateStock(int indexToUpdate, String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.updateStock( indexToUpdate, portfolioId, userId, stockTicker, portfolioStock, request);
    }

    // delete stock from portStock
    public String deleteStock(String portfolioId, String userId, Map<String, List<Integer>> stocksToDelete, HttpServletRequest request) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.deleteStock(portfolioId, userId, stocksToDelete, request);
    }

}
