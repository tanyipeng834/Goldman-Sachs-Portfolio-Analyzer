//package com.trading.application;
//
//import com.crazzyghost.alphavantage.AlphaVantage;
//import com.crazzyghost.alphavantage.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AlphaVantageConfig {
//
//    @Bean
//    public AlphaVantage alphaVantage() {
//        Config config = Config.builder()
//                .key("8X5GQFUZXPA8IUC5")
//                .timeOut(10)
//                .build();
//        AlphaVantage.api().init(config);
//        return AlphaVantage.api();
//    }
//}