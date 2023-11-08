package com.trading.application.portfolio.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.trading.application.logs.service.AccessLogService;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfolio.repository.PortfolioRepository;
import com.trading.application.portfoliostock.entity.PortfolioStock;
import com.trading.application.stock.entity.Stock;
import com.trading.application.stock.service.StockService;
import com.trading.application.stockprice.service.StockPricesService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

/**
 * The type Portfolio service test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PortfolioServiceTest {

    @InjectMocks
    @Spy
    private PortfolioService portfolioService;

    @Mock
    private AccessLogService accessLogService;

    @Mock
    private PortfolioRepository portfolioRepo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private StockService stockService;

    @Mock
    private StockPricesService stockPricesService;

    @Mock
    private Stock stock;

    @Test
    public void testCreatePortfolio() throws Exception {
        // Arrange
        Portfolio portfolio = new Portfolio();
        portfolio.setCapital(666666666);
        when(portfolioRepo.createPortfolio(portfolio)).thenReturn("Success");

        // Act
        ResponseEntity<String> response = portfolioService.createPortfolio(portfolio, request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody());
    }

    @Test
    public void testGetPortfolio() throws Exception {
        Portfolio expectedPortfolio = new Portfolio();
        expectedPortfolio.setPortfolioId("123");
        Mockito.when(portfolioRepo.getPortfolio("portfolio123")).thenReturn(expectedPortfolio);

        Portfolio portfolio = portfolioService.getPortfolio("portfolio123");

        assertEquals(expectedPortfolio, portfolio);
    }

    @Test
    public void testDeletePortfolio() throws Exception {
        String portfolioId = "portfolio123";
        ResponseEntity<String> expectedResult = ResponseEntity.ok("Deleted");

        Mockito.when(portfolioRepo.deletePortfolio(portfolioId)).thenReturn("Deleted");

        ResponseEntity<String> response = portfolioService.deletePortfolio(portfolioId);

        assertEquals(expectedResult, response);
    }

    @Test
    public void testGetSectorsByPortfolioId() throws Exception {
        // Arrange
        String portfolioId = "testPortfolioId";
        Portfolio portfolio = new Portfolio();
        portfolio.setPortStock(new HashMap<>());
        when(portfolioRepo.getPortfolio(portfolioId)).thenReturn(portfolio);

        String stockTicker = "ABC";
        PortfolioStock stock = new PortfolioStock();
        Map<String, List<PortfolioStock>> myStocks = new HashMap<>();
        myStocks.put(stockTicker, Collections.singletonList(stock));
        portfolio.setPortStock(myStocks);

        Stock mockstock = new Stock();
        mockstock.setSector("TestSector");

        when(stockService.getStockOverview(stockTicker)).thenReturn(mockstock);

        // Act
        Map<String, Integer> sectorCounts = portfolioService.getSectorsByPortfolioId(portfolioId);

        // Assert
        verify(portfolioRepo).getPortfolio(portfolioId);
        verify(stockService).getStockOverview(stockTicker);

        assertEquals(1, sectorCounts.size());
        assertTrue(sectorCounts.containsKey("TestSector"));
        assertEquals(1, (int) sectorCounts.get("TestSector"));
    }

    @Test
    public void testGetSectorsByUserId() throws Exception {
        String userId = "yourUserId";
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId("portfolio1");

        Map<String, Integer> sectorCounts = new HashMap<>();
        sectorCounts.put("Technology", 1);
        sectorCounts.put("Manufacturing", 2);

        List<Portfolio> mockPortfolios = Arrays.asList(
                portfolio
        );
        Mockito.when(portfolioRepo.getAllPortfolios(userId)).thenReturn(mockPortfolios);

        when(portfolioService.getSectorsByPortfolioId("portfolio1")).thenReturn(sectorCounts);

        Map<String, Integer> result = portfolioService.getSectorsByUserId(userId);

        assertEquals(2, result.size());
        assertTrue(result.containsKey("Technology"));
        assertEquals(1, (int) result.get("Technology"));
    }


    @Test
    public void testRebalance() throws Exception {

    }

    @Test
    public void testRebalanceValue() throws Exception {

    }

    @Test
    public void testRebalanceStock() throws Exception {

    }

    @Test
    public void testGetTotalPortfolioValue() throws ExecutionException, InterruptedException {
        String portfolioId = "portfolio123";
        float expectedValue = 1000.0f;

        Mockito.when(portfolioRepo.calculatePortfolioValue(portfolioId)).thenReturn(expectedValue);

        float totalValue = portfolioService.getTotalPortfolioValue(portfolioId);

        assertEquals(expectedValue, totalValue);
    }

    @Test
    public void testGetAllPublicPortfolios() throws ExecutionException, InterruptedException {

        ArrayList<Portfolio> publicPortfolios = new ArrayList<>();
        Portfolio portfolio1 = new Portfolio();
        Portfolio portfolio2 = new Portfolio();
        portfolio1.setPortfolioId("1");
        portfolio2.setPortfolioId("2");
        publicPortfolios.add(portfolio1);
        publicPortfolios.add(portfolio2);

        Mockito.when(portfolioRepo.getAllPublicPortfolios()).thenReturn(publicPortfolios);

        ArrayList<Portfolio> portfolios = portfolioService.getAllPublicPortfolios();

        assertEquals(2, portfolios.size());
        assertEquals("1", portfolios.get(0).getPortfolioId());
        assertEquals("2", portfolios.get(1).getPortfolioId());
    }

}

