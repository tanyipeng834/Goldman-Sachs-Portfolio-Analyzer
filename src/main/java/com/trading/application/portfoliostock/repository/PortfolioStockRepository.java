package com.trading.application.portfoliostock.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class PortfolioStockRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;
    private ApiFuture<QuerySnapshot> querySnapshot;
    private CollectionReference colRef = firestore.collection("portfolioStock");
    @Autowired

    private RedisTemplate<String,Object> template;
    Logger logger = LoggerFactory.getLogger(AccessLogService.class);

    @Autowired
    private ChannelTopic topic;

    @Autowired
    private AccessLogService accessLogService = new AccessLogService();

    // NEW
    // add new stock to portStock
    public String addNewStock(String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {

        DocumentReference docRef = firestore.collection("portfolio").document(portfolioId);

        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {

            Map<String, Object> data = document.getData();

            if (data != null) {
                Map<String, Object> portStockMap;

                if (data.containsKey("portStock")) {
                    portStockMap = (Map<String, Object>) data.get("portStock");
                } else {
                    // If "portStock" doesn't exist, create a new map
                    portStockMap = new HashMap<>();
                }

                Map<String, Object> newItem = new HashMap<>();

                // Check if stockTicker exists in portStock
                if (portStockMap.containsKey(stockTicker)) {
                    List<Map<String, Object>> stockList = (List<Map<String, Object>>) portStockMap.get(stockTicker);

                    newItem.put("stockBoughtPrice", portfolioStock.getStockBoughtPrice());
                    newItem.put("quantity", portfolioStock.getQuantity());
                    newItem.put("dateBought", portfolioStock.getDateBought());
                    stockList.add(newItem);

                    portStockMap.put(stockTicker, stockList);
                } else {
                    // If stock doesnt exist, add to array
                    List<Map<String, Object>> stockList = new ArrayList<>();
                    newItem.put("stockBoughtPrice", portfolioStock.getStockBoughtPrice());
                    newItem.put("quantity", portfolioStock.getQuantity());
                    newItem.put("dateBought", portfolioStock.getDateBought());
                    stockList.add(newItem);

                    portStockMap.put(stockTicker, stockList);
                }

                ApiFuture<WriteResult> updateFuture = docRef.update("portStock", portStockMap);
                updateFuture.get();

                AccessLog accessLog = new AccessLog(userId,"ADD", request.getRemoteAddr(), "Added x" + portfolioStock.getQuantity() + " " + stockTicker + " to Portfolio ID:" + portfolioId, LocalDateTime.now().toString(), true);

                Gson gson = new Gson();
                String logJson = gson.toJson(accessLog);

                    template.convertAndSend(topic.getTopic(), logJson);

                //accessLogService.addLog(accessLog);

                return "Added to " + stockTicker + " array";
            } else {
                return "Document data is null";
            }
        } else {
            return "Document does not exist";
        }
    }

    // NEW
    public String updateStock(int indexToUpdate, String portfolioId, String userId, String stockTicker, PortfolioStock portfolioStock, HttpServletRequest request) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("portfolio").document(portfolioId);
        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {

            Map<String, Object> data = document.getData();

            if (data != null) {
                Map<String, Object> portStockMap;

                if (data.containsKey("portStock")) {
                    portStockMap = (Map<String, Object>) data.get("portStock");
                    Map<String, Object> updatedStock = new HashMap<>();

                    // Check if stockTicker exists in portStock
                    if (portStockMap.containsKey(stockTicker)) {
                        List<Map<String, Object>> stockList = (List<Map<String, Object>>) portStockMap.get(stockTicker);

                        // If stock exists, update
                        float quantity = portfolioStock.getQuantity();

                        if (quantity == 0) {
                            // delete specific stock based on the index
                            stockList.remove(indexToUpdate);

                            if (stockList.size() == 0) {
                                portStockMap.remove(stockTicker);
                            }

                            ApiFuture<WriteResult> updateFuture = docRef.update("portStock", portStockMap);
                            updateFuture.get();

                            AccessLog accessLog = new AccessLog(userId, "UPDATE", request.getRemoteAddr(), "Updated" + stockTicker + " to 0" + portfolioId, LocalDateTime.now().toString(), true);
                            Gson gson = new Gson();
                            String logJson = gson.toJson(accessLog);
                            logger.info(logJson);

                            template.convertAndSend(topic.getTopic(), logJson);
                            //accessLogService.addLog(accessLog);

                            return "Updated " + stockTicker + " array";
                        } else {
                            updatedStock.put("quantity", portfolioStock.getQuantity());
                            updatedStock.put("stockBoughtPrice", portfolioStock.getStockBoughtPrice());
                            updatedStock.put("dateBought", portfolioStock.getDateBought());

                            if (indexToUpdate >= 0 && indexToUpdate < stockList.size()) {
                                stockList.remove(indexToUpdate);
                                stockList.add(indexToUpdate, updatedStock);
                            } else {
                                System.out.println("Index is out of bounds.");
                            }

                            ApiFuture<WriteResult> updateFuture = docRef.update("portStock", portStockMap);
                            updateFuture.get();

                            AccessLog accessLog = new AccessLog(userId, "UPDATE", request.getRemoteAddr(), "Updated x" + portfolioStock.getQuantity() + " " + stockTicker + " on Portfolio ID:" + portfolioId, LocalDateTime.now().toString(), true);
                            Gson gson = new Gson();
                            String logJson = gson.toJson(accessLog);
                            logger.info(logJson);

                            template.convertAndSend(topic.getTopic(), logJson);
                            //accessLogService.addLog(accessLog);

                            return "Updated " + stockTicker + " array";
                        }
                    } else {
                        // If stock doesnt exist, add to array
                        // throw error
                        return stockTicker + " does not exist in the portfolio";
                    }
                } else {
                    // portstock has to exist. or not throw error cuz nth to update!
                    return "Port stock does not exist";
                }

            } else {
                return "Document data is null";
            }
        } else {
            return "Document does not exist";
        }
    }

    public String deleteStock(String portfolioId, String userId, Map<String, List<Integer>> stocksToDelete, HttpServletRequest request) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("portfolio").document(portfolioId);
        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            Map<String, Object> data = document.getData();

            if (data != null && data.containsKey("portStock")) {
                Map<String, Object> portStock = (Map<String, Object>) data.get("portStock");

                for (Map.Entry<String, List<Integer>> entry : stocksToDelete.entrySet()) {

                    if (portStock.containsKey(entry.getKey()) && portStock.get(entry.getKey()) instanceof ArrayList) {

                        ArrayList<Map<String, Object>> stockArray = (ArrayList<Map<String, Object>>) portStock.get(entry.getKey());

                        List<Integer> value = entry.getValue();
                        for(int i = value.size()-1; i >= 0; i--){
                            if (value.get(i) < stockArray.size()) {

                                int index = value.get(i);
                                String quantity = stockArray.get(index).get("quantity") + "";
                                String dateBought = stockArray.get(index).get("dateBought") + "";
                                stockArray.remove(index);

                                if(stockArray.isEmpty()){
                                    portStock.remove(entry.getKey());
                                }

                                data.put("portStock", portStock);

                                ApiFuture<WriteResult> updateFuture = docRef.set(data);
                                updateFuture.get();

                                AccessLog accessLog = new AccessLog(userId,"DELETE", request.getRemoteAddr(), "Deleted x" + quantity + " " + entry.getKey() + " from Portfolio ID: " + portfolioId, LocalDateTime.now().toString(), true);
                                ObjectMapper objectMapper = new ObjectMapper();

                                Gson gson = new Gson();
                                String logJson = gson.toJson(accessLog);

                                template.convertAndSend(topic.getTopic(), logJson);
                            }
                            else {
                                System.out.println("index out of bounds!");
                            }
                        }

                    }
                }

            } else {
                return "portStock field is missing.";
            }
        } else {
            return "Document does not exist.";
        }
        return "";
    }

}
