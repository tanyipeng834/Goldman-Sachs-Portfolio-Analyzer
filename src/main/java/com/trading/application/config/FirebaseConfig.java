package com.trading.application.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The type Firebase config.
 */
@Service
public class FirebaseConfig {

    /**
     * Configure firebase connection.
     *
     * @throws IOException the io exception
     */
    @PostConstruct
    public void configureFirebaseConnection() throws IOException {

        File file = ResourceUtils.getFile("classpath:config/testis442-348c1-firebase-adminsdk-wsyew-79ab707779.json");
        FileInputStream serviceAccount =
                new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

    }

}
