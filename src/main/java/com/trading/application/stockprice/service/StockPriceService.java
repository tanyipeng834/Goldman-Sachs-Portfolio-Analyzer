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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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

/**
 * The type Stock price service.
 */
@Service
public class StockPriceService {
    /**
     * The Object mapper.
     */
    private final ObjectMapper objectMapper;

    /**
     * The Template.
     */
    @Autowired
    private RedisTemplate<String,Object> template;

    /**
     * The Web client.
     */
    private final WebClient webClient;
    /**
     * The Api key.
     */
    @Value("${api.key}")
    private  String apiKey;

    /**
     * Instantiates a new Stock price service.
     *
     * @param webClientBuilder the web client builder
     * @param objectMapper     the object mapper
     */
    @Autowired
    public StockPriceService(WebClient.Builder webClientBuilder ,ObjectMapper objectMapper){
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.objectMapper = objectMapper;
    }


    /**
     * Gets stock daily price.
     *
     * @param stockTicker the stock ticker
     * @return the stock daily price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
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


            return stockPrices;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");
        }


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
// get balance sheet
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public JsonNode getBalanceSheet(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"BALANCE_SHEET");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            return rootNode;

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");
        }
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
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public JsonNode getIncomeStatement(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

        String jsonString =parseApiResponse(stockTicker,"INCOME_STATEMENT");
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            return rootNode;

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");
        }
    }

    /**
     * Gets stock weekly price.
     *
     * @param stockTicker the stock ticker
     * @return the stock weekly price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
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


            return stockPrices;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }

    /**
     * Gets stock monthly price.
     *
     * @param stockTicker the stock ticker
     * @return the stock monthly price
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    @Cacheable(key="#stockTicker",cacheNames = "monthlyStockPrice")
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

            return stockPrices;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Stock Ticker does not exist");


        }


    }


    /**
     * Gets stock quarterly return.
     *
     * @param stockTicker the stock ticker
     * @param startDate   the start date
     * @param endDate     the end date
     * @return the stock quarterly return
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public float getStockQuarterlyReturn(String stockTicker, LocalDate startDate, LocalDate endDate)
            throws IOException, ExecutionException, InterruptedException {
        String key = "monthlyStockPrice::" + stockTicker;
        Object value = template.opsForValue().get(key);


        if (value == null) {
            StockPrices stockPrices = getStockMonthlyPrice(stockTicker);

            template.opsForValue().set(key, stockPrices);
            List<StockPrice> stockPriceList = stockPrices.getStockPriceList();
            return calculateQuarterlyReturn(stockPriceList, startDate, endDate);
        }
        else{

            StockPrices  stockPrices = (StockPrices) value;
            List<StockPrice> stockPriceList= stockPrices.getStockPriceList();
            return calculateQuarterlyReturn(stockPriceList, startDate, endDate);

        }


    }

    /**
     * Calculate quarterly return float.
     *
     * @param monthlyStockPrices the monthly stock prices
     * @param startDate          the start date
     * @param endDate            the end date
     * @return the float
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    private float calculateQuarterlyReturn(List<StockPrice> monthlyStockPrices, LocalDate startDate, LocalDate endDate) {
        List<StockPrice> filteredPrices = monthlyStockPrices.stream()
                .filter(price -> {
                    LocalDate stockDate = convertDateToLocalDate(price.getStockDate());
                    return !stockDate.isBefore(startDate) && !stockDate.isAfter(endDate);
                })
                .collect(Collectors.toList());

        if (filteredPrices.size() == 0) {
            return 0.0f;
        }

        float quarterlyReturn = ((filteredPrices.get(filteredPrices.size() - 1).getClosePrice() -
                filteredPrices.get(0).getClosePrice()) / filteredPrices.get(0).getClosePrice()) * 100;

        return quarterlyReturn;
    }


    /**
     * Parse api response string.
     *
     * @param stockTicker the stock ticker
     * @param priceType   the price type
     * @return the string
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
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

    /**
     * Convert date to local date local date.
     *
     * @param date the date
     * @return the local date
     */
    private LocalDate convertDateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


}
