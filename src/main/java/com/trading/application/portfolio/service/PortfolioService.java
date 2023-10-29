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
 * The type Portfolio service.
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
     * Create portfolio response entity.
     *
     * @param portfolio the portfolio
     * @param request   the request
     * @return the response entity
     */
    public ResponseEntity<String> createPortfolio(Portfolio portfolio, HttpServletRequest request)  {
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
     * Gets portfolio.
     *
     * @param portfolioId the portfolio id
     * @return the portfolio
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getPortfolio(portfolioId);
    }

    /**
     * Delete portfolio response entity.
     *
     * @param portfolioId the portfolio id
     * @return the response entity
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
     * Update portfolio response entity.
     *
     * @param portfolio the portfolio
     * @param request   the request
     * @return the response entity
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     * Gets all portfolios.
     *
     * @param userId the user id
     * @return the all portfolios
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public List<Portfolio> getAllPortfolios(String userId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPortfolios(userId);
    }

    /**
     * Gets sectors by portfolio id.
     *
     * @param portfolioId the portfolio id
     * @return the sectors by portfolio id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     * @param userId the user id
     * @return the sectors by user id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     * Rebalance portfolio.
     *
     * @param portfolio     the portfolio
     * @param remoteAddress the remote address
     * @return the portfolio
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
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
     * Rebalance value.
     *
     * @param stockTime     the stock time
     * @param portMap       the port map
     * @param portfolio     the portfolio
     * @param remoteAddress the remote address
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
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
     * Rebalance stock float.
     *
     * @param portStock        the port stock
     * @param currentPortValue the current port value
     * @param portfolio        the portfolio
     * @param remoteAddress    the remote address
     * @return the float
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     * Gets total portfolio value.
     *
     * @param portfolioId the portfolio id
     * @return the total portfolio value
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public float getTotalPortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.calculatePortfolioValue(portfolioId);
    }

    /**
     * Gets all public portfolios.
     *
     * @return the all public portfolios
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public ArrayList<Portfolio> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return portfolioRepo.getAllPublicPortfolios();
    }


}