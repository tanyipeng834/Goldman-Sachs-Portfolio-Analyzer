package com.trading.application.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Configuration
public class firebaseConfig {
    @PostConstruct
    public Firestore firestore() throws IOException {
        // Load the Firebase service account JSON from the classpath
        File file = ResourceUtils.getFile("classpath:config/testis442-348c1-firebase-adminsdk-wsyew-3201f63dd4.json");
        FileInputStream serviceAccount =
                new FileInputStream(file);

        // Initialize Firebase with the FirebaseOptions for the Emulator
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("http://localhost:9080") // Firebase Emulator URL
                .build();

        FirebaseApp.initializeApp(options);

        // Initialize Firestore and return it as a bean
        return FirestoreClient.getFirestore();
    }
}



