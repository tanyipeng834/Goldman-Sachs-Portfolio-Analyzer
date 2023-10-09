package com.trading.application.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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


    //GET STOCK OVERVIEW
    public Stock getStockOverview(String stockTicker) {

        //check fb for data first
        try{
            Stock stockOverview = stockRepo.getStockOverview(stockTicker);
            if(stockOverview == null){
                // create in firebase
                System.out.println("stock not added before");
                createStockOverview(stockTicker);
                return null;
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

