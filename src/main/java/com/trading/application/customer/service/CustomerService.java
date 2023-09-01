package com.trading.application.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.beans.CustomerCreateResponse;
import com.trading.application.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    @Autowired
    CustomerCreateResponse customerCreateResponse;
    public CustomerCreateResponse createCustomer(Customer customer) throws ExecutionException, InterruptedException {

        Firestore fireStore = FirestoreClient.getFirestore();

        DocumentReference docReference = fireStore.collection("customer").document();

        customer.setId(docReference.getId());

        ApiFuture<WriteResult> apiFuture = docReference.set(customer);

        customerCreateResponse.setUpdatedTime(apiFuture.get().getUpdateTime().toDate());
        customerCreateResponse.setId(customer.getId());

        return customerCreateResponse;
    }

}
