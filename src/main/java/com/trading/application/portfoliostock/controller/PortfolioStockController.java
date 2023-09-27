package com.trading.application.portfoliostock.controller;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/portfoliostock")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioStockController {

    @Autowired
    private PortfolioStockService portfolioStockService;

    @PostMapping
    @RequestMapping("/create")
//    public String createPortfolioStock(@RequestBody List<PortfolioStock> portfolioStocks) throws ExecutionException, InterruptedException {
    public String createPortfolioStock(@RequestBody PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

        return portfolioStockService.createPortfolioStock(portfolioStock);
    }

    @GetMapping
    @RequestMapping("/getAllStocks/{portfolioId}")
    public List<PortfolioStock> getAllStocksbyPortfolioId(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioStockService.getAllStocksbyPortfolioId(portfolioId);
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public String deleteStock(@RequestBody PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        return portfolioStockService.deleteStock(portfolioStock);
    }


    @PostMapping
    @RequestMapping("/update")
    public String updateStockQuantity(@RequestBody PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {

//    public String updateStockQuantity(@RequestBody List<PortfolioStock> portfolioStocks) throws ExecutionException, InterruptedException {
        return portfolioStockService.updatePortfolioStock(portfolioStock.getPortfolioId(), portfolioStock.getStockTicker(), portfolioStock.getQuantity());
//        return portfolioStockService.updatePortfolioStocks(portfolioStocks);
    }

    @GetMapping
    @RequestMapping("/getsectorsbyportfolio/{portfolioId}")
    public ResponseEntity<Map<String, Integer>> getSectorsByPortfolioId(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        Map<String, Integer> sectorCounts = portfolioStockService.getSectorsByPortfolioId(portfolioId);
        if (sectorCounts != null) {
            return new ResponseEntity<>(sectorCounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping
//    @RequestMapping("/getAllStocksbyuserid/{userId}")
//    public List<PortfolioStock> getAllStocksbyUserId(@PathVariable String userId) throws ExecutionException, InterruptedException {
//        return portfolioStockService.getAllStocksbyUserId(userId);
//    }

    @GetMapping
    @RequestMapping("/getsectorsbyuser/{userId}")
    public ResponseEntity<Map<String, Integer>> getSectorsByUserId(@PathVariable String userId) throws ExecutionException, InterruptedException {
        Map<String, Integer> sectorCounts = portfolioStockService.getSectorsByUserId(userId);
        if (sectorCounts != null) {
            return new ResponseEntity<>(sectorCounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
