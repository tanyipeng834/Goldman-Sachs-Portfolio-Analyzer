package com.trading.application.stockprice.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.stockprice.entity.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

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
    public String createStock(StockPrice stockPrice) throws ExecutionException, InterruptedException {

//        template.opsForHash().put(HASH_KEY,stockPrice.getStockTicker(),stockPrice);
        DocumentReference docReference = firestore.collection("stockPrice").document();
        stockPrice.setStockTicker(docReference.getId());
        writeResultApiFuture = docReference.set(stockPrice);
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

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
