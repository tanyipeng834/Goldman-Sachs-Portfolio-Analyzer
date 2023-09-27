package com.trading.application.portfolio.service;

import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepo = new PortfolioRepository();

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

    // update a portfolio's Name
    public String updatePortfolioName(String portfolioId, String portfolioName) throws ExecutionException, InterruptedException {
        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioName", portfolioName);
    }

    // update a portfolio's Description
    public String updatePortfolioDescription(String portfolioId, String portfolioDescription) throws ExecutionException, InterruptedException {
        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioDescription", portfolioDescription);
    }

    // update all portfolio stocks. calling the portfoliostock service n then repo bef calling port repo
    public String updatePortfolioStocks(String portfolioId, ArrayList<PortfolioStock> portfolioStocks) throws ExecutionException, InterruptedException {
//        return portfolioRepo.updatePortfolioField(portfolioId, "portfolioDescription", portfolioDescription);


        for(PortfolioStock portfolioStock : portfolioStocks) {

            PortfolioStockService portfolioStockService;
//            String portfolioId = portfolioStock.getPortfolioId();
            String stockTicker = portfolioStock.getStockTicker();
            int quantity = portfolioStock.getQuantity();
//            float stockPrice = portfolioStock.getStockPrice();

            //assume oni quantity is being updated first
//            portfolioStockService.updatePortfolioStock(portfolioId,  stockTicker, quantity);

        }
        // sending to portfolio
        portfolioRepo.updatePortfolioStocks(portfolioId, portfolioStocks);
        System.out.println("all stocks updated");
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


}
