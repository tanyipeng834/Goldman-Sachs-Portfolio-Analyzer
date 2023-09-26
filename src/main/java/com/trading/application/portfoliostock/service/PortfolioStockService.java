package com.trading.application.portfoliostock.service;

import com.trading.application.portfolio.service.PortfolioService;
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

    @Autowired
    private PortfolioService portfolioService;

    // Create PortfolioStock
    // Checks if Stock exists in portfolio. If false, create new stock.
    // verification will be done at dropdown so here create 1 stock

    public String createPortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
            if(!portfolioStockRepository.checkStockExists(portfolioStock)){
                portfolioService.incrementPortfolioValue(portfolioStock.getPortfolioId(), portfolioStock.getQuantity() * portfolioStock.getStockPrice());
                return portfolioStockRepository.createPortfolioStock(portfolioStock);
            }
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
    }

    // get all stocks
    public List<PortfolioStock> getAllStocks(String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioStockRepository.getALlStocks(portfolioId);
    }

    // delete Stock from portfolio
    public String deleteStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        int quantity = portfolioStockRepository.getPortfolioStock(portfolioStock).getQuantity();
        float price = portfolioStockRepository.getPortfolioStock(portfolioStock).getStockPrice();
        portfolioService.decrementPortfolioValue(portfolioStock.getPortfolioId(), quantity * price);
        return portfolioStockRepository.deleteStock(portfolioStock);
    }

    // update Stock quantity
    // still ongoing
    public String updatePortfolioStock(String portfolioId, String stockTicker, int quantity) throws  ExecutionException, InterruptedException {
        return portfolioStockRepository.updatePortfolioStockField(portfolioId, stockTicker, "quantity", quantity);
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

}
