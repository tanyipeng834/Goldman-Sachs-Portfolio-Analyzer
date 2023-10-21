package com.trading.application.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * The type Stock controller.
 */
@RestController
@EnableCaching
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:8080")
public class StockController {
    /**
     * The Stock service.
     */
    @Autowired
    private StockService stockService;

    /**
     * The Template.
     */
    @Autowired
    private RedisTemplate<String,Object> template;
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

    /**
     * Gets stock overview by id.
     *
     * @param stockTicker the stock ticker
     * @return the stock overview by id
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     * @throws RuntimeException        the runtime exception
     */
    @GetMapping
    @RequestMapping("/{stockTicker}/companyOverview")
    @Cacheable(key="#stockTicker",cacheNames = "companyOverview")
    public Stock getStockOverviewById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException, RuntimeException {
        return stockService.getStockOverview(stockTicker);

    }


}
