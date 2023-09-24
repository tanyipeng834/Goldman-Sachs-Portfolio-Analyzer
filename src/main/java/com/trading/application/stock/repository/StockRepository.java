package com.trading.application.stock.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.entity.StockPrice;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Repository
public class StockRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;


    public static final String HASH_KEY = "Stock";
    @Autowired
    private RedisTemplate<String,Object> template;




    public DocumentReference getReferenceById(String stockTicker) throws ExecutionException, InterruptedException {

        return firestore.collection("stock").document(stockTicker);

    }

    // create Stock, get from api
    public ResponseEntity<Object> createStock(Stock newStock) throws ExecutionException, InterruptedException {

        //template.opsForHash().put(HASH_KEY,stock.getStockTicker(),stock);
        DocumentReference docReference = firestore.collection("stock-test").document();
        Gson gson  = new Gson();
        ArrayList<StockPrice> stockPrices = newStock.getHistoricalStockPrice();
        String stockPricesListJson = gson.toJson(stockPrices);
        docReference.update("historicalStockPrice",stockPricesListJson);



        try{
            // Check if there is no error in the database
            writeResultApiFuture = docReference.set(newStock);
            writeResultApiFuture.get();
            return new ResponseEntity<>(newStock,HttpStatus.OK);


        }

        catch(Exception e){
            return new ResponseEntity<>("Error Updating the Database", HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }

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