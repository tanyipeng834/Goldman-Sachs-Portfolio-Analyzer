package com.trading.application.stock.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.trading.application.stock.entity.Stock;

import com.trading.application.stockprice.entity.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * The type Stock repository.
 */
@Repository
public class StockRepository {

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
     * The constant HASH_KEY.
     */
    public static final String HASH_KEY = "Stock";
    /**
     * The Template.
     */
    @Autowired
    private RedisTemplate<String,Object> template;


    /**
     * Gets reference by id.
     *
     * @param stockTicker the stock ticker
     * @return the reference by id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public DocumentReference getReferenceById(String stockTicker) throws ExecutionException, InterruptedException {

        return firestore.collection("stock").document(stockTicker);

    }

    // create Stock, get from api

    /**
     * Gets stock overview.
     *
     * @param stockTicker the stock ticker
     * @return the stock overview
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
// get stock with overview if already created in firebase
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public Stock getStockOverview(String stockTicker) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentSnapshot> future = firestore.collection("stock").document(stockTicker).get();
        DocumentSnapshot document = future.get();

        Stock stock = null;
        if(document.exists()){
            stock = document.toObject(Stock.class);
            return stock;
        }
        else
        {
            return null;
        }

    }

    /**
     * Create stock with overview string.
     *
     * @param stockTicker          the stock ticker
     * @param description          the description
     * @param exchange             the exchange
     * @param currency             the currency
     * @param country              the country
     * @param sector               the sector
     * @param industry             the industry
     * @param marketCapitalization the market capitalization
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
// create Stock with overview
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String createStockWithOverview(String stockTicker, String description, String exchange, String currency, String country, String sector, String industry, String marketCapitalization ) throws ExecutionException, InterruptedException {
        DocumentReference docReference = firestore.collection("stock").document(stockTicker.toUpperCase());

        Stock stock = new Stock( description, exchange, currency, country, sector, industry, marketCapitalization);
        stock.setStockTicker(stockTicker);
        stock.setDescription(description);
        stock.setExchange(exchange);
        stock.setCurrency(currency);
        stock.setCountry(country);
        stock.setSector(sector);

        stock.setStockTicker(docReference.getId());
        writeResultApiFuture = docReference.set(stock);
        return "Added stock overview to firebase";

    }

//    public ResponseEntity<Object> createStockOverview(StockCompanyOverview stockCompanyOverview) throws ExecutionException, InterruptedException {
//
//        String customisedDocumentId = stockCompanyOverview.getStockTicker();
//        //template.opsForHash().put(HASH_KEY,stock.getStockTicker(),stock);
////        DocumentReference docReference = firestore.collection("stockOverview").document(customisedDocumentId);
//        Gson gson  = new Gson();
//
//        try{
//            // Check if there is no error in the database
//            writeResultApiFuture = docReference.set(stockCompanyOverview);
//            writeResultApiFuture.get();
//            return new ResponseEntity<>(stockCompanyOverview,HttpStatus.OK);
//
//
//        }
//
//        catch(Exception e){
//            return new ResponseEntity<>("Error Updating the Database", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * Gets stock.
     *
     * @param stockTicker the stock ticker
     * @return the stock
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public Stock getStock(String stockTicker) throws ExecutionException, InterruptedException {

        DocumentReference docReference = getReferenceById(stockTicker);
        documentSnapshotApiFuture = docReference.get();
        DocumentSnapshot document = documentSnapshotApiFuture.get();

        Stock stock = null;

        if(document.exists()){
            stock = document.toObject(Stock.class);
            return stock;
        }
        else
        {
            return null;
        }
    }
}