package com.trading.application.stockprice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class StockPriceService {
    private final WebClient webClient;

    public StockPriceService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
    }
    @Autowired
    private StockPriceRepository stockPriceRepo;
    public String createStockPrice(StockPrice stockPrice) throws ExecutionException, InterruptedException {
        return stockPriceRepo.createStock(stockPrice);
    }

    // from api
    public String getStockByDailyPrice(String stockTicker) throws ExecutionException, InterruptedException {
        String apiResponse =  this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", "K4UXKCTF5POS6YBS")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if(apiResponse != null){
            StockPrice stockPrice = parseApiResponse(apiResponse);
            return stockPriceRepo.createStock(stockPrice);
        }
        return null;

        //return stockRepo.getStock(stockTickZer);
    }
    private StockPrice parseApiResponse(String apiResponse) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
//            return objectMapper.readValue(apiResponse, StockPrice.class);
            JsonNode rootNode = objectMapper.readTree(apiResponse);

            JsonNode metaDataNode = rootNode.get("Meta Data");
            String date = metaDataNode.get("3. Last Refreshed").asText();
            String symbol = metaDataNode.get("2. Symbol").asText();

            JsonNode timeSeriesData = rootNode.get("Time Series (Daily)");
            String dailyPrices = timeSeriesData.toString();

            StockPrice stockPrice = new StockPrice();
            stockPrice.setStockTicker(symbol);
            stockPrice.setUpdatedDate(date);
            stockPrice.setDailyPrice(dailyPrices);

            return stockPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
