package com.trading.application.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
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
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseConfig {

    @PostConstruct
    public void configureFirebaseConnection() throws IOException {

//        File file = ResourceUtils.getFile("classpath:config/is442-testenv-firebase-adminsdk-43sft-7a371fe33d.json");
//        FileInputStream serviceAccount =
//                new FileInputStream(file);
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
        // Create mock objects for Firebase components
        FirebaseApp firebaseApp = Mockito.mock(FirebaseApp.class);
        Firestore firestore = Mockito.mock(Firestore.class);
        CollectionReference collectionReference = Mockito.mock(CollectionReference.class);
        DocumentReference documentReference = Mockito.mock(DocumentReference.class);

        // Set up the behavior of Firebase components using Mockito
        when(firebaseApp.getOptions()).thenReturn(Mockito.mock(FirebaseOptions.class));
        when(firestore.collection(any(String.class))).thenReturn(collectionReference);
        when(collectionReference.document(any(String.class))).thenReturn(documentReference);

    }

    @Configuration
    @EnableRedisRepositories
    public static class RedisConfig {

        @Bean
        JedisConnectionFactory connectionFactory() {
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName("localhost");
            configuration.setPort(6379);
            return new JedisConnectionFactory(configuration);
        }

            @Bean
            RedisTemplate<String, Object> template(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(new JdkSerializationRedisSerializer());
            template.setValueSerializer(new JdkSerializationRedisSerializer());
            template.setEnableTransactionSupport(true);
            template.afterPropertiesSet();
            return template;
        }

    }
}
