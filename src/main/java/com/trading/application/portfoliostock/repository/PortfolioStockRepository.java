package com.trading.application.portfoliostock.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.stock.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    private static void sectorCountLoop(CollectionReference stockColRef, Map<String, Integer> sectorCounts, List<PortfolioStock> myStocks) throws InterruptedException, ExecutionException {
        for (PortfolioStock myStock : myStocks) {
            String stockTicker = myStock.getStockTicker();
            ApiFuture<DocumentSnapshot> stocksInfo = stockColRef.document(stockTicker).get();
            DocumentSnapshot stocksInfoDoc = stocksInfo.get();

            if (stocksInfoDoc.exists()) {
                Stock stock = stocksInfoDoc.toObject(Stock.class);
                String sector = stock.getSector();

                // Update the sector counts in the map
                sectorCounts.put(sector, sectorCounts.getOrDefault(sector, 0) + 1);
            }
        }
    }


    // create individual PortfolioStock
    public String createPortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        System.out.println("inside portstockrepo createportstock");
        DocumentReference docReference = firestore.collection("portfolioStock").document();
        writeResultApiFuture = docReference.set(portfolioStock);
        return "Each Portfolio Stock successfully created";

    }


    // get a Portfolio Stock
    public PortfolioStock getPortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioStock.getPortfolioId()).whereEqualTo("stockTicker", portfolioStock.getStockTicker()).get();
        if(!querySnapshot.get().isEmpty()){

            return querySnapshot.get().getDocuments().get(0).toObject(PortfolioStock.class);
        }

        return null;
    }

    // get all stocks by portfolioId
    public List<PortfolioStock> getAllStocksbyPortfolioId(String portfolioId) throws ExecutionException, InterruptedException {

        List<PortfolioStock> stocks = new ArrayList<>();

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).get();
        for(DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            stocks.add(document.toObject(PortfolioStock.class));
        }

        return stocks;
    }


    // check if Stock exists in portfolio. used when user selects dropdown. verify that stock dont exist in current portfolio
    public boolean checkStockExists(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioStock.getPortfolioId()).whereEqualTo("stockTicker", portfolioStock.getStockTicker()).get();

        return !querySnapshot.get().isEmpty();

    }

    // delete a stock from portfolio
    public String deletePortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioStock.getPortfolioId()).whereEqualTo("stockTicker", portfolioStock.getStockTicker()).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).delete();

        return "Stock successfully deleted";
    }

    // Update a portfolio stock's field
    public String updatePortfolioStockField(String portfolioId, String stockTicker, String field, String fieldValue) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).whereEqualTo("stockTicker", stockTicker).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).update(field, fieldValue);
//        return writeResultApiFuture.get().getUpdateTime().toString();
        return "portfolio stock successfully updated";
    }

    // Overloading
    // Update a portfolio stock's field (qty)
    public String updatePortfolioStockField(String portfolioId, String stockTicker, String field, int fieldValue) throws ExecutionException, InterruptedException {

        // get particular stock from portfolio
        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).whereEqualTo("stockTicker", stockTicker).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).update(field, fieldValue);
        return "portfolio stock successfully updated";

    }

    // to update stockprice. if person manually changes the price. kiv.
    public String updatePortfolioStockField(String portfolioId, String stockTicker, String field, float fieldValue) throws ExecutionException, InterruptedException {

        // get particular stock from portfolio
        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).whereEqualTo("stockTicker", stockTicker).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).update(field, fieldValue);
        return "portfolio stock successfully updated";

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
