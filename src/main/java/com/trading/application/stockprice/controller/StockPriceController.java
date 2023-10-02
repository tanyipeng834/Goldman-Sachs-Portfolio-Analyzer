package com.trading.application.stockprice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.trading.application.stock.service.StockPricesService;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import com.trading.application.stockprice.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/stockprice")
@CrossOrigin(origins = "http://localhost:8080")
public class StockPriceController {
    @Autowired
    private StockPriceService stockPriceService;
    @Autowired
    private RedisTemplate<String,Object> template;
    @Autowired
    private StockPricesService stockPricesService;


    // still need to create in firebase. this is the first call to api
    @GetMapping
    @RequestMapping("/eodprice/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "eodPrice")
    public StockPrice getLatestStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        String stockName = stockTicker.toUpperCase();
        if (template.opsForHash().hasKey("eodPrice",stockTicker)){
            return (StockPrice) template.opsForHash().get("eodPrice",stockTicker);

        }
        else{
            getDailyStockPrice(stockTicker);
            return(StockPrice) template.opsForHash().get("eodPrice",stockTicker);

        }

    }
    @GetMapping
    @RequestMapping("/dailyprice/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "dailyStockPrice")
    public StockPrices getDailyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockDailyPrice(stockTicker);

    }

    @GetMapping
    @RequestMapping("/balancesheet/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "balanceSheet")
    public Object getBalanceSheet(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getBalanceSheet(stockTicker);
    }

    @GetMapping
    @RequestMapping("/incomestatement/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "incomeStatement")
    public Object getIncomeStatement(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getIncomeStatement(stockTicker);
    }

    @GetMapping
    @RequestMapping("/weeklyprice/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "weeklyStockPrice")
    public StockPrices getWeeklyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockWeeklyPrice(stockTicker);

    }

    @GetMapping
    @RequestMapping("/monthlyprice/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "monthlyStockPrice")
    public StockPrices getMonthlyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockMonthlyPrice(stockTicker);

    }

    @GetMapping
    @RequestMapping("/testmonth/{stockTicker}")
    public Object getMonthlyPriceFromDate(@PathVariable String stockTicker, @RequestParam String month, @RequestParam String year) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPricesService.getMonthlyPriceFromDate(stockTicker, month, year);

    }




}
