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
 * The Repository class for interacting with the Firestore database to manage portfolios.
 */
@Repository
public class PortfolioRepository {

    /**
     * The Firestore instance used for database operations.
     */
    private Firestore firestore = FirestoreClient.getFirestore();
    /**
     * The API future for DocumentSnapshot.
     */
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    /**
     * The API future for WriteResult.
     */
    private ApiFuture<WriteResult> writeResultApiFuture;

    /**
     * Get a document reference by portfolio id.
     *
     * @param documentId the document id of the document to retrieve.
     * @return A DocumentReference object representing the requested document.
     */
    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("portfolio").document(documentId);

    }

    /**
     * Create a new portfolio in the database.
     *
     * @param portfolio The Portfolio object to be created.
     * @return A String indicating the result of the creation operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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
     *  Calculate the value of a portfolio.
     *
     * @param portfolioPortStocks A map of portfolio stock data.
     * @return The calculated portfolio value as a float.
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
     * Calculate the value of a portfolio by portfolio id.
     *
     * @param portfolioId the portfolio id of the portfolio of which the value is to be calculated.
     * @return The calculated portfolio value as a float.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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
     * Update a portfolio in the database.
     *
     * @param portfolio The Portfolio object with updated information.
     * @return A String indicating the result of the update operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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
     * Delete a portfolio from the database.
     *
     * @param portfolioId The portfolio id of the portfolio to be deleted.
     * @return A String indicating the result of the deletion operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String deletePortfolio(String portfolioId) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).delete();
        return "Portfolio successfully deleted on: " + writeResultApiFuture.get().getUpdateTime();
    }

    /**
     * Update a specific field of a portfolio.
     *
     * @param portfolioId The portfolio id of the portfolio for which the field is to be updated.
     * @param field       The name of the field to update.
     * @param fieldValue  The new field value as a string.
     * @return A String indicating the result of the update operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolioField(String portfolioId, String field, String fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    /**
     * Overloaded method to update a specific field of a portfolio with a float value.
     *
     * @param portfolioId The portfolio id of the portfolio for which the field is to be updated.
     * @param field       The name of the field to update.
     * @param fieldValue  The new field value as a float.
     * @return A String indicating the result of the update operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolioField(String portfolioId, String field, float fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    /**
     *  Overloaded method to update a specific field of a portfolio with a boolean value.
     *
     * @param portfolioId The portfolio id of the portfolio for which the field is to be updated.
     * @param field       The name of the field to update.
     * @param fieldValue  The new field value as a boolean.
     * @return A String indicating the result of the update operation.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updatePortfolioField(String portfolioId, String field, boolean fieldValue) throws ExecutionException, InterruptedException {

        writeResultApiFuture = firestore.collection("portfolio").document(portfolioId).update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    /**
     * Get a portfolio by portfolio id.
     *
     * @param portfolioId The portfolio id of the portfolio to retrieve.
     * @return The Portfolio object representing the requested portfolio, or null if not found.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @Retryable(retryFor = {InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
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
     * Get a list of all portfolios associated with a specific user.
     *
     * @param userId The user id of the user for whom the portfolios are to be retrieved.
     * @return A List of Portfolio objects
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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
     * Retrieve a list of all public portfolios from the database.
     *
     * @return An ArrayList of Portfolio objects representing the public portfolios.
     * @throws ExecutionException If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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
