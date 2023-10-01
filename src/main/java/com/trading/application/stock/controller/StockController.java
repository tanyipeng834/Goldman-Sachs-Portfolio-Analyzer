package com.trading.application.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@EnableCaching
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    // create new stock
//    @PostMapping
//    @RequestMapping("/create")
//    public String createStock(@RequestBody Stock stock) throws ExecutionException, InterruptedException {
//        return stockService.createStock(stock);
//    }

    // get stock by stockticker
//    @Cacheable(key="#stockTicker",value = "stockCache")
//    @GetMapping
//    @RequestMapping("/{stockTicker}")
//    public Stock getStockById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
//        System.out.println("Invoked cache");
//        return stockService.(stockTicker);
//    }

    @GetMapping
    @RequestMapping("/{stockTicker}/companyOverview")
    public Stock getStockOverviewById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockService.getStockOverview(stockTicker);
    }


}
