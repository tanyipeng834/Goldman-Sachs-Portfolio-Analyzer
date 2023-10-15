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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

       String jsonString =parseApiResponse(stockTicker,"TIME_SERIES_DAILY_ADJUSTED");
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




            return stockPrices;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }

    // get balance sheet
    public JsonNode getBalanceSheet(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"BALANCE_SHEET");
        System.out.println("Invoked API");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            System.out.println("HERE " + rootNode);
            return rootNode;

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");
        }
    }

    // get income statement
    public JsonNode getIncomeStatement(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

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

        String jsonString =parseApiResponse(stockTicker,"TIME_SERIES_WEEKLY_ADJUSTED");

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




            return stockPrices;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }

    @Cacheable(key="#stockTicker",cacheNames = "monthlyStockPrice")
    public StockPrices getStockMonthlyPrice(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"TIME_SERIES_MONTHLY_ADJUSTED");

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




            return stockPrices;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }




    public float getStockQuarterlyReturn(String stockTicker, LocalDate startDate, LocalDate endDate)
            throws IOException, ExecutionException, InterruptedException {
        String key = "monthlyStockPrice::" + stockTicker;
        Object value = template.opsForValue().get(key);


        if (value == null) {
            // If data is not available in the cache, fetch it and store it in the cache
            StockPrices stockPrices = getStockMonthlyPrice(stockTicker);

            // Store the data in the cache
            template.opsForValue().set(key, stockPrices);
            List<StockPrice> stockPriceList = stockPrices.getStockPriceList();
            return calculateQuarterlyReturn(stockPriceList, startDate, endDate);
        }
        else{

            StockPrices  stockPrices = (StockPrices) value;
            List<StockPrice> stockPriceList= stockPrices.getStockPriceList();
            return calculateQuarterlyReturn(stockPriceList, startDate, endDate);

        }

        // Calculate the quarterly return based on the start and end date

    }

    private float calculateQuarterlyReturn(List<StockPrice> monthlyStockPrices, LocalDate startDate, LocalDate endDate) {
        // Filter the monthly stock prices for the specified date range
        List<StockPrice> filteredPrices = monthlyStockPrices.stream()
                .filter(price -> {
                    LocalDate stockDate = convertDateToLocalDate(price.getStockDate());
                    return !stockDate.isBefore(startDate) && !stockDate.isAfter(endDate);
                })
                .collect(Collectors.toList());

        // Calculate quarterly return using the filtered data
        if (filteredPrices.size() == 0) {
            // Handle the case where there is no data for the specified date range
            return 0.0f; // You can change this to an appropriate default value or handle it as needed
        }

        float quarterlyReturn = ((filteredPrices.get(filteredPrices.size() - 1).getClosePrice() -
                filteredPrices.get(0).getClosePrice()) / filteredPrices.get(0).getClosePrice()) * 100;

        return quarterlyReturn;
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
    private LocalDate convertDateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


}
