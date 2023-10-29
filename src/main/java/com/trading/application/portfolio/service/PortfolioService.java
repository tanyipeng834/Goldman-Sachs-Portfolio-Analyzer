package com.trading.application.portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.firestore.FirestoreException;
import com.google.gson.Gson;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;

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


import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * Portfolio Service class for handling portfolio-related operations
 */
@Service
public class PortfolioService {

    /**
     * The Portfolio repo.
     */
    private PortfolioRepository portfolioRepo = new PortfolioRepository();

    /**
     * The Access log service.
     */
    @Autowired
    private AccessLogService accessLogService = new AccessLogService();

    /**
     * The Stock service.
     */
    @Autowired
    private StockService stockService;

    /**
     * The Template.
     */
    @Autowired
    private RedisTemplate<String, Object> template;

    /**
     * The Stock prices service.
     */
    @Autowired
    private StockPricesService stockPricesService;

    /**
     * The Topic.
     */
    @Autowired
    private ChannelTopic topic;

    /**
     * Creates a new portfolio.
     *
     * @param portfolio The portfolio object containing the data for the new portfolio.
     * @param request the request to retrieve the IP address of the client that makes the HTTP request
     * @return a `ResponseEntity` containing a `String` response, typically indicating the result
     *         of the portfolio creation operation.
     */
    public ResponseEntity<String> createPortfolio(Portfolio portfolio, HttpServletRequest request) {
        try {

            accessLogService.addLog(new AccessLog(portfolio.getUserId(), "CREATE", request.getRemoteAddr(), "Created Portfolio", LocalDateTime.now().toString(), true));

            if (portfolio.isRebalancing()) {
                rebalance(portfolio,request.getRemoteAddr());
            }

            String result = portfolioRepo.createPortfolio(portfolio);
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException |JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating portfolio.");
        }
    }

    /**
     * Get portfolio by portfolioId.
     *
     * @param portfolioId the portfolio id of the portfolio to be retrieved.
     * @return the portfolio object associated with the given portfolio id.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getPortfolio(portfolioId);
    }

    /**
     * Delete a portfolio based on a given portfolio id.
     *
     * @param portfolioId The portfolio id of the portfolio to delete.
     * @return a `ResponseEntity` containing a `String` response, indicating the result
     *         of the portfolio deletion operation.
     */
    public ResponseEntity<String> deletePortfolio(String portfolioId) {
        try {
            String result = portfolioRepo.deletePortfolio(portfolioId);
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException | FirestoreException  e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting portfolio.");
        }
    }

    /**
     * Update portfolio based on portfolio object.
     *
     * @param portfolio The Portfolio object containing the updated information for the portfolio.
     * @param request the request to retrieve the IP address of the client that makes the HTTP request.
     * @return A `ResponseEntity` containing a `String` response, indicating the result
     *         of the portfolio update operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public ResponseEntity<String> updatePortfolio(Portfolio portfolio, HttpServletRequest request) throws ExecutionException,
            InterruptedException {
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

    /**
     * Retrieves a list of portfolios based on the given user id.
     *
     * @param userId The user id of the user for whom the portfolios are to be retrieved.
     * @return A list of portfolio objects associated with the given user id.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public List<Portfolio> getAllPortfolios(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPortfolios(userId);
    }

    /**
     * Gets sectors by portfolio id.
     *
     * @param portfolioId The portfolio id of the portfolio for which sector information is to be retrieved.
     * @return A map where keys represent sector names and values represent the counts of stocks in each sector.
     *         Returns `null` if no data is available or the portfolio does not exist.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public Map<String, Integer> getSectorsByPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {

        Portfolio portfolio = portfolioRepo.getPortfolio(portfolioId);

        Map<String, Integer> sectorCounts = new HashMap<>(); // Map to store sector counts

        if (portfolio != null) {
            Map<String, List<PortfolioStock>> myStocks = portfolio.getPortStock();

            if (!myStocks.isEmpty()) {
                Set<String> stockKeys = myStocks.keySet();

                for (String stockTicker : stockKeys) {

                    String key = "companyOverview::" + stockTicker;

                    String sector = stockService.getStockOverview(stockTicker).getSector();
                    // Update the sector counts in the map
                    sectorCounts.put(sector, sectorCounts.getOrDefault(sector, 0) + 1);

                }
                return sectorCounts;
            }
        }
        return null;

    }

    /**
     * Gets sectors by user id.
     *
     * @param userId The user id of the user for whom sector information is to be retrieved.
     * @return A map where keys represent sector names and values represent the counts of stocks in each sector across all portfolios.
     *         Returns `null` if no data is available.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
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

    /**
     * Rebalances a portfolio based on its creation date and stock data.
     *
     * @param portfolio The portfolio object representing the portfolio to be rebalanced.
     * @param remoteAddress The remote address of the client initiating the rebalance.
     * @return The rebalanced portfolio object.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     * @throws JsonProcessingException the json processing exception
     */
    public Portfolio rebalance(Portfolio portfolio ,String remoteAddress) throws ExecutionException, InterruptedException, JsonProcessingException {
        String startDate = portfolio.getDateCreated();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth createYearMonth = YearMonth.parse(startDate, formatter);

        int year = createYearMonth.getYear();
        int month = createYearMonth.getMonthValue();

        YearMonth newYearMonth = YearMonth.of(year, month);

        Map<String, List<PortfolioStock>> portMap = portfolio.getPortStock();

        rebalanceValue(newYearMonth, portMap,portfolio,remoteAddress);

        return portfolio;
    }

