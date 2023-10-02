package com.trading.application.stockprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stockprice.controller.StockPriceController;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import com.trading.application.stockprice.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StockPriceService {
    private final ObjectMapper objectMapper;
    @Autowired
    private StockPriceRepository stockPriceRepo;
    @Autowired
    private RedisTemplate<String,Object> template;

    private final WebClient webClient;
    @Value("${api.key}")
    private  String apiKey;
    @Autowired
    public StockPriceService(WebClient.Builder webClientBuilder ,ObjectMapper objectMapper){
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.objectMapper = objectMapper;
    }



    // from api

    public StockPrices getStockDailyPrice(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

       String jsonString =parseApiResponse(stockTicker,"TIME_SERIES_DAILY");
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

    // get balance sheet
    public Object getBalanceSheet(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"BALANCE_SHEET");
        System.out.println("Invoked API");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            return rootNode;

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");
        }
    }

    // get income statement
    public Object getIncomeStatement(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"INCOME_STATEMENT");
        System.out.println("Invoked API");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            return rootNode;

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");
        }
    }

    public StockPrices getStockWeeklyPrice(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"TIME_SERIES_WEEKLY");

        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode MetaNode = rootNode.get("Meta Data");
            LocalDate currentDate = LocalDate.now();
            String stockSymbol =  MetaNode.get("2. Symbol").asText();

            JsonNode dateNode = rootNode.get("Weekly Time Series");
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




            return stockPriceRepo.saveStockWeeklyPrice(stockPrices,stockTicker);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }


    public StockPrices getStockMonthlyPrice(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"TIME_SERIES_MONTHLY");

        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode MetaNode = rootNode.get("Meta Data");
            LocalDate currentDate = LocalDate.now();
            String stockSymbol =  MetaNode.get("2. Symbol").asText();

            JsonNode dateNode = rootNode.get("Monthly Time Series");
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




            return stockPriceRepo.saveStockMonthlyPrice(stockPrices,stockTicker);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }

    @Cacheable(key="#stockTicker",cacheNames = "monthlyStockPrice")
    public StockPrices getMonthlyPrice(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        return this.getStockMonthlyPrice(stockTicker);

    }

    private String parseApiResponse(String stockTicker, String priceType) {
        String jsonString =this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", priceType)
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();


        return jsonString;




    }


}
