package com.trading.application.portfolio.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.trading.application.portfolio.entity.Portfolio;
import com.trading.application.portfoliostock.entity.PortfolioStock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * The type Portfolio repository test.
 */
/**
 * The type Portfolio repository test.
 */
@ContextConfiguration(classes = {Portfolio.class, PortfolioRepository.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class PortfolioRepositoryTest {
    /**
     * The Portfolio.
     */
    @Autowired
    private Portfolio portfolio;

    /**
     * The Portfolio repository.
     */
    @MockBean
    private PortfolioRepository portfolioRepository;

    /**
     * Method under test: {@link PortfolioRepository#getReferenceById(String)}
     */
    @Test
    void testGetReferenceById() {
        when(portfolioRepository.getReferenceById(Mockito.<String>any())).thenReturn(null);
        assertNull(portfolioRepository.getReferenceById("42"));
        verify(portfolioRepository).getReferenceById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#createPortfolio(Portfolio)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCreatePortfolio() throws InterruptedException, ExecutionException {
        when(portfolioRepository.createPortfolio(Mockito.<Portfolio>any())).thenReturn("Create Portfolio");
        assertEquals("Create Portfolio", portfolioRepository.createPortfolio(portfolio));
        verify(portfolioRepository).createPortfolio(Mockito.<Portfolio>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#calculatePortfolioValue(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCalculatePortfolioValue() throws InterruptedException, ExecutionException {
        when(portfolioRepository.calculatePortfolioValue(Mockito.<String>any())).thenReturn(10.0f);
        assertEquals(10.0f, portfolioRepository.calculatePortfolioValue("42"));
        verify(portfolioRepository).calculatePortfolioValue(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#calculatePortfolioValue(Map)}
     */
    @Test
    void testCalculatePortfolioValue2() {
        when(portfolioRepository.calculatePortfolioValue(Mockito.<Map<String, List<PortfolioStock>>>any()))
                .thenReturn(10.0f);
        assertEquals(10.0f, portfolioRepository.calculatePortfolioValue(new HashMap<>()));
        verify(portfolioRepository).calculatePortfolioValue(Mockito.<Map<String, List<PortfolioStock>>>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#updatePortfolio(Portfolio)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testUpdatePortfolio() throws InterruptedException, ExecutionException {
        when(portfolioRepository.updatePortfolio(Mockito.<Portfolio>any())).thenReturn("2020-03-01");
        assertEquals("2020-03-01", portfolioRepository.updatePortfolio(portfolio));
        verify(portfolioRepository).updatePortfolio(Mockito.<Portfolio>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#getAllPublicPortfolios()}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetAllPublicPortfolios() throws InterruptedException, ExecutionException {
        ArrayList<Portfolio> portfolioList = new ArrayList<>();
        when(portfolioRepository.getAllPublicPortfolios()).thenReturn(portfolioList);
        ArrayList<Portfolio> actualAllPublicPortfolios = portfolioRepository.getAllPublicPortfolios();
        assertSame(portfolioList, actualAllPublicPortfolios);
        assertTrue(actualAllPublicPortfolios.isEmpty());
        verify(portfolioRepository).getAllPublicPortfolios();
    }

    /**
     * Method under test: {@link PortfolioRepository#deletePortfolio(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testDeletePortfolio() throws InterruptedException, ExecutionException {
        when(portfolioRepository.deletePortfolio(Mockito.<String>any())).thenReturn("Delete Portfolio");
        assertEquals("Delete Portfolio", portfolioRepository.deletePortfolio("42"));
        verify(portfolioRepository).deletePortfolio(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#updatePortfolioField(String, String, float)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testUpdatePortfolioField() throws InterruptedException, ExecutionException {
        when(portfolioRepository.updatePortfolioField(Mockito.<String>any(), Mockito.<String>any(), anyFloat()))
                .thenReturn("2020-03-01");
        assertEquals("2020-03-01", portfolioRepository.updatePortfolioField("42", "Field", 10.0f));
        verify(portfolioRepository).updatePortfolioField(Mockito.<String>any(), Mockito.<String>any(), anyFloat());
    }

    /**
     * Method under test: {@link PortfolioRepository#updatePortfolioField(String, String, String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testUpdatePortfolioField2() throws InterruptedException, ExecutionException {
        when(
                portfolioRepository.updatePortfolioField(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("2020-03-01");
        assertEquals("2020-03-01", portfolioRepository.updatePortfolioField("42", "Field", "42"));
        verify(portfolioRepository).updatePortfolioField(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#updatePortfolioField(String, String, boolean)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testUpdatePortfolioField3() throws InterruptedException, ExecutionException {
        when(portfolioRepository.updatePortfolioField(Mockito.<String>any(), Mockito.<String>any(), anyBoolean()))
                .thenReturn("2020-03-01");
        assertEquals("2020-03-01", portfolioRepository.updatePortfolioField("42", "Field", true));
        verify(portfolioRepository).updatePortfolioField(Mockito.<String>any(), Mockito.<String>any(), anyBoolean());
    }

    /**
     * Method under test: {@link PortfolioRepository#getPortfolio(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetPortfolio() throws InterruptedException, ExecutionException {
        Portfolio portfolio = new Portfolio();
        when(portfolioRepository.getPortfolio(Mockito.<String>any())).thenReturn(portfolio);
        assertSame(portfolio, portfolioRepository.getPortfolio("42"));
        verify(portfolioRepository).getPortfolio(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PortfolioRepository#getAllPortfolios(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetAllPortfolios() throws InterruptedException, ExecutionException {
        ArrayList<Portfolio> portfolioList = new ArrayList<>();
        when(portfolioRepository.getAllPortfolios(Mockito.<String>any())).thenReturn(portfolioList);
        List<Portfolio> actualAllPortfolios = portfolioRepository.getAllPortfolios("42");
        assertSame(portfolioList, actualAllPortfolios);
        assertTrue(actualAllPortfolios.isEmpty());
        verify(portfolioRepository).getAllPortfolios(Mockito.<String>any());
    }
}

