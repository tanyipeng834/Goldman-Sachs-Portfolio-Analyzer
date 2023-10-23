package com.trading.application.config;

import com.trading.application.listener.LogSubsciber;
import com.trading.application.logs.entity.AccessLog;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * The type Redis config.
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

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

    /**
     * Publish template redis template.
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis template
     */
    @Bean
    RedisTemplate<String, Object> publishTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(new JdkSerializationRedisSerializer());

        return template;
    }

    /**
     * Cache manager cache manager.
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the cache manager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration1 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(7)); // Set TTL to 7 days for cache1
        RedisCacheConfiguration cacheConfiguration2 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(24)); // Set TTL to 24 hours for cache2
        RedisCacheConfiguration cacheConfiguration3 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(30)); //
        RedisCacheConfiguration cacheConfiguration4 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(24)); //

        CacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration3)
                .withCacheConfiguration("weeklyStockPrice", cacheConfiguration1)
                .withCacheConfiguration("dailyStockPrice", cacheConfiguration2)
                .withCacheConfiguration("monthlyStockPrice",cacheConfiguration3)
                .withCacheConfiguration("eod-price", cacheConfiguration2)
                .withCacheConfiguration("portfolioValue", cacheConfiguration4)
                .build();

        return cacheManager;
    }

    /**
     * Topic channel topic.
     *
     * @return the channel topic
     */
    @Bean
    public ChannelTopic topic(){

        return new ChannelTopic("Logs");

    }

    /**
     * Message listener adapter message listener adapter.
     *
     * @return the message listener adapter
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        // Have to put the new receiver class inside the listener adapter
        return new MessageListenerAdapter(new LogSubsciber());
    }

    /**
     * Redis message listener container redis message listener container.
     *
     * @return the redis message listener container
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(messageListenerAdapter(),topic());
        return container;
    }



}