package com.trading.application.stock.service;

import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.concurrent.ExecutionException;

@Service
public class StockService {
    private final WebClient webClient;





    public StockService(WebClient.Builder webClientBuilder){

        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();


    }



    @Autowired
    private StockRepository stockRepo;

    // create new stock
    public String createStock(Stock stock) throws ExecutionException, InterruptedException {

        return stockRepo.createStock(stock);

    }

    // get stock by id
    public Mono<String> getStock(String stockTicker) throws  ExecutionException, InterruptedException {



        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        //return stockRepo.getStock(stockTicker);

    }
}
