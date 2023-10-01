package com.trading.application.stockprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import com.trading.application.stockprice.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

@Service
public class StockPriceService {
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public StockPriceService(WebClient.Builder webClientBuilder,ObjectMapper objectMapper){
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.objectMapper = objectMapper;
    }
    @Autowired
    private StockPriceRepository stockPriceRepo;


    // from api

    public StockPrices getStockDailyPrice(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println("Invoked API");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode MetaNode = rootNode.get("Meta Data");
            LocalDate currentDate = LocalDate.now();
            String stockSymbol =  MetaNode.get("2. Symbol").asText();

            JsonNode dateNode = rootNode.get("Time Series (Daily)");
            ArrayList<StockPrice> stockPriceList = new ArrayList<>();
            Iterator<String> fieldNames = dateNode.fieldNames();
            while(fieldNames.hasNext()){

                String date = fieldNames.next();

                StockPrice stockPrice = objectMapper.readValue(dateNode.get(date).toString(), StockPrice.class);

                String pattern = "yyyy-MM-dd";

                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                Date stockPriceDate = dateFormat.parse(date);


                stockPrice.setStockDate(stockPriceDate);
                stockPriceList.add(stockPrice);
            }
           StockPrices stockPrices = new StockPrices(stockPriceList);


            // Convert LocalDate to Date




            return stockPriceRepo.saveStockDailyPrice(stockPrices,stockTicker);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }


    private StockPrice parseApiResponse(String stockTicker, String priceType) {
        String jsonString =this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", priceType)
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();




    }


}
