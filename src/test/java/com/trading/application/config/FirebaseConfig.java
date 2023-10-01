package com.trading.application.config;

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
import java.io.IOException;

@Service
public class FirebaseConfig {

    @PostConstruct
    public void configureFirebaseConnection() throws IOException {

        File file = ResourceUtils.getFile("classpath:config/is442-testenv-firebase-adminsdk-43sft-7a371fe33d.json");
        FileInputStream serviceAccount =
                new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

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
