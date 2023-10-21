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

        File file = ResourceUtils.getFile("classpath:config/is442-testenv-firebase-adminsdk-43sft-7a371fe33d.json");
        FileInputStream serviceAccount =
                new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

    }

    /**
     * The type Redis config.
     */
    @Configuration
    @EnableRedisRepositories
    public static class RedisConfig {

        /**
         * Connection factory jedis connection factory.
         *
         * @return the jedis connection factory
         */
        @Bean
        JedisConnectionFactory connectionFactory() {
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName("localhost");
            configuration.setPort(6379);
            return new JedisConnectionFactory(configuration);
        }

        /**
         * Template redis template.
         *
         * @param redisConnectionFactory the redis connection factory
         * @return the redis template
         */
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
