package com.trading.application.portfoliostock.controller;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/portfoliostock")
public class PortfolioStockController {

    @Autowired
    private PortfolioStockService portfolioStockService;

    @PostMapping
    @RequestMapping("/create")
    public String createPortfolioStock(@RequestBody PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        return portfolioStockService.createPortfolioStock(portfolioStock);
    }

    @GetMapping
    @RequestMapping("/getAllStocks/{portfolioId}")
    public List<PortfolioStock> getAllStocks(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioStockService.getAllStocks(portfolioId);
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public String deleteStock(@RequestBody PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        return portfolioStockService.deleteStock(portfolioStock);
    }

    @PostMapping
    @RequestMapping("/update")
    public String updateStockQuantity(@RequestBody PortfolioStock portfolioStock) throws ExecutionException, InterruptedException {
        return portfolioStockService.updatePortfolioStock(portfolioStock.getPortfolioId(), portfolioStock.getStockTicker(), portfolioStock.getQuantity());
    }

}
