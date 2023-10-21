package com.trading.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * The type Redis publish config.
 */
@Configuration
@EnableRedisRepositories
public class RedisPublishConfig {

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
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;


    }

}
