package com.trading.application.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.entity.StockPrice;
import com.trading.application.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

@Service
public class StockService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public StockService(WebClient.Builder webClientBuilder,ObjectMapper objectMapper){

        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.objectMapper = objectMapper;


    }

    @Autowired
    private StockRepository stockRepo;

    // create new stock
//    public String createStock(Stock stock) throws ExecutionException, InterruptedException {
//        return stockRepo.createStock(stock);
//    }

    // get stock by id
    public Stock getStock(String stockTicker) throws  ExecutionException, InterruptedException , JsonProcessingException {

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

                                StockPrice stockPrice = objectMapper.readValue(dateNode.get(date).toString(),StockPrice.class);

                                String pattern = "yyyy-MM-dd";

                                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                                Date stockPriceDate = dateFormat.parse(date);


                                stockPrice.setStockDate(stockPriceDate);
                                stockPriceList.add(stockPrice);
                            }
                            String EodDate =  dateNode.fieldNames().next();
                            String EodPrice = dateNode.get(EodDate).get("4. close").asText();


                            // Convert LocalDate to Date
                            Date currentRefresh = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                            Stock newStock = new Stock(stockSymbol,Float.valueOf(EodPrice),stockPriceList,currentRefresh);




                            return stockRepo.createStock(newStock);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                            throw new RuntimeException("Stock Ticker does not exist");


                        }


                }



        //return stockRepo.getStock(stockTicker);


    //GET STOCK OVERVIEW
    public Stock getStockOverview(String stockTicker) {

        //check fb for data first
        try{
            Stock stockOverview = stockRepo.getStockOverview(stockTicker);
            if(stockOverview == null){
                // create in firebase
                System.out.println("stock not added before");
                createStockOverview(stockTicker);
                return getStockOverview(stockTicker);
            }else{
                return stockOverview;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createStockOverview(String stockTicker) {

        // if dont have then call api and create in firebase
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "OVERVIEW")
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(jsonString -> {
                    try {
                        System.out.println(jsonString);
                        JsonNode rootNode = objectMapper.readTree(jsonString);
                        String description = rootNode.get("Description").asText();
                        String exchange = rootNode.get("Exchange").asText();
                        String currency = rootNode.get("Currency").asText();
                        String country = rootNode.get("Country").asText();
                        String sector = rootNode.get("Sector").asText();
                        String industry = rootNode.get("Industry").asText();
                        String marketCapitalisation = rootNode.get("MarketCapitalization").asText();
                        stockRepo.createStockWithOverview( stockTicker, description,  exchange,  currency,  country,  sector, industry, marketCapitalisation);
                    } catch (Exception e1) {
                        e1.getStackTrace();
                    }

                }).subscribe();
    }


}

    //return stockRepo.getStock(stockTicker);

//    // get stock weekly price
//    public Mono<String> getStockByWeeklyPrice(String stockTicker) throws  ExecutionException, InterruptedException {
//        return this.webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/query")
//
//                        .queryParam("function", "TIME_SERIES_DAILY")
//                        .queryParam("symbol", stockTicker)
//                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
//                        .build())
//                .retrieve()
//                .bodyToMono(String.class);
//        //return stockRepo.getStock(stockTicker);
//    }
//    // get stock monthly price
//    public Mono<String> getStockByMonthlyPrice(String stockTicker) throws  ExecutionException, InterruptedException {
//        return this.webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/query")
//                        .queryParam("function", "TIME_SERIES_MONTHLY")
//                        .queryParam("symbol", stockTicker)
//                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
//                        .build())
//                .retrieve()
//                .bodyToMono(String.class);
//        //return stockRepo.getStock(stockTicker);
//    }


//}
