package com.trading.application.portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.firestore.FirestoreException;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.entity.PortfolioStocksRequest;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.service.StockPriceService;
import com.trading.application.stockprice.service.StockPricesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    private StockPricesService stockPricesService = new StockPricesService()
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
                portfolioStockService.deleteStock(portfolioStocksRequest.getPortfolioId(), portfolioStocksRequest.getUserId(), stocksToDelete, request);
            }

            portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "public", portfolioStocksRequest.getIsPublic());

            // recalculate portfolio value
            if(stocksToUpdate!=null | stocksToAdd!=null | stocksToDelete!=null) {
                float newPortfolioValue = portfolioRepo.calculatePortfolioValue(portfolioStocksRequest.getPortfolioId());
                portfolioRepo.updatePortfolioField(portfolioStocksRequest.getPortfolioId(), "portfolioValue", newPortfolioValue);
            }

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

    public float getTotalPortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.calculatePortfolioValue(portfolioId);
    }

    public ArrayList<Portfolio> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPublicPortfolios();
    }



    public Map<String, List<PortfolioStock>> rebalance(Portfolio portfolio)

    {

        String startDate = portfolio.getDateCreated();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        YearMonth createyearMonth = YearMonth.parse(startDate, formatter);



        Map<String,List<PortfolioStock>> portMap= portfolio.getPortStock();





            // You can access the value associated with the key using map.get(key)

            rebalanceStock(createyearMonth,portMap);














    }


    public List<PortfolioStock> rebalanceStock(YearMonth stockTime,Map<String,List<PortfolioStock>> portMap) throws ExecutionException, InterruptedException, JsonProcessingException {
        YearMonth createdTime = stockTime;
         // Initialize the list
        YearMonth currentYearMonth = YearMonth.now();

        int counter = -1;
        while (createdTime.isBefore(currentYearMonth) || createdTime.equals(currentYearMonth)) {
            // Add logic here to process PortfolioStock and add to newPortfolioStock
            // For example, if PortfolioStock is retrieved from some source:
            // have to create a new list with the 1st object to take care of update
            float portValue = 0.0f;

            for (Map.Entry<String, List<PortfolioStock>> entry : portMap.entrySet()) {
                String key = entry.getKey();

                if(counter>=0){
                    // Get the previous quantity of the stock from the last rebalancing
                    PortfolioStock stock =entry.getValue().get(counter);
                    float idealAmount = stock.get












                }


                else{
                    // No need to add the stock in to the object
                    PortfolioStock stock =entry.getValue().get(0);
                    YearMonth futureTime = stockTime.plusMonths(3);
                    Object stockPrice =stockPricesService.getMonthlyPriceFromDate(key,String.valueOf(futureTime.getMonthValue()),String.valueOf(futureTime.getYear()));
                    if(stockPrice instanceof StockPrice){
                        portValue+= ((StockPrice) stockPrice).getClosePrice()*stock.getQuantity();

                    }


                }










                // Process the value here
            }


            createdTime = createdTime.plusMonths(3);
            counter+=1;
        }






    }





}
