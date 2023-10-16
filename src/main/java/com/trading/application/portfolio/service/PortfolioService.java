package com.trading.application.portfolio.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FirestoreException;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.entity.PortfolioStocksRequest;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;

import com.trading.application.stock.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import com.trading.application.stock.entity.Stock;


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

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisTemplate<String,Object> template;

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

    public ResponseEntity<String> updatePortfolio(Portfolio portfolio) throws ExecutionException, InterruptedException {
//        return portfolioRepo.updatePortfolio(portfolio);
        try {
            String result = portfolioRepo.updatePortfolio(portfolio);
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating " +
                    "portfolio.");
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
//            if(stocksToDelete != null) {
//                portfolioStockService.deleteStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), stocksToDelete, request);
//            }
//
//            portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "public", portfolioStocksRequest.getIsPublic());
//
//            // recalculate portfolio value
//            if(stocksToUpdate!=null | stocksToAdd!=null | stocksToDelete!=null) {
//                float newPortfolioValue = portfolioRepo.calculatePortfolioValue(portfolioStocksRequest.getPortfolioId());
//                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "portfolioValue", newPortfolioValue);
//            }
//
//            return ResponseEntity.ok("Portfolio updated successfully.");
//
//        } catch (InterruptedException | ExecutionException | FirestoreException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting portfolio.");
//        }
//
//    }

    // get all portfolios of a customer
    public List<Portfolio> getAllPortfolios(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPortfolios(userId);
    }

    // get sectors of all stocks in a portfolio
    public Map<String, Integer> getSectorsByPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {

        Portfolio portfolio = portfolioRepo.getPortfolio(portfolioId);

//        CollectionReference stockColRef = firestore.collection("stock");

        Map<String, Integer> sectorCounts = new HashMap<>(); // Map to store sector counts

        if (portfolio != null) {
            Map<String, List<PortfolioStock>> myStocks = portfolio.getPortStock();

            if (!myStocks.isEmpty()) {
                Set<String> stockKeys = myStocks.keySet();

                for (String stockTicker : stockKeys) {

                    String key = "companyOverview::" + stockTicker;

                    Object value = template.opsForValue().get(key);

                    if (value == null) {
                        stockService.getStockOverview(stockTicker);
                        value = template.opsForValue().get(key);
                    }

                    Stock stock = (Stock) value;
                    String sector = stock.getSector();

                    // Update the sector counts in the map
                    sectorCounts.put(sector, sectorCounts.getOrDefault(sector, 0) + 1);

                }
                return sectorCounts;
            }
        }
        return null;

    }

    // get sectors of all stocks a user has
    public Map<String, Integer> getSectorsByUserId(String userId) throws ExecutionException, InterruptedException {

        List<Portfolio> allPortfolios = portfolioRepo.getAllPortfolios(userId);

        Map<String, Integer> allSectorCounts = new HashMap<>(); // Map to store all sector counts

        if (allPortfolios != null) {

            for (Portfolio portfolio : allPortfolios) {
                String portfolioId = portfolio.getPortfolioId();
                Map<String, Integer> sectorCounts = getSectorsByPortfolioId(portfolioId);

                if (sectorCounts != null) {
                    // Update allSectorCounts with sectorCounts
                    for (Map.Entry<String, Integer> entry : sectorCounts.entrySet()) {
                        String sector = entry.getKey();
                        int count = entry.getValue();
                        allSectorCounts.put(sector, allSectorCounts.getOrDefault(sector, 0) + count);
                    }
                }
            }
            return allSectorCounts;
        }
        return null;

    }

    public float getTotalPortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.calculatePortfolioValue(portfolioId);
    }

    public ArrayList<Portfolio> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPublicPortfolios();
    }


}
