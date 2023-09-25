package com.trading.application.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.application.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    // get stock daily price by stockticker
    @GetMapping
    @RequestMapping("/{stockTicker}")
    public Mono<ResponseEntity<Object>> getStockById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockService.getStock(stockTicker);
    }

    @GetMapping
    @RequestMapping("/{stockTicker}/companyOverview")
    public Mono<ResponseEntity<Object>>  getStockOverviewById(@PathVariable String stockTicker) throws ExecutionException, InterruptedException, JsonProcessingException {
        return stockService.getStockOverview(stockTicker);
    }


}