    /**
     * Rebalances a portfolio based on its creation date and stock data.
     *
     * @param portfolio The portfolio object representing the portfolio to be rebalanced.
     * @param remoteAddress The remote address of the client initiating the rebalance.
     * @return The rebalanced portfolio object.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     * @throws JsonProcessingException the json processing exception
     */
    public void rebalanceValue(YearMonth stockTime, Map<String, List<PortfolioStock>> portMap,Portfolio portfolio,String remoteAddress) throws ExecutionException, InterruptedException, JsonProcessingException {
        YearMonth createdTime = stockTime;
        YearMonth currentYearMonth = YearMonth.now();
        int counter = -1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        while (createdTime.isBefore(currentYearMonth) || createdTime.equals(currentYearMonth)) {
            float portValue = 0.0f;
            List<PortfolioStock> portAllocation = new ArrayList<>();
            for (Map.Entry<String, List<PortfolioStock>> entry : portMap.entrySet()) {
                String key = entry.getKey();
                if (counter>=0) {
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

        }

    }


    /**
     * Rebalances individual stocks within a portfolio based on their allocation and current value.
     *
     * @param portStock A list of portfolio stock objects representing the stocks to be rebalanced.
     * @param currentPortValue The current total value of the portfolio.
     * @param portfolio The portfolio object representing the portfolio containing the stocks.
     * @param remoteAddress The remote address of the client initiating the rebalance.
     * @return The new total value of the portfolio after rebalancing.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public float rebalanceStock(List<PortfolioStock> portStock,float currentPortValue,Portfolio portfolio,String remoteAddress) throws ExecutionException, InterruptedException {
        float newPortValue = 0.0f;
        List<String> portMapKeys = new ArrayList<>(portfolio.getPortStock().keySet());
        int counter =0;
        for (PortfolioStock stock : portStock)
        {
            float idealAmount = stock.getAllocation() * currentPortValue / stock.getStockBoughtPrice();
            DecimalFormat df = new DecimalFormat("0.00");

            String formattedIdealAmount = df.format(idealAmount);
            float formattedIdealAmountFloat = Float.parseFloat(formattedIdealAmount);

            if(stock.getQuantity()<idealAmount){
                float addedAmount = idealAmount-stock.getQuantity();

                AccessLog accessLog =new AccessLog(portfolio.getUserId(),"ADD", remoteAddress, "Added x" + addedAmount + " " + portMapKeys.get(counter) + " to Portfolio :" + portfolio.getPortfolioName(), stock.getDateBought(), true);
                Gson gson = new Gson();
                String logJson = gson.toJson(accessLog);

                template.convertAndSend(topic.getTopic(), logJson);
            } else if (idealAmount<stock.getQuantity()) {

                float subtractedAmount = idealAmount-stock.getQuantity();
                AccessLog accessLog =new AccessLog(portfolio.getUserId(),"DELETE", remoteAddress, "Deleted x" + subtractedAmount + " " + portMapKeys.get(counter) + " from Portfolio : " + portfolio.getPortfolioName(), stock.getDateBought(), true);

                Gson gson = new Gson();
                String logJson = gson.toJson(accessLog);

                template.convertAndSend(topic.getTopic(), logJson);

            }

            stock.setQuantity(formattedIdealAmountFloat);
            newPortValue += formattedIdealAmountFloat*stock.getStockBoughtPrice();
            // Still have to add the add /delete into the Access Log
            counter +=1;


        }
        return newPortValue;
    }

    /**
     * Retrieves the total value of a portfolio based on the provided portfolio id.
     *
     * @param portfolioId The portfolio id of the portfolio for which the total value is requested.
     * @return The total value of the portfolio.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public float getTotalPortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.calculatePortfolioValue(portfolioId);
    }

    /**
     * Retrieves a list of all public portfolios.
     *
     * @return An Arraylist of portfolio objects representing public portfolios.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    public ArrayList<Portfolio> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPublicPortfolios();
    }


}