package com.trading.application.portfolio.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.entity.PortfolioRequest;
import com.trading.application.portfolio.entity.PortfolioStocksRequest;
import com.trading.application.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> createPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.createPortfolio(portfolio);
    }

    @PostMapping
    @RequestMapping("/add")
    public String addStock(@RequestBody JsonNode portfolioStockData) throws ExecutionException,InterruptedException{

       String portfolioId = portfolioStockData.get("portfolioId").asText();
       String portfolioStockId = portfolioStockData.get("portfolioStockId").asText();
        System.out.println(portfolioId);
        System.out.println(portfolioStockId);
        return portfolioService.addStock(portfolioStockId,portfolioId);
    }

    // get a portfolio
    @GetMapping
    @RequestMapping("/{portfolioId}")
    public Portfolio getPortfolio(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioService.getPortfolio(portfolioId);
    }

    // delete a portfolio
    @DeleteMapping
    @RequestMapping("delete/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String portfolioId) {
        return portfolioService.deletePortfolio(portfolioId);
    }

    // update portfolio. when submit button is clicked
    @PostMapping
    @RequestMapping("/update/{portfolioId}")
    public String updatePortfolioName(@RequestBody PortfolioRequest portfolio, @PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioService.updatePortfolio(portfolio, portfolioId);
    }

    // update portfolio name
    @PostMapping
    @RequestMapping("/updatename")
    public String updatePortfolioName(@RequestBody Portfolio portfolio) throws ExecutionException, InterruptedException {
        return portfolioService.updatePortfolioName(portfolio.getPortfolioId(), portfolio.getPortfolioName());
    }

    // update portfolio description
    @PostMapping
    @RequestMapping("/updatedescription")
    public String updatePortfolioDescription(@RequestBody Portfolio portfolio) throws ExecutionException, InterruptedException {
        return portfolioService.updatePortfolioDescription(portfolio.getPortfolioId(), portfolio.getPortfolioDescription());
    }

    @PutMapping
    @RequestMapping("/updateportfoliostocks/{portfolioId}")
    public String updatePortfolioStocks(@PathVariable String portfolioId,@RequestBody PortfolioStocksRequest portfolioStocks) throws ExecutionException, InterruptedException {
        return portfolioService.updatePortfolioStocks(portfolioId, portfolioStocks);
//        return "test";
    }

    // get all portfolios of a customer
    @GetMapping
    @RequestMapping("/getportfolios/{userId}")
    public List<Portfolio> getAllPortfolios(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return portfolioService.getAllPortfolios(userId);
    }

    // get sectors of all stocks in a portfolio
    @GetMapping
    @RequestMapping("/getsectorsbyportfolio/{portfolioId}")
    public ResponseEntity<Map<String, Integer>> getSectorsByPortfolioId(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        Map<String, Integer> sectorCounts = portfolioService.getSectorsByPortfolioId(portfolioId);
        if (sectorCounts != null) {
            return new ResponseEntity<>(sectorCounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RequestMapping("/getsectorsbyuser/{userId}")
    public ResponseEntity<Map<String, Integer>> getSectorsByUserId(@PathVariable String userId) throws ExecutionException, InterruptedException {
        Map<String, Integer> allSectorCounts = portfolioService.getSectorsByUserId(userId);
        if (allSectorCounts != null) {
            return new ResponseEntity<>(allSectorCounts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
