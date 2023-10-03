package com.trading.application.portfolio.service;

import com.google.cloud.firestore.FirestoreException;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.entity.PortfolioStocksRequest;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepo = new PortfolioRepository();
    @Autowired
    private PortfolioStockService portfolioStockService = new PortfolioStockService();

    @Autowired
    private AccessLogService accessLogService = new AccessLogService();

    public ResponseEntity<String> createPortfolio(Portfolio portfolio, HttpServletRequest request) {
        try {
            String result = portfolioRepo.createPortfolio(portfolio);
            // add to access log after portfolio successfully created in firebase
            AccessLog accessLog = new AccessLog(portfolio.getUserId(),"CREATE", request.getRemoteAddr() , "Created Portfolio", LocalDateTime.now().toString(), true);
            accessLogService.addLog(accessLog);
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating portfolio.");
        }
    }

    public String addStock(String portfolioStockId,String portfolioId) throws ExecutionException,InterruptedException{
        return portfolioRepo.addStock(portfolioStockId,portfolioId);
    }

    // get a portfolio
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getPortfolio(portfolioId);
    }

    // delete a portfolio
    public ResponseEntity<String> deletePortfolio(String portfolioId) {
//        return portfolioRepo.deletePortfolio(portfolioId);
        try {
            String result = portfolioRepo.deletePortfolio(portfolioId);
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting portfolio.");
        }
    }

    // update a portfolio's Name
    public String updatePortfolioName(String portfolioId, String portfolioName) throws ExecutionException, InterruptedException {
        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioName", portfolioName);
    }

    // update a portfolio's Description
    public String updatePortfolioDescription(String portfolioId, String portfolioDescription) throws ExecutionException, InterruptedException {
        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioDescription", portfolioDescription);
    }

    public String updatePortfolio(PortfolioStocksRequest portfolioStocksRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {

        String portfolioName = portfolioStocksRequest.getPortfolioName();
        String portfolioDesc = portfolioStocksRequest.getPortfolioDescription();
        Map<String, PortfolioStock> stocksToAdd = portfolioStocksRequest.getAdd();
        Map<String, Map<String, PortfolioStock>> stocksToUpdate = portfolioStocksRequest.getUpdate();
        List<String> stocksToDelete = portfolioStocksRequest.getDelete();

        if(portfolioName != null){
            this.updatePortfolioName(portfolioStocksRequest.getPortfolioId(), portfolioName);
        }

        if(portfolioDesc != null){
            this.updatePortfolioDescription(portfolioStocksRequest.getPortfolioId(), portfolioDesc);
        }

        if(stocksToAdd != null) {
            for (Map.Entry<String, PortfolioStock> entry : stocksToAdd.entrySet()) {
                    PortfolioStock portfolioStock = entry.getValue();
                    portfolioStockService.addNewStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), portfolioStock, request);
            }
        }

        if(stocksToDelete != null) {
            for (String stockTicker: stocksToDelete) {
                portfolioStockService.deleteStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), stockTicker, request);
            }
        }

        if(stocksToUpdate != null) {
            for (Map.Entry<String, Map<String, PortfolioStock>> entry : stocksToUpdate.entrySet()) {
                String stockTicker = entry.getKey();

                for (Map.Entry<String, PortfolioStock> innerEntry : entry.getValue().entrySet()) {
                    String indexAsString = innerEntry.getKey();
                    int index = Integer.parseInt(indexAsString);

                    PortfolioStock stock = innerEntry.getValue();
                    portfolioStockService.updateStock(index, portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), stock, request);
                }
            }
        }

        return "works!!!";
    }

    // Increment a portfolio's Value
    public void incrementPortfolioValue(String portfolioId, float totalStockPrice) throws ExecutionException, InterruptedException {
        float portfolioVal = portfolioRepo.getPortfolio(portfolioId).getPortfolioValue();
        portfolioRepo.updatePortfolioField(portfolioId, "portfolioValue", portfolioVal + totalStockPrice);
    }

    // Decrement a portfolio's Value
    public void decrementPortfolioValue(String portfolioId, float totalStockPrice) throws ExecutionException, InterruptedException {
        float portfolioVal = portfolioRepo.getPortfolio(portfolioId).getPortfolioValue();
        portfolioRepo.updatePortfolioField(portfolioId, "portfolioValue", portfolioVal - totalStockPrice);
    }

    // get all portfolios of a customer
    public List<Portfolio> getAllPortfolios(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPortfolios(userId);
    }

    // get sectors of all stocks in a portfolio
    public Map<String, Integer> getSectorsByPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getSectorsByPortfolioId(portfolioId);
    }

    // get sectors of all stocks a user has
    public Map<String, Integer> getSectorsByUserId(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getSectorsByUserId(userId);
    }

    public Map<String, Integer> getCountriesByUserId(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getCountriesByUserId(userId);
    }


}
