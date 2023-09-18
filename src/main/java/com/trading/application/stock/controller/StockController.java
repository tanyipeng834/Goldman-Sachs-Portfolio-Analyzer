package com.trading.application.stock.controller;

import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    // create new stock
    @PostMapping
    @RequestMapping("/create")
    public String createStock(@RequestBody Stock stock) throws ExecutionException, InterruptedException {
        return stockService.createStock(stock);
    }

    // get stock by stockticker
    @GetMapping
    @RequestMapping("/{stockTicker}")
    public Stock getStockById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException {
        return stockService.getStock(stockTicker);
    }

}
