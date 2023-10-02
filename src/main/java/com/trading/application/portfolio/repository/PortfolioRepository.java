package com.trading.application.portfolio.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.repository.PortfolioStockRepository;
import com.trading.application.stock.entity.Stock;
import org.springframework.http.ResponseEntity;
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
        writeResultApiFuture = docReference.set(portfolio);
        return "Portfolio successfully created on: " + writeResultApiFuture.get().getUpdateTime().toDate().toString();
    }

    public String addStock(String portfolioStockId ,String portfolioId) throws ExecutionException,InterruptedException{
        DocumentReference portfolioDocReference = firestore.collection("portfolio").document(portfolioId);
        DocumentReference portfolioStockReference = firestore.collection("portfolioStock").document(portfolioStockId);
        writeResultApiFuture = portfolioDocReference.update("portfolioStockArray",FieldValue.arrayUnion(portfolioStockReference));
        System.out.println("Update time : " + portfolioStockReference.get());

        return writeResultApiFuture.get().getUpdateTime().toDate().toString();
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

    // get a portfolio
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        Portfolio portfolio = null;
        if(document.exists()){
            portfolio = document.toObject(Portfolio.class);
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



}
