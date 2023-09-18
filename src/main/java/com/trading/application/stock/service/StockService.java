package com.trading.application.stock.service;

import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    // create new stock
    public String createStock(Stock stock) throws ExecutionException, InterruptedException {

        return stockRepo.createStock(stock);

    }

    // get stock by id
    public Stock getStock(String stockTicker) throws  ExecutionException, InterruptedException {

        return stockRepo.getStock(stockTicker);

    }
}
