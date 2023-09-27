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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

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
        return writeResultApiFuture.get().getUpdateTime().toString();
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

//    updating all portfoliostocks here.
    public String updatePortfolioStocks(String portfolioId, ArrayList<PortfolioStock> portfolioStocks) throws ExecutionException, InterruptedException {

//        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(portfolioStocks, portfolioStocks);
//        return "Result: " + writeResultApiFuture.get();
        // Assuming you have a Firestore document reference
        DocumentReference docRef = firestore.collection("portfolio").document(portfolioId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            Portfolio portfolio = document.toObject(Portfolio.class);
            portfolio.setPortfolioStockArray(portfolioStocks);

            docRef.set(portfolio).get();

//            System.out.println("cuz got dependency, now will ask portfoliostocks to update also");
            return "Result: Portfolio stocks updated successfully";
        } else {
            return "Document not found";
        }

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

}
