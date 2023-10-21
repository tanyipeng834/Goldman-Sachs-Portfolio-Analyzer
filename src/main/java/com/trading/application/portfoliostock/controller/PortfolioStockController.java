package com.trading.application.portfoliostock.controller;

import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Portfolio stock controller.
 */
@RestController
@RequestMapping("/portfoliostock")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioStockController {

    /**
     * The Portfolio stock service.
     */
    @Autowired
    private PortfolioStockService portfolioStockService;

}
