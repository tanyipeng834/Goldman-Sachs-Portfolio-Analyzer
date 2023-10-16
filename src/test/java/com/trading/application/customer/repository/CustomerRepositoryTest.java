package com.trading.application.customer.repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import com.trading.application.customer.entity.Customer;

import java.io.*;

import com.google.cloud.firestore.Firestore;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.customer.entity.Customer;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;


import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryTest extends CustomerRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("customer").document(documentId);

    }
    public CustomerRepositoryTest() throws IOException, FileNotFoundException {
        // Configure Firebase for the test environment
        File file = ResourceUtils.getFile("classpath:config/is442-testenv-firebase-adminsdk-43sft-7a371fe33d.json");
        FileInputStream serviceAccount =
                new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Override
    public String addCustomer(Customer customer) {
        // Simulate adding a customer in memory (no database interaction)
        return "Customer created successfully";
    }
}


//    @Test
//    void getReferenceById() {
//    }
//
//    @Test
//    void addCustomer() {
//    }
//
//    @Test
//    void getById() {
//    }
//
//    @Test
//    void updateDocumentField() {
//    }
//
//    @Test
//    void updateTotalCapitalAvailable() {
//    }
//
//    @Test
//    void deleteCustomerAccount() {
//    }
//
//    @Test
//    void getTotalCapitalAvailable() {
//    }
//}