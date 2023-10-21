package com.trading.application.logs.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.logs.entity.AccessLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * The type Access log repository.
 */
@Repository
public class AccessLogRepository {
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
     * Add log string.
     *
     * @param accessLog the access log
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String addLog(AccessLog accessLog) throws ExecutionException, InterruptedException {

        ApiFuture<DocumentReference> addedDocRef = firestore.collection("logs").document(accessLog.getUserId()).collection("logs").add(accessLog);
        return addedDocRef.get().toString();

    }

    /**
     * Gets logs.
     *
     * @param userId the user id
     * @return the logs
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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
