package com.trading.application.stockprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.application.stockprice.controller.StockPriceController;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import com.trading.application.stockprice.repository.StockPriceRepository;
import com.trading.application.stockprice.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

@Service
public class StockPricesService {

    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String,Object> template;
    @Autowired
    private StockPriceService stockPriceService;

    // get monthly price from date
    public Object getMonthlyPriceFromDate(String stockTicker, String month, String year) throws ExecutionException, InterruptedException , JsonProcessingException {

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String key = "monthlyStockPrice::" + stockTicker;
        Object value = template.opsForValue().get(key);
        String dateInput = year + "-" + month;

        // monthly price not in redis
        if(value == null){
            System.out.println("Redis value doesnt exist");

            stockPriceService.getStockMonthlyPrice(stockTicker);

            value = template.opsForValue().get(key);

            StockPrices stockPrices = (StockPrices) value;
            for(StockPrice stockPrice : stockPrices.getStockPriceList()){
                String formattedDateString = outputDateFormat.format(stockPrice.getStockDate());

                if(formattedDateString.contains(dateInput)){
                    StockPrice output = new StockPrice();
                    output.setOpenPrice(stockPrice.getOpenPrice());
                    output.setHighPrice(stockPrice.getHighPrice());
                    output.setLowPrice(stockPrice.getLowPrice());
                    output.setClosePrice(stockPrice.getClosePrice());
                    output.setVolume(stockPrice.getVolume());
                    output.setStockDate(stockPrice.getStockDate());
                    return output;
                }
            }

            return null;
        }

        StockPrices stockPrices = (StockPrices) value;
        for(StockPrice stockPrice : stockPrices.getStockPriceList()){
            String formattedDateString = outputDateFormat.format(stockPrice.getStockDate());

            if(formattedDateString.contains(dateInput)){
                StockPrice output = new StockPrice();
                output.setOpenPrice(stockPrice.getOpenPrice());
                output.setHighPrice(stockPrice.getHighPrice());
                output.setLowPrice(stockPrice.getLowPrice());
                output.setClosePrice(stockPrice.getClosePrice());
                output.setVolume(stockPrice.getVolume());
                output.setStockDate(stockPrice.getStockDate());
                return output;
            }
        }

        return null;
    }

}
