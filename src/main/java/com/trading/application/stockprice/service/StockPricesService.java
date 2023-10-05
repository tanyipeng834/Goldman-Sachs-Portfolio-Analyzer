package com.trading.application.stockprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
public class StockPricesService {

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
                    return new StockPrice(stockPrice.getOpenPrice(),stockPrice.getHighPrice(), stockPrice.getLowPrice(), stockPrice.getClosePrice(),stockPrice.getStockDate(),stockPrice.getVolume());
                }
            }

            return null;
        }

        StockPrices stockPrices = (StockPrices) value;
        for(StockPrice stockPrice : stockPrices.getStockPriceList()){
            String formattedDateString = outputDateFormat.format(stockPrice.getStockDate());

            if(formattedDateString.contains(dateInput)){
                return new StockPrice(stockPrice.getOpenPrice(),stockPrice.getHighPrice(), stockPrice.getLowPrice(), stockPrice.getClosePrice(),stockPrice.getStockDate(),stockPrice.getVolume());
            }
        }

        return null;
    }

    public Object getPricesFromDateBought(String stockTicker, String dateBought) throws ExecutionException, InterruptedException, JsonProcessingException, ParseException {

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String key = "monthlyStockPrice::" + stockTicker;
        Object value = template.opsForValue().get(key);
        Date datebought = outputDateFormat.parse(dateBought);
        Date today = new Date(); // Current date
        ArrayList<StockPrice> stockPriceList = new ArrayList<>();

        // monthly price not in redis
        if(value == null){
            System.out.println("Redis value doesnt exist");

            stockPriceService.getStockMonthlyPrice(stockTicker);

            value = template.opsForValue().get(key);

            StockPrices stockPrices = (StockPrices) value;

            for(StockPrice stockPrice : stockPrices.getStockPriceList()){
                String formattedDateString = outputDateFormat.format(stockPrice.getStockDate());
                Date dateToCompare = outputDateFormat.parse(formattedDateString);

                // Check if dateToCompare is between dateBought and today
                if (datebought.compareTo(dateToCompare) <= 0 && dateToCompare.compareTo(today) <= 0) {
                    stockPriceList.add(new StockPrice(stockPrice.getOpenPrice(),stockPrice.getHighPrice(), stockPrice.getLowPrice(), stockPrice.getClosePrice(),stockPrice.getStockDate(),stockPrice.getVolume()));
                }

            }

            return stockPriceList;

        }

        StockPrices stockPrices = (StockPrices) value;
        for(StockPrice stockPrice : stockPrices.getStockPriceList()){
            String formattedDateString = outputDateFormat.format(stockPrice.getStockDate());
            Date dateToCompare = outputDateFormat.parse(formattedDateString);

            // Check if dateToCompare is between dateBought and today
            if (datebought.compareTo(dateToCompare) <= 0 && dateToCompare.compareTo(today) <= 0) {

                stockPriceList.add(new StockPrice(stockPrice.getOpenPrice(),stockPrice.getHighPrice(), stockPrice.getLowPrice(), stockPrice.getClosePrice(),stockPrice.getStockDate(),stockPrice.getVolume()));
            }

        }

        return stockPriceList;
    }

}
