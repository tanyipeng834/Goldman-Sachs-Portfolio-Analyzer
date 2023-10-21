package com.trading.application.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * The type Web client config.
 */
@Configuration
public class WebClientConfig {

    /**
     * Web client builder web client . builder.
     *
     * @return the web client . builder
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl("https://www.alphavantage.co"); // Configure the base URL here
    }
}
