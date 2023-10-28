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
 * Stock Controller class for handling stock-related HTTP requests.
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

    /**
     * Gets stock overview by id.
     *
     * @param stockTicker The stock ticker for which overview information is to be retrieved.
     * @return A Stock object containing overview details.
     * @throws ExecutionException      If an error occurs during execution.
     * @throws InterruptedException    If the operation is interrupted.
     * @throws JsonProcessingException If there is an issue with JSON processing.
     * @throws RuntimeException        If there is a runtime exception.
     */
    @GetMapping
    @RequestMapping("/{stockTicker}/companyOverview")
    @Cacheable(key="#stockTicker",cacheNames = "companyOverview")
    public Stock getStockOverviewById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException, RuntimeException {
        return stockService.getStockOverview(stockTicker);

    }


}
