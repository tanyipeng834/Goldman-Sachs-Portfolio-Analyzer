package com.trading.application.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
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

//    @GetMapping("/ibm-price")
//    public String getIBMStockPrice() {
//        System.out.println("Before making the API request");
//        AlphaVantage.api()
//                .timeSeries()
//                .intraday()
//                .forSymbol("IBM")
//                .interval(Interval.FIVE_MIN)
//                .outputSize(OutputSize.FULL)
//                .onSuccess(e -> handleSuccess(e)
//                )
//                .onFailure(e -> handleFailure(e))
//                .fetch();
//
//        return "Fetching IBM stock price data...";
//    }
//
//    private void handleSuccess(Object data) {
//        String jsonResponse = data.toString(); // Convert the response to JSON format
//        System.out.println(jsonResponse);
//    }
//
//    private void handleFailure(Exception e) {
//        // Handle API request failure here
//    }


    // get stock by stockticker
    @GetMapping
    @RequestMapping("/{stockTicker}")
    public Mono<ResponseEntity<Object>> getStockById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockService.getStock(stockTicker);
    }

    @GetMapping
    @RequestMapping("/daily-price/{stockTicker}")
    public Mono<String> getStockDailyPriceById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException {
        return stockService.getStockByDailyPrice(stockTicker);
    }

    @GetMapping
    @RequestMapping("/weekly-price/{stockTicker}")
    public Mono<String> getStockWeeklyPriceById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException {
        return stockService.getStockByWeeklyPrice(stockTicker);
    }

    @GetMapping
    @RequestMapping("/monthly-price/{stockTicker}")
    public Mono<String> getStockMonthlyPriceById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException {
        return stockService.getStockByMonthlyPrice(stockTicker);
    }

}
