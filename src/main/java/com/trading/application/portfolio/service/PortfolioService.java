package com.trading.application.portfolio.service;

import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.entity.PortfolioRequest;
import com.trading.application.portfolio.entity.PortfolioStocksRequest;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepo = new PortfolioRepository();
    @Autowired
    private PortfolioStockService portfolioStockService = new PortfolioStockService();

    @Autowired
    private AccessLogService accessLogService = new AccessLogService();

    // Create Portfolio
    public String createPortfolio(Portfolio portfolio) throws ExecutionException, InterruptedException {
        return portfolioRepo.createPortfolio(portfolio);
    }

    public String addStock(String portfolioStockId,String portfolioId) throws ExecutionException,InterruptedException{


        return portfolioRepo.addStock(portfolioStockId,portfolioId);
    }

    // get a portfolio
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.getPortfolio(portfolioId);
    }

    // delete a portfolio
    public String deletePortfolio(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioRepo.deletePortfolio(portfolioId);
    }
    // update entire portfolio. frontend will call this.
    public String updatePortfolio(PortfolioRequest portfolio, String portfolioId) throws ExecutionException, InterruptedException {

//        System.out.println(portfolio.getPortfolioStocks());
//        check if name changed
        if(portfolio.getPortfolioName()!=null){
            System.out.println("update name");
            updatePortfolioName(portfolioId, portfolio.getPortfolioName());

//            handle error
        }

//        check if description changed
        if(portfolio.getPortfolioDescription()!=null){
            System.out.println("update description");
            updatePortfolioDescription(portfolioId, portfolio.getPortfolioDescription());

//            handle error
        }

//        check if stocks changed
        if(portfolio.getPortfolioStocks()!=null){
            PortfolioStocksRequest portfolioStocks = portfolio.getPortfolioStocks();
            //gives a list of stocks in map string
            // there is a stock being updated/created/deleted
            System.out.println("update stocks");
            updatePortfolioStocks(portfolioId, portfolioStocks);


//            handle error
        }
        return "nondone";
//        return portfolioRepo.updatePortfolio(portfolioId, "portfolioName", portfolioName);
    }

    // update a portfolio's Name
    public String updatePortfolioName(String portfolioId, String portfolioName) throws ExecutionException, InterruptedException {
        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioName", portfolioName);
    }

    // update a portfolio's Description
    public String updatePortfolioDescription(String portfolioId, String portfolioDescription) throws ExecutionException, InterruptedException {
        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioDescription", portfolioDescription);
    }

    // update all portfolio stocks. calling the portfoliostock service n then repo bef calling port repo
    public String updatePortfolioStocks(String portfolioId, PortfolioStocksRequest portfolioStocks) throws ExecutionException, InterruptedException {

        if (portfolioStocks.getAdded() != null && !portfolioStocks.getAdded().isEmpty()) {
            System.out.println("added some stock");
            List<PortfolioStock> added = portfolioStocks.getAdded();
//            Map<String, List<PortfolioStock>> added = portfolioStocks.getAdded();
//            List<PortfolioStock> allPortfolioStocks = new ArrayList<>();

//            // Iterate through the map to collect PortfolioStock objects
//            for (Map.Entry<String, List<PortfolioStock>> entry : added.entrySet()) {
//                List<PortfolioStock> portfolioStockList = entry.getValue();
//                allPortfolioStocks.addAll(portfolioStockList);
//            }
//
//            // Now, allPortfolioStocks contains all individual PortfolioStock objects
//            for (PortfolioStock portfolioStock : allPortfolioStocks) {
//                System.out.println(portfolioStock);
//                String result = portfolioStockService.createPortfolioStock(portfolioStock);
//                System.out.println(result);
//            }
            for(PortfolioStock portfolioStock : added){
                String result = portfolioStockService.createPortfolioStock(portfolioStock);
                System.out.println(result);
            }
        }

        return "All stocks are updated";
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


}
