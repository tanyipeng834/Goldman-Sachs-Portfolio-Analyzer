package com.trading.application.stockprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.service.StockService;
import com.trading.application.stockprice.entity.StockPrice;
import com.trading.application.stockprice.entity.StockPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * The type Stock prices service.
 */
@Service
public class StockPricesService {

    /**
     * The Template.
     */
    @Autowired
    private RedisTemplate<String,Object> template;
    /**
     * The Stock price service.
     */
    @Autowired
    private StockPriceService stockPriceService;


    /**
     * Gets monthly price from date.
     *
     * @param stockTicker the stock ticker
     * @param month       the month
     * @param year        the year
     * @return the monthly price from date
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
// get monthly price from date
    public Object getMonthlyPriceFromDate(String stockTicker, String month, String year) throws ExecutionException, InterruptedException , JsonProcessingException {

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String key = "monthlyStockPrice::" + stockTicker;
        Object value = template.opsForValue().get(key);
        String dateInput = year + "-" + month;
        StockPrices stockPrices = stockPriceService.getStockMonthlyPrice(stockTicker);

        for(StockPrice stockPrice : stockPrices.getStockPriceList()){
            String formattedDateString = outputDateFormat.format(stockPrice.getStockDate());

            if(formattedDateString.contains(dateInput)){
                return new StockPrice(stockPrice.getOpenPrice(),stockPrice.getHighPrice(), stockPrice.getLowPrice(), stockPrice.getClosePrice(),stockPrice.getStockDate(),stockPrice.getVolume());
            }
        }

        return null;
    }

    /**
     * Gets prices from date bought.
     *
     * @param stockTicker the stock ticker
     * @param dateBought  the date bought
     * @return the prices from date bought
     * @throws ExecutionException      the execution exception
     * @throws InterruptedException    the interrupted exception
     * @throws JsonProcessingException the json processing exception
     * @throws ParseException          the parse exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public Object getPricesFromDateBought(String stockTicker, String dateBought) throws ExecutionException, InterruptedException, JsonProcessingException, ParseException {

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String key = "monthlyStockPrice::" + stockTicker;
        Object value = template.opsForValue().get(key);
        Date datebought = outputDateFormat.parse(dateBought);
        Date today = new Date(); // Current date
        ArrayList<StockPrice> stockPriceList = new ArrayList<>();
        StockPrices stockPrices = stockPriceService.getStockMonthlyPrice(stockTicker);

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
