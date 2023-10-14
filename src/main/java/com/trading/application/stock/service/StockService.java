package com.trading.application.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stock.entity.Stock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StockService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    @Value("${api.key}")
    private String apiKey;

    public StockService(WebClient.Builder webClientBuilder,ObjectMapper objectMapper){

        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.objectMapper = objectMapper;

    }

    private String parseApiResponse(String stockTicker) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "OVERVIEW")
                        .queryParam("symbol", stockTicker)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();
    }

    //GET STOCK OVERVIEW
    @Cacheable(key="#stockTicker",cacheNames = "companyOverview")
    public Stock getStockOverview(String stockTicker) {

        String jsonString =parseApiResponse(stockTicker);
        try {
            System.out.println(jsonString);
            JsonNode rootNode = objectMapper.readTree(jsonString);
            String description = rootNode.get("Description").asText();
            String exchange = rootNode.get("Exchange").asText();
            String currency = rootNode.get("Currency").asText();
            String country = rootNode.get("Country").asText();
            String sector = rootNode.get("Sector").asText();
            String industry = rootNode.get("Industry").asText();
            String marketCapitalization = rootNode.get("MarketCapitalization").asText();

            return new Stock(description,  exchange,  currency,  country,  sector,  industry,  marketCapitalization);

        } catch (Exception e1) {
            e1.getStackTrace();
            throw new RuntimeException("Stock does not have stock overview data.");

        }
    }

}

