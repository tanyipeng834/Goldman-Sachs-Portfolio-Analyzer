package com.trading.application.logs.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.logs.entity.AccessLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class AccessLogRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    public String addLog(AccessLog accessLog) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentReference> addedDocRef = firestore.collection("logs").document(accessLog.getUserId()).collection("logs").add(accessLog);
        return addedDocRef.get().toString();

    }

    public ArrayList<AccessLog> getLogs(String userId) throws ExecutionException,  InterruptedException {

        ArrayList<AccessLog> accessLogs = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = firestore.collection("logs").document(userId).collection("logs").orderBy("dateTime", Query.Direction.DESCENDING).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            accessLogs.add(document.toObject(AccessLog.class));
        }

        return accessLogs;

    }



}
