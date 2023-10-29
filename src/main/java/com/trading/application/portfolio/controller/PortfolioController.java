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
 * Portfolio Controller class for handling portfolio-related HTTP requests.
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
     * @param portfolio the portfolio for which to be created.
     * @param request the request to retrieve the IP address of the client that makes the HTTP request.
     * @return a `ResponseEntity` containing a `String` response indicating the result
     *         of the portfolio creation operation.
     */
    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> createPortfolio(@RequestBody Portfolio portfolio, HttpServletRequest request) {
        return portfolioService.createPortfolio(portfolio, request);
    }

    /**
     * Gets portfolio by portfolio id
     *
     * @param portfolioId the portfolio id of the portfolio to be retrieved.
     * @return the portfolio object representing the portfolio with the given portfolioId.
     * @throws ExecutionException   If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @GetMapping
    @RequestMapping("/{portfolioId}")
    public Portfolio getPortfolio(@PathVariable String portfolioId) throws ExecutionException, InterruptedException {
        return portfolioService.getPortfolio(portfolioId);
    }

    /**
     * Delete portfolio by portfolio id.
     *
     * @param portfolioId the portfolio id of the portfolio to be deleted.
     * @return a ResponseEntity containing a message indicating the result of the deletion operation.
     *  *         - HttpStatus.OK (200) if the portfolio was successfully deleted.
     *  *         - HttpStatus.NOT_FOUND (404) if the portfolio with the given portfolioId was not found.
     *  *         - HttpStatus.INTERNAL_SERVER_ERROR (500) if an error occurred during the deletion.
     */
    @DeleteMapping
    @RequestMapping("/delete/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String portfolioId) {
        return portfolioService.deletePortfolio(portfolioId);
    }

    /**
     * Update portfolio based on request body.
     *
     * @param portfolio The Portfolio object containing the updated information for the portfolio.
     * @param request   the request to retrieve the IP address of the client that makes the HTTP request.
     * @return a ResponseEntity containing a message indicating the result of the deletion operation.
     *  *         - HttpStatus.OK (200) if the portfolio was successfully deleted.
     *  *         - HttpStatus.NOT_FOUND (404) if the portfolio with the given portfolioId was not found.
     *  *         - HttpStatus.INTERNAL_SERVER_ERROR (500) if an error occurred during the deletion.
     * @throws ExecutionException   If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @PutMapping
    @RequestMapping("/updateportfolio")
    public ResponseEntity<String> updatePortfolio(@RequestBody Portfolio portfolio, HttpServletRequest request) throws ExecutionException,
            InterruptedException {
        return portfolioService.updatePortfolio(portfolio, request);
    }


    /**
     * Gets all portfolios of a user by user id.
     *
     * @param userId the user id of the user for whom the portfolios are to be retrieved.
     * @return a List of Portfolio objects representing the portfolios associated with the user.
     * @throws ExecutionException   If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @GetMapping
    @RequestMapping("/getportfolios/{userId}")
    public List<Portfolio> getAllPortfolios(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return portfolioService.getAllPortfolios(userId);
    }

    /**
     * Gets sectors by portfolio id.
     *
     * @param portfolioId the portfolio id of the portfolio for which sector information is to be retrieved.
     * @return A ResponseEntity containing a map of sector names and counts if found.
     *         - HttpStatus.OK (200) if the data was successfully retrieved.
     *         - HttpStatus.NOT_FOUND (404) if the portfolio with the given portfolioId was not found.
     * @throws ExecutionException   If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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
     * @param userId the user id of the user for whom sector information is to be retrieved.
     * @return A ResponseEntity containing a map of sector names and counts if found.
     *         - HttpStatus.OK (200) if the data was successfully retrieved.
     *         - HttpStatus.NOT_FOUND (404) if no data was found for the specified user.
     * @throws ExecutionException   If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
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

    /**
     * Gets all public portfolios.
     *
     * @return A ResponseEntity containing a list of public portfolios.
     *         - HttpStatus.OK (200) if the public portfolios were successfully retrieved.
     * @throws ExecutionException   If an error occurs during execution.
     * @throws InterruptedException If the operation is interrupted.
     */
    @GetMapping
    @RequestMapping("/getpublicportfolios")
    public ResponseEntity<ArrayList<Portfolio>> getAllPublicPortfolios() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(portfolioService.getAllPublicPortfolios(), HttpStatus.OK);
    }

}
