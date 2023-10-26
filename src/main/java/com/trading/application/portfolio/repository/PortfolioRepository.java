package com.trading.application.portfolio.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * The type Portfolio repository.
 */
@Repository
public class PortfolioRepository {

    /**
     * The Firestore.
     */
    private Firestore firestore = FirestoreClient.getFirestore();
    /**
     * The Document snapshot api future.
     */
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    /**
     * The Write result api future.
     */
    private ApiFuture<WriteResult> writeResultApiFuture;

    /**
     * Get reference by id document reference.
     *
     * @param documentId the document id
     * @return the document reference
     */
    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("portfolio").document(documentId);

    }

    /**
     * Create portfolio string.
     *
     * @param portfolio the portfolio
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String createPortfolio(Portfolio portfolio) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("portfolio").document();
        portfolio.setPortfolioId(docReference.getId());

        Map<String, List<PortfolioStock>> portfolioPortStocks = portfolio.getPortStock();

        float portfolioValue = calculatePortfolioValue(portfolioPortStocks);

        portfolio.setPortfolioValue(portfolioValue);
        writeResultApiFuture = docReference.set(portfolio);

        return "Portfolio successfully created on: " + writeResultApiFuture.get().getUpdateTime().toDate().toString();
    }

    /**
     * Calculate portfolio value float.
     *
     * @param portfolioPortStocks the portfolio port stocks
     * @return the float
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public float calculatePortfolioValue(Map<String, List<PortfolioStock>> portfolioPortStocks){
        float portfolioValue = 0;
        if (portfolioPortStocks != null) {
            for (Map.Entry<String, List<PortfolioStock>> entry : portfolioPortStocks.entrySet()) {
                List<PortfolioStock> portfolioStockList = entry.getValue();

                PortfolioStock lastStock = portfolioStockList.get(portfolioStockList.size()-1);
                float quantity = lastStock.getQuantity();
                float boughtPrice = lastStock.getStockBoughtPrice();
                portfolioValue += (boughtPrice * quantity);
            }
        }
        return portfolioValue;
    }

    /**
     * Calculate portfolio value float.
     *
     * @param portfolioId the portfolio id
     * @return the float
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public float calculatePortfolioValue(String portfolioId) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        float portfolioValue = 0;

        Portfolio portfolio = null;
        if(document.exists()){
            portfolio = document.toObject(Portfolio.class);
            portfolioValue = calculatePortfolioValue(portfolio.getPortStock());

        }else{
            return portfolioValue;
        }
        return portfolioValue;
    }


    /**
     * Update portfolio string.
     *
     * @param portfolio the portfolio
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolio(Portfolio portfolio) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("portfolio").document(portfolio.getPortfolioId());

        float portfolioValue = calculatePortfolioValue(portfolio.getPortStock());
        portfolio.setPortfolioValue(portfolioValue);
        writeResultApiFuture = docRef.set(portfolio);

        return "Updated document with ID: " + writeResultApiFuture.get().toString();
    }

    /**
     * Delete portfolio string.
     *
     * @param portfolioId the portfolio id
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String deletePortfolio(String portfolioId) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).delete();
        return "Portfolio successfully deleted on: " + writeResultApiFuture.get().getUpdateTime();
    }

    /**
     * Update portfolio field string.
     *
     * @param portfolioId the portfolio id
     * @param field       the field
     * @param fieldValue  the field value
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolioField(String portfolioId, String field, String fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    /**
     * Update portfolio field string.
     *
     * @param portfolioId the portfolio id
     * @param field       the field
     * @param fieldValue  the field value
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolioField(String portfolioId, String field, float fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    /**
     * Update portfolio field string.
     *
     * @param portfolioId the portfolio id
     * @param field       the field
     * @param fieldValue  the field value
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
// Overloading
    // Update a portfolio's field
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolioField(String portfolioId, String field, boolean fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    /**
     * Gets portfolio.
     *
     * @param portfolioId the portfolio id
     * @return the portfolio
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Portfolio getPortfolio(String portfolioId) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentSnapshot> future = firestore.collection("portfolio").document(portfolioId).get();
        DocumentSnapshot document = future.get();

        Portfolio portfolio = null;
        if(document.exists()){
            portfolio = document.toObject(Portfolio.class);
            portfolio.setPublic(document.getBoolean("public"));
            return portfolio;
        }
        else
        {
            return null;
        }

    }

    /**
     * Gets all portfolios.
     *
     * @param userId the user id
     * @return the all portfolios
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public List<Portfolio> getAllPortfolios(String userId) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = firestore.collection("portfolio").whereEqualTo("userId", userId).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Portfolio> allportfolios = new ArrayList<Portfolio>();
        for (QueryDocumentSnapshot document : documents) {
            Portfolio portfolio = document.toObject(Portfolio.class);
            allportfolios.add(portfolio);
        }
        return allportfolios;

    }


    /**
     * Gets all public portfolios.
     *
     * @return the all public portfolios
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
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
