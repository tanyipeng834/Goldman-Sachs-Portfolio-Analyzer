package com.trading.application.customer.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.customer.entity.Customer;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    Firestore fireStore = FirestoreClient.getFirestore();

    // create new customer
    public String createCustomer(Customer customer) throws ExecutionException, InterruptedException {

        DocumentReference docReference = fireStore.collection("customer").document();

        customer.setId(docReference.getId());

        ApiFuture<WriteResult> apiFuture = docReference.set(customer);

        return apiFuture.get().getUpdateTime().toDate().toString();
    }

    // get customer by id
    public Customer getCustomer(String id) throws  ExecutionException, InterruptedException {

        DocumentReference docReference = fireStore.collection("customer").document(id);

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

    // update customer name
    public String customerUpdateName(String id, String name) throws ExecutionException, InterruptedException {

        DocumentReference docReference = fireStore.collection("customer").document(id);

        ApiFuture<WriteResult> apiFuture = docReference.update("name", name);

        WriteResult result = apiFuture.get();

        return "Result: " + result;
    }

}
