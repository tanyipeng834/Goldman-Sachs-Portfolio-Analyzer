package com.trading.application.portfoliostock.controller;

import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code PortfolioStockController} class handles HTTP requests related to portfolio stocks.
 */
@RestController
@RequestMapping("/portfoliostock")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioStockController {

    /**
     * The {@code PortfolioStockService} is responsible for providing services related to portfolio stocks.
     */
    @Autowired
    private PortfolioStockService portfolioStockService;

}
