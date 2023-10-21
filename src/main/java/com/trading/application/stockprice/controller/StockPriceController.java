package com.trading.application.stockprice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stockprice.service.StockPricesService;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import com.trading.application.stockprice.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.time.LocalDate;

/**
 * The type Stock price controller.
 */
@RestController
@RequestMapping("/stockprice")
@CrossOrigin(origins = "http://localhost:8080")
public class StockPriceController {
    /**
     * The Stock price service.
     */
    @Autowired
    private StockPriceService stockPriceService;
    /**
     * The Template.
     */
    @Autowired
    private RedisTemplate<String,Object> template;
    /**
     * The Stock prices service.
     */
    @Autowired
    private StockPricesService stockPricesService;


    /**
     * Gets latest stock price.
     *
     * @param stockTicker the stock ticker
     * @return the latest stock price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
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

    /**
     * Gets daily stock price.
     *
     * @param stockTicker the stock ticker
     * @return the daily stock price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping
    @RequestMapping("/dailyprice/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "dailyStockPrice")
    public StockPrices getDailyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockDailyPrice(stockTicker);

    }

    /**
     * Gets balance sheet.
     *
     * @param stockTicker the stock ticker
     * @return the balance sheet
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping
    @RequestMapping("/balancesheet/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "balanceSheet")
    public JsonNode getBalanceSheet(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getBalanceSheet(stockTicker);
    }

    /**
     * Gets income statement.
     *
     * @param stockTicker the stock ticker
     * @return the income statement
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping
    @RequestMapping("/incomestatement/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "incomeStatement")
    public JsonNode getIncomeStatement(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getIncomeStatement(stockTicker);
    }

    /**
     * Gets weekly stock price.
     *
     * @param stockTicker the stock ticker
     * @return the weekly stock price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping
    @RequestMapping("/weeklyprice/{stockTicker}")
    @Cacheable(key="#stockTicker",cacheNames = "weeklyStockPrice")
    public StockPrices getWeeklyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockWeeklyPrice(stockTicker);

    }

    /**
     * Gets monthly stock price.
     *
     * @param stockTicker the stock ticker
     * @return the monthly stock price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @GetMapping
    @RequestMapping("/monthlyprice/{stockTicker}")
//    @Cacheable(key="#stockTicker",cacheNames = "monthlyStockPrice")
    public StockPrices getMonthlyStockPrice(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPriceService.getStockMonthlyPrice(stockTicker);

    }

    /**
     * Gets quarterly stock price.
     *
     * @param stockTicker the stock ticker
     * @param year        the year
     * @param quarter     the quarter
     * @return the quarterly stock price
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    @GetMapping
    @RequestMapping("/quarterlyreturn/{stockTicker}/{year}/{quarter}")
    @Cacheable(key = "{#stockTicker, #year, #quarter}", cacheNames = "quaterlyReturns")
    public float getQuarterlyStockPrice(
            @PathVariable String stockTicker,
            @PathVariable int year,
            @PathVariable int quarter
    ) throws ExecutionException, InterruptedException, IOException {
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
        return stockPriceService.getStockQuarterlyReturn(stockTicker, startDate, endDate);
    }

    /**
     * Gets monthly price from date.
     *
     * @param stockTicker the stock ticker
     * @param month       the month
     * @param year        the year
     * @return the monthly price from date
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @RequestMapping("/getmonthlypricebydate/{stockTicker}")
    public Object getMonthlyPriceFromDate(@PathVariable String stockTicker, @RequestParam String month, @RequestParam String year) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockPricesService.getMonthlyPriceFromDate(stockTicker, month, year);

    }

    /**
     * Gets prices from date bought.
     *
     * @param stockTicker the stock ticker
     * @param dateBought  the date bought
     * @return the prices from date bought
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     * @throws ParseException          the parse exception
     */
    @GetMapping
    @RequestMapping("/getpricesfromdatebought/{stockTicker}")
    public Object getPricesFromDateBought(@PathVariable String stockTicker, @RequestParam String dateBought) throws ExecutionException, InterruptedException, JsonProcessingException, ParseException {
        return stockPricesService.getPricesFromDateBought(stockTicker, dateBought);


    }




}
