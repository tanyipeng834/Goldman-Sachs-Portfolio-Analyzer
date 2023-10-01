package com.trading.application.stockprice.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Repository
public class StockPriceRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;
    public static final String HASH_KEY = "StockPrice";
    @Autowired
    private RedisTemplate<String,Object> template;

    public DocumentReference getReferenceById(String stockTicker) throws ExecutionException, InterruptedException {

        return firestore.collection("stockPrice").document(stockTicker);

    }
    // create StockPrice, get from api
    public StockPrices saveStockDailyPrice(StockPrices stockPrices,String stockTicker) throws ExecutionException, InterruptedException {
        template.opsForHash().put("eodPrice",stockTicker, stockPrices.getStockPriceList().get(0));



        DocumentReference docReference = firestore.collection("stockPrice").document(stockTicker.toUpperCase());
// Check if the document exists
        ApiFuture<DocumentSnapshot> future = docReference.get();
        DocumentSnapshot document = future.get();

// Create a Gson instance to convert your StockPrices object to JSON

// Create a Gson instance to convert your StockPrices object to JSON


        if (document.exists()) {
            // Document exists, update the field "historicalStockPrice"
            docReference.update("dailyStockPrice", stockPrices.getStockPriceList());
            return stockPrices;
        } else {
            // Document doesn't exist, create it with the fields
            Map<String, Object> dailyStockPriceMap = new HashMap<>();
            // Document doesn't exist, create it with the fields
            dailyStockPriceMap.put("dailyStockPrice",stockPrices.getStockPriceList());

            docReference.set(dailyStockPriceMap);
            // Assuming stockPrices is a POJO or a Map
            return  stockPrices;

            // Optionally, you can set the "historicalStockPrice" field here if needed
            // docReference.update("historicalStockPrice", stockPricesListJson);
        }
    }

    public StockPrices saveStockWeeklyPrice(StockPrices stockPrices,String stockTicker) throws ExecutionException, InterruptedException {



        DocumentReference docReference = firestore.collection("stockPrice").document(stockTicker.toUpperCase());
// Check if the document exists
        ApiFuture<DocumentSnapshot> future = docReference.get();
        DocumentSnapshot document = future.get();

// Create a Gson instance to convert your StockPrices object to JSON
//        Gson gson = new Gson();
//        String stockPricesListJson = gson.toJson(stockPrices.getStockPriceList());

// Create a Gson instance to convert your StockPrices object to JSON


        if (document.exists()) {
            // Document exists, update the field "historicalStockPrice"
            docReference.update("weeklyStockPrice", stockPrices.getStockPriceList());
            return stockPrices;
        } else {
            Map<String, Object> weeklyStockPriceMap = new HashMap<>();
            // Document doesn't exist, create it with the fields
            weeklyStockPriceMap.put("weeklyStockPrice",stockPrices.getStockPriceList());

            docReference.set(weeklyStockPriceMap);
            // Assuming stockPrices is a POJO or a Map
            return  stockPrices;

            // Optionally, you can set the "historicalStockPrice" field here if needed
            // docReference.update("historicalStockPrice", stockPricesListJson);
        }
    }

    public StockPrices saveStockMonthlyPrice(StockPrices stockPrices,String stockTicker) throws ExecutionException, InterruptedException {



        DocumentReference docReference = firestore.collection("stockPrice").document(stockTicker.toUpperCase());
// Check if the document exists
        ApiFuture<DocumentSnapshot> future = docReference.get();
        DocumentSnapshot document = future.get();

// Create a Gson instance to convert your StockPrices object to JSON
//        Gson gson = new Gson();
//        String stockPricesListJson = gson.toJson(stockPrices.getStockPriceList());

// Create a Gson instance to convert your StockPrices object to JSON


        if (document.exists()) {
            // Document exists, update the field "historicalStockPrice"
            docReference.update("monthlyStockPrice", stockPrices.getStockPriceList());
            return stockPrices;
        } else {
            Map<String, Object> monthlyStockPriceMap = new HashMap<>();
            // Document doesn't exist, create it with the fields
            monthlyStockPriceMap.put("monthlyStockPrice",stockPrices.getStockPriceList());

            docReference.set(monthlyStockPriceMap);
            // Assuming stockPrices is a POJO or a Map
            return  stockPrices;

            // Optionally, you can set the "historicalStockPrice" field here if needed
            // docReference.update("historicalStockPrice", stockPricesListJson);
        }
    }


    // retrieve value from firebase
    public StockPrice getStock(String stockTicker) throws ExecutionException, InterruptedException {

        DocumentReference docReference = getReferenceById(stockTicker);
        documentSnapshotApiFuture = docReference.get();
        DocumentSnapshot document = documentSnapshotApiFuture.get();

        // deal with redis later !
        StockPrice stockPrice = null;

        if(document.exists()){
            stockPrice = document.toObject(StockPrice.class);
            return stockPrice;
        }
        else
        {
            return null;
        }
    }
}
