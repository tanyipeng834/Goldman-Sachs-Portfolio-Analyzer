package com.trading.application.portfolio.controller;

import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // create new portfolio
    @PostMapping
    @RequestMapping("/create")
    public String createPortfolio(@RequestBody Portfolio portfolio) throws ExecutionException, InterruptedException {
        return portfolioService.createPortfolio(portfolio);
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
    public String deletePortfolio( @PathVariable String portfolioId) throws  ExecutionException, InterruptedException {
        return portfolioService.deletePortfolio(portfolioId);
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

    // get all portfolios of a customer
    @GetMapping
    @RequestMapping("/getportfolios/{userId}")
    public List getAllPortfolios(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return portfolioService.getAllPortfolios(userId);
    }
}
