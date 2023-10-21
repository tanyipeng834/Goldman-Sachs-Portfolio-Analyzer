package com.trading.application.portfolio.controller;

import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.service.PortfolioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * The type Portfolio controller.
 */
@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioController {

    /**
     * The Portfolio service.
     */
    @Autowired
    private PortfolioService portfolioService;

    /**
     * Create portfolio response entity.
     *
     * @param portfolio the portfolio
     * @param request   the request
     * @return the response entity
     */
    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> createPortfolio(@RequestBody Portfolio portfolio, HttpServletRequest request) {
        return portfolioService.createPortfolio(portfolio, request);
    }

    /**
     * Gets portfolio.
     *
     * @param portfolioId the portfolio id
     * @return the portfolio
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping
    @RequestMapping("/{portfolioId}")
    public Portfolio getPortfolio(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioService.getPortfolio(portfolioId);
    }

    /**
     * Delete portfolio response entity.
     *
     * @param portfolioId the portfolio id
     * @return the response entity
     */
    @DeleteMapping
    @RequestMapping("/delete/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String portfolioId) {
        return portfolioService.deletePortfolio(portfolioId);
    }

    /**
     * Update portfolio response entity.
     *
     * @param portfolio the portfolio
     * @param request   the request
     * @return the response entity
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @PutMapping
    @RequestMapping("/updateportfolio")
    public ResponseEntity<String> updatePortfolio(@RequestBody Portfolio portfolio, HttpServletRequest request) throws ExecutionException,
            InterruptedException {
        return portfolioService.updatePortfolio(portfolio, request);
    }


    /**
     * Gets all portfolios.
     *
     * @param userId the user id
     * @return the all portfolios
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping
    @RequestMapping("/getportfolios/{userId}")
    public List<Portfolio> getAllPortfolios(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return portfolioService.getAllPortfolios(userId);
    }

    /**
     * Gets sectors by portfolio id.
     *
     * @param portfolioId the portfolio id
     * @return the sectors by portfolio id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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

    /**
     * Gets sectors by user id.
     *
     * @param userId the user id
     * @return the sectors by user id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
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

//    @GetMapping
//    @RequestMapping("/gettotalportfoliovalue/{portfolioId}")
//    public int getTotalPortfolioValue(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
//        return portfolioService.getTotalPortfolioValue(portfolioId);
//    }

    /**
     * Gets all public portfolios.
     *
     * @return the all public portfolios
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping
    @RequestMapping("/getpublicportfolios")
    public ResponseEntity<ArrayList<Portfolio>> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(portfolioService.getAllPublicPortfolios(), HttpStatus.OK);
    }

}
