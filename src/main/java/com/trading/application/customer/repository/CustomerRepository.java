package com.trading.application.customer.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.customer.entity.Customer;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;

@Repository
public class CustomerRepository {

    Firestore firestore = FirestoreClient.getFirestore();

    public DocumentReference getReferenceById(String documentid){

        return firestore.collection("customer").document(documentid);

    }

    public Customer getById(String id, String collection) throws ExecutionException, InterruptedException {

        DocumentReference docReference = getReferenceById(id);
        ApiFuture<DocumentSnapshot> apiFuture = docReference.get();
        DocumentSnapshot document = apiFuture.get();

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

}
