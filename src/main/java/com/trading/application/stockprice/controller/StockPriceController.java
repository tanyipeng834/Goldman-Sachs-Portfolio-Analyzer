package com.trading.application.stockprice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.trading.application.stockprice.service.StockPricesService;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import com.trading.application.stockprice.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.time.LocalDate;

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
//    @Cacheable(key="#stockTicker",cacheNames = "monthlyStockPrice")
    public StockPrices getMonthlyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockMonthlyPrice(stockTicker);

    }

    @GetMapping
<<<<<<< HEAD
    @RequestMapping(("/quarterlyprice/{stockTicker}/{year}/{quarter}"))
    @Cacheable(key="#stockTicker")
    public float getQuarterlyStockPrice(
            @PathVariable String stockTicker,
            @PathVariable int year,
            @PathVariable int quarter
    ) throws ExecutionException, InterruptedException, JsonProcessingException {
        // Calculate the start and end dates based on the year and quarter
        LocalDate startDate;
        LocalDate endDate;

        // Assuming quarters are defined as Q1=1, Q2=2, Q3=3, Q4=4
        switch (quarter) {
            case 1:
                startDate = LocalDate.of(year, 1, 1);
                endDate = LocalDate.of(year, 3, 31);
                break;
            case 2:
                startDate = LocalDate.of(year, 4, 1);
                endDate = LocalDate.of(year, 6, 30);
                break;
            case 3:
                startDate = LocalDate.of(year, 7, 1);
                endDate = LocalDate.of(year, 9, 30);
                break;
            case 4:
                startDate = LocalDate.of(year, 10, 1);
                endDate = LocalDate.of(year, 12, 31);
                break;
            default:
                throw new IllegalArgumentException("Invalid quarter: " + quarter);
        }

        // Call your service method to fetch quarterly stock price data
        return stockPriceService.getStockQuarterlyPrice(stockTicker, startDate, endDate);

    @RequestMapping("/getmonthlypricebydate/{stockTicker}")
    public Object getMonthlyPriceFromDate(@PathVariable String stockTicker, @RequestParam String month, @RequestParam String year) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPricesService.getMonthlyPriceFromDate(stockTicker, month, year);

    }

    @GetMapping
    @RequestMapping("/getpricesfromdatebought/{stockTicker}")
    public Object getPricesFromDateBought(@PathVariable String stockTicker, @RequestParam String dateBought) throws ExecutionException, InterruptedException, JsonProcessingException, ParseException {
        return stockPricesService.getPricesFromDateBought(stockTicker, dateBought);


    }




}
