package com.trading.application.portfoliostock.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

                AccessLog accessLog = new AccessLog(userId,"ADD", request.getRemoteAddr(), "Added x" + portfolioStock.getQuantity() + " " + stockTicker + " to " + portfolioId, LocalDateTime.now().toString());
                accessLogService.addLog(accessLog);

                return "Added to " + stockTicker + " array";
            } else {
                return "Document data is null";
            }
        } else {
            return "Document does not exist";
        }
    }

    // NEW
    // delete stock from portstock in portfolio
    public String deleteStock(String portfolioId, String userId, String stockTicker, HttpServletRequest request) throws ExecutionException, InterruptedException {

        DocumentReference docRef = firestore.collection("portfolio").document(portfolioId);

        ApiFuture<DocumentSnapshot> future = docRef.get();
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

                if (portStockMap.containsKey(stockTicker)) {
                    portStockMap.remove(stockTicker);

                    ApiFuture<WriteResult> updateFuture = docRef.update("portStock", portStockMap);
                    updateFuture.get();

                    AccessLog accessLog = new AccessLog(userId,"DELETE", request.getRemoteAddr(), "Deleted " + stockTicker + " from " + portfolioId, LocalDateTime.now().toString());
                    accessLogService.addLog(accessLog);

                    return "Deleted " + stockTicker + " from the portfolio";
                } else {
                    return stockTicker + " does not exist in the portfolio";
                }
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
                        int quantity = portfolioStock.getQuantity();

                        if (quantity == 0) {
                            // delete specific stock based on the index
                            stockList.remove(indexToUpdate);

                            if (stockList.size() == 0) {
                                portStockMap.remove(stockTicker);
                            }

                            ApiFuture<WriteResult> updateFuture = docRef.update("portStock", portStockMap);
                            updateFuture.get();

                            AccessLog accessLog = new AccessLog(userId, "UPDATE", request.getRemoteAddr(), "Updated" + stockTicker + " to 0" + portfolioId, LocalDateTime.now().toString());
                            accessLogService.addLog(accessLog);

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

                            AccessLog accessLog = new AccessLog(userId, "UPDATE", request.getRemoteAddr(), "Updated x" + portfolioStock.getQuantity() + " " + stockTicker + " to " + portfolioId, LocalDateTime.now().toString());
                            accessLogService.addLog(accessLog);

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

//    public Map<String, Integer> getSectorsByPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {
//
//        CollectionReference stockColRef = firestore.collection("stock");
//
//        Map<String, Integer> sectorCounts = new HashMap<>(); // Map to store sector counts
//
//        List<PortfolioStock> myStocks = getAllStocksbyPortfolioId(portfolioId);
//        if (!myStocks.isEmpty()) {
//            sectorCountLoop(stockColRef, sectorCounts, myStocks);
//            return sectorCounts;
//        }
//        return null;
//    }
//
//    // get all stocks by userId
//    public List<PortfolioStock> getAllStocksbyUserId(String userId) throws ExecutionException, InterruptedException {
//
//        List<PortfolioStock> stocks = new ArrayList<>();
//
//        querySnapshot = colRef.whereEqualTo("userId", userId).get();
//        for(DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//            stocks.add(document.toObject(PortfolioStock.class));
//        }
//
//        return stocks;
//
//    }
//
////     get all sectors of portfolios that a user owns
//    public Map<String, Integer> getSectorsByUserId(String userId) throws ExecutionException, InterruptedException {
//
//        CollectionReference stockColRef = firestore.collection("stock");
//
//        Map<String, Integer> sectorCounts = new HashMap<>(); // Map to store sector counts
//
//        List<PortfolioStock> myStocks = getAllStocksbyUserId(userId);
//
//        if (!myStocks.isEmpty()) {
//            sectorCountLoop(stockColRef, sectorCounts, myStocks);
//        }
//        return sectorCounts;
//    }
}
