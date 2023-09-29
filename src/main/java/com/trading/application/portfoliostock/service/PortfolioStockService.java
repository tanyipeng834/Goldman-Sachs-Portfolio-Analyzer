package com.trading.application.portfoliostock.service;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.repository.PortfolioStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PortfolioStockService {

    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

//    @Autowired
//    private PortfolioService portfolioService;

    // Create PortfolioStock
    // even if created. user can still add stock. called under update too.
    public String createPortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
//            if(!portfolioStockRepository.checkStockExists(portfolioStock)){
//                portfolioService.incrementPortfolioValue(portfolioStock.getPortfolioId(), portfolioStock.getQuantity() * portfolioStock.getStockPrice());
        System.out.println("creating a port stock");
        portfolioStockRepository.createPortfolioStock(portfolioStock);
//            }
        return "Portfolio stock created";
    }

    // if want to create multiple stocks
//    public String createPortfolioStocks(List<PortfolioStock> portfolioStocks) throws ExecutionException, InterruptedException {
//        for(PortfolioStock portfolioStock : portfolioStocks){
////            if(!portfolioStockRepository.checkStockExists(portfolioStock)){
//                portfolioService.incrementPortfolioValue(portfolioStock.getPortfolioId(), portfolioStock.getQuantity() * portfolioStock.getStockPrice());
//                return portfolioStockRepository.createPortfolioStock(portfolioStock);
////            }
//        }
//        return "Portfolio stock created";
//        return "Stock already exists!";
//    }

    // get all stocks by portfolioId
    public List<PortfolioStock> getAllStocksbyPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.getAllStocksbyPortfolioId(portfolioId);
    }

    // delete Stock from portfolio
    public String deletePortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        int quantity = portfolioStockRepository.getPortfolioStock(portfolioStock).getQuantity();
        float price = portfolioStockRepository.getPortfolioStock(portfolioStock).getStockPrice();
        return portfolioStockRepository.deletePortfolioStock(portfolioStock);
    }

    // update Stock quantity
    // still ongoing
    public String updatePortfolioStock(String portfolioId, String stockTicker, int quantity) throws  ExecutionException, InterruptedException {
        return portfolioStockRepository.updatePortfolioStockField(portfolioId, stockTicker, "quantity", quantity);
    }

    // NEW
    // add new stock to portStock
    public String addNewStock(String portfolioId, String stockTicker, PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.addNewStock(portfolioId, stockTicker, portfolioStock);
    }

    // will do for loop in portfolio
//    public String updatePortfolioStocks(List<PortfolioStock> portfolioStocks) throws ExecutionException, InterruptedException {
//
//        for(PortfolioStock portfolioStock : portfolioStocks){
//
//            String portfolioId = portfolioStock.getPortfolioId();
//            String stockTicker = portfolioStock.getStockTicker();
//            int quantity = portfolioStock.getQuantity();
//
//            portfolioStockRepository.updatePortfolioStockField(portfolioId, stockTicker, "quantity", quantity);
////        portfolioService.incrementPortfolioValue(portfolioStock.getPortfolioId(), portfolioStock.getQuantity() * portfolioStock.getStockPrice());
////            return portfolioStockRepository.createPortfolioStock(portfolioStock);
////            }
//        }
//        return "Portfolio stock updated";
//    }

    // get sectors of all stocks in a portfolio
//    public Map<String, Integer> getSectorsByPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {
//        return portfolioStockRepository.getSectorsByPortfolioId(portfolioId);
//    }
//
//    // get sectors of all stocks by userId
//    public Map<String, Integer> getSectorsByUserId(String userId) throws ExecutionException, InterruptedException {
//        return portfolioStockRepository.getSectorsByUserId(userId);
//    }

    // get all stocks by userId
//    public List<PortfolioStock> getAllStocksbyUserId(String userId) throws ExecutionException, InterruptedException {
//        return portfolioStockRepository.getAllStocksbyUserId(userId);
//    }

}
