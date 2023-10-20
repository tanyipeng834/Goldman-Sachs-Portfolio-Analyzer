package com.trading.application.customer.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.customer.entity.Customer;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;

@Repository
public class CustomerRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("customer").document(documentId);
    }

    public String addCustomer(Customer customer) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("customer").document(customer.getId());
        customer.setTotalCapitalAvailable(10000);
        writeResultApiFuture = docReference.set(customer);
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

    }

    public Customer getById(String documentId) throws ExecutionException, InterruptedException {

        DocumentReference docReference = getReferenceById(documentId);
        documentSnapshotApiFuture = docReference.get();
        DocumentSnapshot document = documentSnapshotApiFuture.get();

        Customer customer = null;

        if(document.exists()){
            customer = document.toObject(Customer.class);
            return customer;
        }
        else
        {
            return null;
        }

    }

    public String updateDocumentField(String documentId, String field, String fieldValue) throws InterruptedException, ExecutionException{

        DocumentReference docReference = getReferenceById(documentId);
        writeResultApiFuture = docReference.update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();

    }

    public String updateTotalCapitalAvailable(String documentId, String field, int fieldValue) throws InterruptedException, ExecutionException {

        DocumentReference docReference = getReferenceById(documentId);
        writeResultApiFuture = docReference.update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();

    }


    public String deleteCustomerAccount(String documentId){

        try {
            writeResultApiFuture = getReferenceById(documentId).delete();
            return "Successfully deleted " + documentId;
        } catch (FirestoreException e) {
            return "Error deleting " + documentId;
        }

    }

    public int getTotalCapitalAvailable(String documentId) throws InterruptedException, ExecutionException {

        DocumentReference docRef = getReferenceById(documentId);
        try {
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                Long totalCapital = document.getLong("totalCapitalAvailable");

                if (totalCapital != null) {
                    return totalCapital.intValue();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
            } catch (InterruptedException e) {
                throw new InterruptedException("Failed to retrieve customer capital: " + e.getMessage());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
        }
    }

}


