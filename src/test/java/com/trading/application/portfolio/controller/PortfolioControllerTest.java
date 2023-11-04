package com.trading.application.portfolio.controller;

import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.service.PortfolioService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PortfolioControllerTest {

    @InjectMocks
    private PortfolioController portfolioController;

    @Mock
    private PortfolioService portfolioService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImpER3QxOC1LQ09lM25wTWZacGhjRiJ9" +
                ".eyJpc3MiOiJodHRwczovL2Rldi1oemtzajg0NjhoZ2o0cTVmLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJuUXZNSmMyQUlrNGFxa2pHUWtCdHFLSVJaWVRnNHBXREBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9nb2xkbWFuLmNvbSIsImlhdCI6MTY5ODc0MTExMywiZXhwIjoxNzAxMzMzMTEzLCJhenAiOiJuUXZNSmMyQUlrNGFxa2pHUWtCdHFLSVJaWVRnNHBXRCIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.gJCNGKAKSrI8366jKpr24-lGjoPPbb-Ce0Q7kFQc0iTDNxuJ57g-3602dRv9bLlN9mltILAF6a1-gqmuGEepG8Ey79wdAvRgZahgCdgRoRd7yc1gYTaSlmSvWrPm41XJOxAtB23d3H4yT38RdAn6CqX3V7vp5KR1t3nUu4-Gk_eel5N6a1cckYk70Fh5OahaYT__-rUPxNcoWzs_FgQefUsAwwZaEzCSlVnk4ZRivx5ph4Ygu8-jp36m_nKwo7g0Es3da_2p_wFSXMKF88Wf35bqicFMVR7L-cCWLyx9ITjx41hUxoXKCVh3hNj3lsky-E9cxqFx555x7_s_l8jnAg";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
    }

    @Test
    public void testCreatePortfolio() {
        // Arrange
        Portfolio portfolio = new Portfolio();

        when(portfolioService.createPortfolio(portfolio, request)).thenReturn(ResponseEntity.ok("Portfolio created " +
                "successfully"));

        // Act
        ResponseEntity<String> response = portfolioController.createPortfolio(portfolio, request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Portfolio created successfully", response.getBody());
    }


    @Test
    void testGetPortfolio() throws ExecutionException, InterruptedException {
        // Arrange
        String portfolioId = "123";
        Portfolio expectedPortfolio = new Portfolio(); // Create a mock portfolio object

        when(portfolioService.getPortfolio(portfolioId)).thenReturn(expectedPortfolio);

        // Act
        Portfolio result = portfolioController.getPortfolio(portfolioId);

        // Assert
        assertEquals(expectedPortfolio, result);
    }

    @Test
    public void testDeletePortfolio() throws ExecutionException, InterruptedException {
        // Arrange
        String portfolioId = "123";
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Portfolio deleted successfully");

        when(portfolioService.deletePortfolio(portfolioId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = portfolioController.deletePortfolio(portfolioId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testUpdatePortfolio() throws ExecutionException, InterruptedException {
        // Arrange
        Portfolio portfolio = new Portfolio();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Portfolio updated successfully");

        when(portfolioService.updatePortfolio(portfolio, request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = portfolioController.updatePortfolio(portfolio, request);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetAllPortfolios() throws ExecutionException, InterruptedException {
        // Arrange
        String userId = "123";
        List<Portfolio> expectedPortfolios = new ArrayList<>(); // Create a list of mock portfolios

        when(portfolioService.getAllPortfolios(userId)).thenReturn(expectedPortfolios);

        // Act
        List<Portfolio> result = portfolioController.getAllPortfolios(userId);

        // Assert
        assertEquals(expectedPortfolios, result);
    }

    @Test
    public void testGetSectorsByPortfolioId() throws ExecutionException, InterruptedException {
        // Arrange
        String portfolioId = "123";
        Map<String, Integer> expectedSectorCounts = new HashMap<>(); // Create a map of mock sector counts
        ResponseEntity<Map<String, Integer>> expectedResponse = ResponseEntity.ok(expectedSectorCounts);

        when(portfolioService.getSectorsByPortfolioId(portfolioId)).thenReturn(expectedSectorCounts);

        // Act
        ResponseEntity<Map<String, Integer>> response = portfolioController.getSectorsByPortfolioId(portfolioId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetSectorsByUserId() throws ExecutionException, InterruptedException {
        // Arrange
        String userId = "123";
        Map<String, Integer> allSectorCounts = new HashMap<>(); // Create a map of mock sector counts
        ResponseEntity<Map<String, Integer>> expectedResponse = ResponseEntity.ok(allSectorCounts);

        when(portfolioService.getSectorsByUserId(userId)).thenReturn(allSectorCounts);

        // Act
        ResponseEntity<Map<String, Integer>> response = portfolioController.getSectorsByUserId(userId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetAllPublicPortfolios() throws ExecutionException, InterruptedException {
        // Arrange
        ArrayList<Portfolio> publicPortfolios = new ArrayList<>(); // Create a list of mock public portfolios
        ResponseEntity<ArrayList<Portfolio>> expectedResponse = ResponseEntity.ok(publicPortfolios);

        when(portfolioService.getAllPublicPortfolios()).thenReturn(publicPortfolios);

        // Act
        ResponseEntity<ArrayList<Portfolio>> response = portfolioController.getAllPublicPortfolios();

        // Assert
        assertEquals(expectedResponse, response);
    }
}