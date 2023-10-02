package com.trading.application.portfoliostock.controller;

import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.portfoliostock.service.PortfolioStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/portfoliostock")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioStockController {

    @Autowired
    private PortfolioStockService portfolioStockService;

}
