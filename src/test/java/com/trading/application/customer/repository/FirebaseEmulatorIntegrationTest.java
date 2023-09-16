package com.trading.application.customer.repository;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.config.FirebaseEmulatorConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = FirebaseEmulatorConfig.class)

public class FirebaseEmulatorIntegrationTest {

    @Autowired
    private Firestore firestore;


    @Test
    void testFirebaseEmulatorIntegration() {
        System.out.println(firestore.collection("customer").document());
        System.out.println("Hello");
    }
}
