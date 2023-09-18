package com.trading.application.stock.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.stock.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class StockRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    public DocumentReference getReferenceById(String stockTicker) throws ExecutionException, InterruptedException {

        return firestore.collection("stock").document(stockTicker);

    }

    // create Stock, get from api
    public String createStock(Stock stock) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("stock").document();
        stock.setStockTicker(docReference.getId());
        writeResultApiFuture = docReference.set(stock);
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

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