package com.trading.application.portfolio.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.firestore.FirestoreException;
import com.google.gson.Gson;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;

import com.trading.application.stock.service.StockService;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.service.StockPricesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import com.trading.application.stock.entity.Stock;


import java.text.DecimalFormat;
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
    private StockService stockService;

    @Autowired
    private RedisTemplate<String, Object> template;

    @Autowired
    private StockPricesService stockPricesService;




    @Autowired
    private ChannelTopic topic;



    public ResponseEntity<String> createPortfolio(Portfolio portfolio, HttpServletRequest request)  {
        try {

            // add to access log after portfolio successfully created in firebase
            accessLogService.addLog(new AccessLog(portfolio.getUserId(), "CREATE", request.getRemoteAddr(), "Created Portfolio", LocalDateTime.now().toString(), true));
            // May need to add some rebalancing logic

            if (portfolio.isRebalancing()) {
                rebalance(portfolio,request.getRemoteAddr());
            }
//
            String result = portfolioRepo.createPortfolio(portfolio);
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException |JsonProcessingException e) {
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
        } catch (InterruptedException | ExecutionException | FirestoreException  e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting portfolio.");
        }
    }

    public ResponseEntity<String> updatePortfolio(Portfolio portfolio, HttpServletRequest request) throws ExecutionException,
            InterruptedException {
//        return portfolioRepo.updatePortfolio(portfolio);
        try {
            String result = portfolioRepo.updatePortfolio(portfolio);

            if(portfolio.isRebalancing()){
                rebalance(portfolio,request.getRemoteAddr());
            }
            // add to access log after portfolio successfully updated in firebase
            accessLogService.addLog(new AccessLog(portfolio.getUserId(), "UPDATE", request.getRemoteAddr(), "Updated " +
                    "Portfolio", LocalDateTime.now().toString(), true));
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException | JsonProcessingException  e) {
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

    public Portfolio rebalance(Portfolio portfolio ,String remoteAddress) throws ExecutionException, InterruptedException, JsonProcessingException {
        String startDate = portfolio.getDateCreated();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth createYearMonth = YearMonth.parse(startDate, formatter);

        int year = createYearMonth.getYear();
        int month = createYearMonth.getMonthValue();

        YearMonth newYearMonth = YearMonth.of(year, month);

        Map<String, List<PortfolioStock>> portMap = portfolio.getPortStock();


        // You can access the value associated with the key using map.get(key)
        rebalanceValue(newYearMonth, portMap,portfolio,remoteAddress);

        return portfolio;


    }

    public void rebalanceValue(YearMonth stockTime, Map<String, List<PortfolioStock>> portMap,Portfolio portfolio,String remoteAddress) throws ExecutionException, InterruptedException, JsonProcessingException {
        YearMonth createdTime = stockTime;     // Initialize the list
        YearMonth currentYearMonth = YearMonth.now();
        int counter = -1;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        while (createdTime.isBefore(currentYearMonth) || createdTime.equals(currentYearMonth)) {
            // Add logic here to process PortfolioStock and add to newPortfolioStock        // For example, if PortfolioStock is retrieved from some source:
            // have to create a new list with the 1st object to take care of update
            float portValue = 0.0f;
            List<PortfolioStock> portAllocation = new ArrayList<>();
            for (Map.Entry<String, List<PortfolioStock>> entry : portMap.entrySet()) {
                String key = entry.getKey();
                // Get the previous quantity of the stock from the last rebalancing
                if (counter>=0) {
                    // Get the previous quantity
                    PortfolioStock stock = entry.getValue().get(counter);
                    Object currentStockPrice = stockPricesService.getMonthlyPriceFromDate(key, String.format("%02d", createdTime.getMonthValue()), String.valueOf(createdTime.getYear()));
                    if (currentStockPrice instanceof StockPrice) {
                        StockPrice stockPrice = (StockPrice) currentStockPrice; // Cast currentStockPrice to StockPrice
                        float currentPrice = stockPrice.getClosePrice();
                        PortfolioStock rebalancedStock = new PortfolioStock(stock);
                        rebalancedStock.setStockBoughtPrice(currentPrice);
                        rebalancedStock.setDateBought(createdTime.format(formatter));

                        entry.getValue().add(rebalancedStock);

                        portValue += currentPrice*rebalancedStock.getQuantity();
                        portAllocation.add(rebalancedStock);








                    }


                }


            }

            float newPortValue =rebalanceStock(portAllocation,portValue,portfolio,remoteAddress);
            portfolio.setPortfolioValue(newPortValue);


            createdTime = createdTime.plusMonths(3);
            counter += 1;

            // Process the value here
        }




    }


    public float rebalanceStock(List<PortfolioStock> portStock,float currentPortValue,Portfolio portfolio,String remoteAddress) throws ExecutionException, InterruptedException {
        float newPortValue = 0.0f;
        List<String> portMapKeys = new ArrayList<>(portfolio.getPortStock().keySet());
        int counter =0;
        for (PortfolioStock stock : portStock)
        {
            // Calculate the ideal amount
            float idealAmount = stock.getAllocation() * currentPortValue / stock.getStockBoughtPrice();

            // Create a DecimalFormat object to format the float to 2 decimal places
            DecimalFormat df = new DecimalFormat("0.00");


            // Format the idealAmount to a string with 2 decimal places
            String formattedIdealAmount = df.format(idealAmount);
            float formattedIdealAmountFloat = Float.parseFloat(formattedIdealAmount);

            if(stock.getQuantity()<idealAmount){
                float addedAmount = idealAmount-stock.getQuantity();



                AccessLog accessLog =new AccessLog(portfolio.getUserId(),"ADD", remoteAddress, "Added x" + addedAmount + " " + portMapKeys.get(counter) + " to Portfolio :" + portfolio.getPortfolioName(), LocalDateTime.now().toString(), true);
                Gson gson = new Gson();
                String logJson = gson.toJson(accessLog);

                template.convertAndSend(topic.getTopic(), logJson);
            } else if (idealAmount<stock.getQuantity()) {

                float subtractedAmount = idealAmount-stock.getQuantity();
                AccessLog accessLog =new AccessLog(portfolio.getUserId(),"DELETE", remoteAddress, "Deleted x" + subtractedAmount + " " + portMapKeys.get(counter) + " from Portfolio : " + portfolio.getPortfolioName(), LocalDateTime.now().toString(), true);

                Gson gson = new Gson();
                String logJson = gson.toJson(accessLog);

                template.convertAndSend(topic.getTopic(), logJson);



            }

            // Parse the formatted string back to a float if needed


            // Set the stock quantity to the formatted idealAmount
            System.out.println(formattedIdealAmountFloat);

            stock.setQuantity(formattedIdealAmountFloat);
            newPortValue += formattedIdealAmountFloat*stock.getStockBoughtPrice();
            // Still have to add the add /delete into the Access Log
            counter +=1;


        }
        return newPortValue;



    }








    public float getTotalPortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.calculatePortfolioValue(portfolioId);
    }

    public ArrayList<Portfolio> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPublicPortfolios();
    }


}