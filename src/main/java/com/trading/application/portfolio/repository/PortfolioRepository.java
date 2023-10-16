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

    public String updatePortfolio(String portfolioId, Portfolio portfolio) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("portfolio").document(portfolioId);
        docRef.set(portfolio, SetOptions.merge()).get();

        return "Updated document with ID: " + docRef.getId();
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
