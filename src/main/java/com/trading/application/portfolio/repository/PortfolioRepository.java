package com.trading.application.portfolio.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.stock.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class PortfolioRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("portfolio").document(documentId);

    }

    // create Portfolio
    public String createPortfolio(Portfolio portfolio) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("portfolio").document();
        portfolio.setPortfolioId(docReference.getId());

        Map<String, List<PortfolioStock>> portfolioPortStocks = portfolio.getPortStock();

        float portfolioValue = 0;
        if (portfolioPortStocks != null) {
            for (Map.Entry<String, List<PortfolioStock>> entry : portfolioPortStocks.entrySet()) {
                List<PortfolioStock> portfolioStockList = entry.getValue();

                for (PortfolioStock portfolioStock : portfolioStockList) {
                    int quantity = portfolioStock.getQuantity();
                    float boughtPrice = portfolioStock.getStockBoughtPrice();
                    portfolioValue += (boughtPrice * quantity);
                }
            }
        }

        portfolio.setPortfolioValue(portfolioValue);
        writeResultApiFuture = docReference.set(portfolio);

        return "Portfolio successfully created on: " + writeResultApiFuture.get().getUpdateTime().toDate().toString();
    }

    // delete a portfolio
    public String deletePortfolio(String portfolioId) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).delete();
        return "Portfolio successfully deleted on: " + writeResultApiFuture.get().getUpdateTime();
    }

    // Update a portfolio's field
    public String updatePortfolioField(String portfolioId, String field, String fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    // Overloading
    // Update a portfolio's field
    public String updatePortfolioField(String portfolioId, String field, float fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    // Overloading
    // Update a portfolio's field
    public String updatePortfolioField(String portfolioId, String field, boolean fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    // get a portfolio
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        Portfolio portfolio = null;
        if(document.exists()){
            portfolio = document.toObject(Portfolio.class);
//            portfolio.setIsPublic(document.getBoolean("public"));
            portfolio.setPublic(document.getBoolean("public"));
            return portfolio;
        }
        else
        {
            return null;
        }

    }

    // Get all Portfolios of a customer
    public List<Portfolio> getAllPortfolios(String userId) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = firestore.collection("portfolio").whereEqualTo("userId", userId).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Portfolio> allportfolios = new ArrayList<Portfolio>();
        for (QueryDocumentSnapshot document : documents) {
            Portfolio portfolio = document.toObject(Portfolio.class);
            allportfolios.add(portfolio);
//            return document.toObject(Portfolio.class));
        }
        return allportfolios;

    }

    // get sectors of all stocks in a portfolio
    public Map<String, Integer> getSectorsByPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {

        Portfolio portfolio = getPortfolio(portfolioId);

        CollectionReference stockColRef = firestore.collection("stock");

        Map<String, Integer> sectorCounts = new HashMap<>(); // Map to store sector counts

        if (portfolio != null) {
            Map<String, List<PortfolioStock>> myStocks = portfolio.getPortStock();

            if (!myStocks.isEmpty()) {
                Set<String> stockKeys = myStocks.keySet();

                for (String stockTicker : stockKeys) {
                    ApiFuture<DocumentSnapshot> stocksInfo = stockColRef.document(stockTicker).get();
                    DocumentSnapshot stocksInfoDoc = stocksInfo.get();

                    if (stocksInfoDoc.exists()) {
                        Stock stock = stocksInfoDoc.toObject(Stock.class);
                        String sector = stock.getSector();

                        // Update the sector counts in the map
                        sectorCounts.put(sector, sectorCounts.getOrDefault(sector, 0) + 1);
                    }
                }
                return sectorCounts;
            }
        }
        return null;
    }

    // get all sectors of stocks that a user owns
    public Map<String, Integer> getSectorsByUserId(String userId) throws ExecutionException, InterruptedException {

        List<Portfolio> allPortfolios = getAllPortfolios(userId);

        Map<String, Integer> allSectorCounts = new HashMap<>(); // Map to store all sector counts

        if (allPortfolios != null) {

            for (Portfolio portfolio : allPortfolios) {
                String portfolioId = portfolio.getPortfolioId();
                Map<String, Integer> sectorCounts = getSectorsByPortfolioId(portfolioId);

                if (sectorCounts != null) {
                    // Update allSectorCounts with sectorCounts
                    for (Map.Entry<String, Integer> entry : sectorCounts.entrySet()) {
                        String sector = entry.getKey();
                        int count = entry.getValue();
                        allSectorCounts.put(sector, allSectorCounts.getOrDefault(sector, 0) + count);
                    }
                }
            }
            return allSectorCounts;
        }
        return null;
    }

    // to display on Map (overview)
    public Map<String, Integer> getCountriesByUserId(String userId) throws ExecutionException, InterruptedException {

        List<Portfolio> allPortfolios = getAllPortfolios(userId);

        Map<String, Integer> allCountriesCounts = new HashMap<>();
        if (allPortfolios != null) {

            for (Portfolio portfolio : allPortfolios) {
                Map<String, List<PortfolioStock>> portfolioPortStocks = portfolio.getPortStock();

                for (Map.Entry<String, List<PortfolioStock>> entry : portfolioPortStocks.entrySet()) {
                    int count = 0;
                    String stockTicker = entry.getKey();
                    List<PortfolioStock> portfolioStockList = entry.getValue(); // Get the list of PortfolioStock objects

                    for(PortfolioStock portfolioStock: portfolioStockList){
                        int quantity = portfolioStock.getQuantity();
                        count += quantity;
                    }
                    System.out.println(stockTicker);
                    ApiFuture<DocumentSnapshot> future = firestore.collection("stock").document(stockTicker).get();
                    DocumentSnapshot document = future.get();

                    Stock stock = null;
                    if (document.exists()) {
                        stock = document.toObject(Stock.class);
                        String country = stock.getCountry();

                        if (allCountriesCounts.containsKey(country)) {
                            int currentCount = allCountriesCounts.get(country);
                            allCountriesCounts.put(country, currentCount + count);
                        } else {
                            allCountriesCounts.put(country, count);
                        }

                    } else {
                        return null;
                    }
                }
            }
            return allCountriesCounts;
        }
        return null;
    }

    //get each portfolio value
    public float calculatePortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        float portfolioValue = 0;

        Portfolio portfolio = null;
        if(document.exists()){
            portfolio = document.toObject(Portfolio.class);
            Map<String, List<PortfolioStock>> portfolioPortStocks = portfolio.getPortStock();

            if (portfolioPortStocks != null) {
                for (Map.Entry<String, List<PortfolioStock>> entry : portfolioPortStocks.entrySet()) {
                    List<PortfolioStock> portfolioStockList = entry.getValue();

                    for (PortfolioStock portfolioStock : portfolioStockList) {
                        int quantity = portfolioStock.getQuantity();
                        float boughtPrice = portfolioStock.getStockBoughtPrice();
                        portfolioValue += (boughtPrice * quantity);
                    }
                }
            }
        }else{
            return portfolioValue;
        }
        return portfolioValue;
    }

    public ArrayList<Portfolio> getAllPublicPortfolios() throws InterruptedException, ExecutionException {

        ArrayList<Portfolio> portfolios = new ArrayList<>();
        Query query = firestore.collection("portfolio").whereEqualTo("public", true);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            QuerySnapshot documents = querySnapshot.get();

            for (QueryDocumentSnapshot document : documents) {
                portfolios.add(document.toObject(Portfolio.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return portfolios;
    }


}
