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

//    public ResponseEntity<String> updatePortfolio(PortfolioStocksRequest portfolioStocksRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {
//
//        try {
//            String portfolioName = portfolioStocksRequest.getPortfolioName();
//            String portfolioDesc = portfolioStocksRequest.getPortfolioDescription();
//            int capital = portfolioStocksRequest.getCapital();
//            Map<String, List<PortfolioStock>> stocksToAdd = portfolioStocksRequest.getAdd();
//            Map<String, Map<String, PortfolioStock>> stocksToUpdate = portfolioStocksRequest.getUpdate();
//            Map<String, List<Integer>> stocksToDelete = portfolioStocksRequest.getDelete();
//
//            if(portfolioName != null){
//                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "portfolioName", portfolioName);
//            }
//
//            if(portfolioDesc != null){
//                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "portfolioDescription", portfolioDesc);
//            }
//
//            if(capital != 0){
//                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "capital", capital);
//            }
//
//            if(stocksToAdd != null) {
//                for (Map.Entry<String, List<PortfolioStock>> entry : stocksToAdd.entrySet()) {
//                    List<PortfolioStock> value = entry.getValue();
//                    for (PortfolioStock stock : value) {
//                        portfolioStockService.addNewStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), stock, request);
//                    }
//                }
//            }
//
//            if(stocksToDelete != null) {
//                for (Map.Entry<String, List<Integer>> entry : stocksToDelete.entrySet()) {
//                    List<Integer> value = entry.getValue();
//                    for (Integer number : value) {
//                        System.out.println(number);
//                        portfolioStockService.deleteStock(number, portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), request);
//                    }
//                }
//            }
//
//            if(stocksToUpdate != null){
//                System.out.println(stocksToDelete);
//            }
//
//            if(stocksToUpdate != null) {
//                for (Map.Entry<String, Map<String, PortfolioStock>> entry : stocksToUpdate.entrySet()) {
//                    String stockTicker = entry.getKey();
//
//                    for (Map.Entry<String, PortfolioStock> innerEntry : entry.getValue().entrySet()) {
//                        String indexAsString = innerEntry.getKey();
//                        int index = Integer.parseInt(indexAsString);
//
//                        PortfolioStock stock = innerEntry.getValue();
//                        portfolioStockService.updateStock(index, portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), stock, request);
//                    }
//                }
//            }
//
//            portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "public", portfolioStocksRequest.getIsPublic());
//
//            return ResponseEntity.ok("Portfolio updated successfully.");
//
//        } catch (InterruptedException | ExecutionException | FirestoreException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting portfolio.");
//        }
//
//    }

    public ResponseEntity<String> updatePortfolio(PortfolioStocksRequest portfolioStocksRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {

        try {
            String portfolioName = portfolioStocksRequest.getPortfolioName();
            String portfolioDesc = portfolioStocksRequest.getPortfolioDescription();
            int capital = portfolioStocksRequest.getCapital();
            Map<String, List<PortfolioStock>> stocksToAdd = portfolioStocksRequest.getAdd();
            Map<String, Map<String, PortfolioStock>> stocksToUpdate = portfolioStocksRequest.getUpdate();
            Map<String, List<Integer>> stocksToDelete = portfolioStocksRequest.getDelete();

            if(portfolioName != null){
                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "portfolioName", portfolioName);
            }

            if(portfolioDesc != null){
                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "portfolioDescription", portfolioDesc);
            }

            if(capital != 0){
                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "capital", capital);
            }

            if(stocksToAdd != null) {
                for (Map.Entry<String, List<PortfolioStock>> entry : stocksToAdd.entrySet()) {
                    List<PortfolioStock> value = entry.getValue();
                    for (PortfolioStock stock : value) {
                        portfolioStockService.addNewStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), stock, request);
                    }
                }
            }



            if(stocksToUpdate != null){
                System.out.println(stocksToDelete);
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

            if(stocksToDelete != null) {
//                for (Map.Entry<String, List<Integer>> entry : stocksToDelete.entrySet()) {
//                    List<Integer> value = entry.getValue();
//                    for (Integer number : value) {
//                        System.out.println(number);
//                        portfolioStockService.deleteStock(number, portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), entry.getKey(), request);
//                    }
//                }
                portfolioStockService.deleteStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), stocksToDelete, request);
            }

            portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "public", portfolioStocksRequest.getIsPublic());

            return ResponseEntity.ok("Portfolio updated successfully.");

        } catch (InterruptedException | ExecutionException | FirestoreException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting portfolio.");
        }

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

    public int getTotalPortfolioValue(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getTotalPortfolioValue(userId);
    }


}
