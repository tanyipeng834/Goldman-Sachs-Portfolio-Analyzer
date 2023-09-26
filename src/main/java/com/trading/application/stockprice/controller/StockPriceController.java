package com.trading.application.stockprice.controller;

import com.trading.application.stockprice.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/stockprice")
@CrossOrigin(origins = "http://localhost:8080")
public class StockPriceController {
    @Autowired
    private StockPriceService stockPriceService;

    // still need to create in firebase. this is the first call to api
    @PostMapping
    @RequestMapping("/{stockTicker}/create-daily-price")
    public String createStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException {
        return stockPriceService.getStockByDailyPrice(stockTicker);
    }

    // get daily price. from firebase
//    @GetMapping
//    @RequestMapping("/{stockTicker}/daily-price")
//    public Mono<String> getStockById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException {
//        return stockPriceService.getStockByDailyPrice(stockTicker);
//    }
}
