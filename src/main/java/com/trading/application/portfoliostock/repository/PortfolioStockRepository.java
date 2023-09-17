package com.trading.application.portfoliostock.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.service.PortfolioService;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Port;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PortfolioStockRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    private ApiFuture<QuerySnapshot> querySnapshot;

    private CollectionReference colRef = firestore.collection("portfolioStock");

    @Autowired
    private PortfolioService portfolioService;


    // create PortfolioStock
    public String createPortfolioStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("portfolioStock").document();
        writeResultApiFuture = docReference.set(portfolioStock);
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

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
    public List<PortfolioStock> getALlStocks(String portfolioId) throws ExecutionException, InterruptedException {

        List<PortfolioStock> stocks = new ArrayList<>();

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).get();
        for(DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            stocks.add(document.toObject(PortfolioStock.class));
        }

        return stocks;
    }


    // check if Stock exists in portfolio
    public boolean checkStockExists(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioStock.getPortfolioId()).whereEqualTo("stockTicker", portfolioStock.getStockTicker()).get();

        return !querySnapshot.get().isEmpty();

    }

    // delete a stock from portfolio
    public String deleteStock(PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioStock.getPortfolioId()).whereEqualTo("stockTicker", portfolioStock.getStockTicker()).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).delete();
        return writeResultApiFuture.get().getUpdateTime().toString();
    }

    // Update a portfolio stock's field
    public String updatePortfolioStockField(String portfolioId, String stockTicker, String field, String fieldValue) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).whereEqualTo("stockTicker", stockTicker).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).update(field, fieldValue);
        return writeResultApiFuture.get().getUpdateTime().toString();

    }

    // Overloading
    // Update a portfolio stock's field
    public String updatePortfolioStockField(String portfolioId, String stockTicker, String field, int fieldValue) throws ExecutionException, InterruptedException {

        querySnapshot = colRef.whereEqualTo("portfolioId", portfolioId).whereEqualTo("stockTicker", stockTicker).get();

        String docId = querySnapshot.get().getDocuments().get(0).getId();

        writeResultApiFuture = firestore.collection("portfolioStock").document(docId).update(field, fieldValue);
        return writeResultApiFuture.get().getUpdateTime().toString();

    }



}
