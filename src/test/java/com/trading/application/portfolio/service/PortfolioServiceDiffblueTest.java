//package com.trading.application.portfolio.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.trading.application.logs.service.AccessLogService;
//import com.trading.application.portfolio.entity.Portfolio;
//import com.trading.application.portfoliostock.entity.PortfolioStock;
//import com.trading.application.portfoliostock.service.PortfolioStockService;
//import com.trading.application.stock.service.StockService;
//import com.trading.application.stockprice.service.StockPricesService;
//import jakarta.servlet.http.HttpServletRequest;
//
//import java.time.Month;
//import java.time.YearMonth;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = {Portfolio.class, RedisTemplate.class, ChannelTopic.class})
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = YourApplication.class,
//        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration")
//
//class PortfolioServiceDiffblueTest {
//    @MockBean
//    private AccessLogService accessLogService;
//
//    @Autowired
//    private Portfolio portfolio;
//
//    @Autowired
//    private PortfolioService portfolioService;
//
//    @MockBean
//    private PortfolioStockService portfolioStockService;
//
//    @MockBean
//    private StockPricesService stockPricesService;
//
//    @MockBean
//    private StockService stockService;
//
//    /**
//     * Method under test: {@link PortfolioService#createPortfolio(Portfolio, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testCreatePortfolio() {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   com.diffblue.fuzztest.shared.proxy.BeanInstantiationException: Could not instantiate bean: redisTemplate defined in null
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: Failed to load ApplicationContext for [MergedContextConfiguration@3ad95252 testClass = com.trading.application.portfolio.service.DiffblueFakeClass2, locations = [], classes = [com.trading.application.portfolio.entity.Portfolio, org.springframework.data.redis.core.RedisTemplate, org.springframework.data.redis.listener.ChannelTopic], contextInitializerClasses = [], activeProfiles = [], propertySourceLocations = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@140a3e18, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5d024ecb, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@84b3e7ec, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@9da1, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@e3175c6], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:142)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'redisTemplate': RedisConnectionFactory is required
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1751)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: RedisConnectionFactory is required
//        //       at org.springframework.util.Assert.state(Assert.java:76)
//        //       at org.springframework.data.redis.core.RedisAccessor.afterPropertiesSet(RedisAccessor.java:38)
//        //       at org.springframework.data.redis.core.RedisTemplate.afterPropertiesSet(RedisTemplate.java:130)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1797)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1747)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        portfolioService.createPortfolio(portfolio, new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#getPortfolio(String)}
//     */
//    @Test
//    void testGetPortfolio() throws InterruptedException, ExecutionException {
//        Portfolio portfolio = new Portfolio();
//        when(portfolioService.getPortfolio(Mockito.<String>any())).thenReturn(portfolio);
//        assertSame(portfolio, portfolioService.getPortfolio("42"));
//        verify(portfolioService).getPortfolio(Mockito.<String>any());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#deletePortfolio(String)}
//     */
//    @Test
//    void testDeletePortfolio() {
//        when(portfolioService.deletePortfolio(Mockito.<String>any())).thenReturn(null);
//        assertNull(portfolioService.deletePortfolio("42"));
//        verify(portfolioService).deletePortfolio(Mockito.<String>any());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#updatePortfolio(Portfolio, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testUpdatePortfolio() throws InterruptedException, ExecutionException {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   com.diffblue.fuzztest.shared.proxy.BeanInstantiationException: Could not instantiate bean: redisTemplate defined in null
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: Failed to load ApplicationContext for [MergedContextConfiguration@294e94a0 testClass = com.trading.application.portfolio.service.DiffblueFakeClass1902, locations = [], classes = [com.trading.application.portfolio.entity.Portfolio, org.springframework.data.redis.core.RedisTemplate, org.springframework.data.redis.listener.ChannelTopic], contextInitializerClasses = [], activeProfiles = [], propertySourceLocations = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@140a3e18, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5d024ecb, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@84b3e7ec, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@9da1, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@e3175c6], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:142)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'redisTemplate': RedisConnectionFactory is required
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1751)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: RedisConnectionFactory is required
//        //       at org.springframework.util.Assert.state(Assert.java:76)
//        //       at org.springframework.data.redis.core.RedisAccessor.afterPropertiesSet(RedisAccessor.java:38)
//        //       at org.springframework.data.redis.core.RedisTemplate.afterPropertiesSet(RedisTemplate.java:130)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1797)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1747)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        portfolioService.updatePortfolio(portfolio, new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#getAllPortfolios(String)}
//     */
//    @Test
//    void testGetAllPortfolios() throws InterruptedException, ExecutionException {
//        ArrayList<Portfolio> portfolioList = new ArrayList<>();
//        when(portfolioService.getAllPortfolios(Mockito.<String>any())).thenReturn(portfolioList);
//        List<Portfolio> actualAllPortfolios = portfolioService.getAllPortfolios("42");
//        assertSame(portfolioList, actualAllPortfolios);
//        assertTrue(actualAllPortfolios.isEmpty());
//        verify(portfolioService).getAllPortfolios(Mockito.<String>any());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#getSectorsByPortfolioId(String)}
//     */
//    @Test
//    void testGetSectorsByPortfolioId() throws InterruptedException, ExecutionException {
//        HashMap<String, Integer> stringIntegerMap = new HashMap<>();
//        when(portfolioService.getSectorsByPortfolioId(Mockito.<String>any())).thenReturn(stringIntegerMap);
//        Map<String, Integer> actualSectorsByPortfolioId = portfolioService.getSectorsByPortfolioId("42");
//        assertSame(stringIntegerMap, actualSectorsByPortfolioId);
//        assertTrue(actualSectorsByPortfolioId.isEmpty());
//        verify(portfolioService).getSectorsByPortfolioId(Mockito.<String>any());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#getSectorsByUserId(String)}
//     */
//    @Test
//    void testGetSectorsByUserId() throws InterruptedException, ExecutionException {
//        HashMap<String, Integer> stringIntegerMap = new HashMap<>();
//        when(portfolioService.getSectorsByUserId(Mockito.<String>any())).thenReturn(stringIntegerMap);
//        Map<String, Integer> actualSectorsByUserId = portfolioService.getSectorsByUserId("42");
//        assertSame(stringIntegerMap, actualSectorsByUserId);
//        assertTrue(actualSectorsByUserId.isEmpty());
//        verify(portfolioService).getSectorsByUserId(Mockito.<String>any());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#rebalance(Portfolio, String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRebalance() throws JsonProcessingException, InterruptedException, ExecutionException {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   com.diffblue.fuzztest.shared.proxy.BeanInstantiationException: Could not instantiate bean: redisTemplate defined in null
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: Failed to load ApplicationContext for [MergedContextConfiguration@a58d16f testClass = com.trading.application.portfolio.service.DiffblueFakeClass1837, locations = [], classes = [com.trading.application.portfolio.entity.Portfolio, org.springframework.data.redis.core.RedisTemplate, org.springframework.data.redis.listener.ChannelTopic], contextInitializerClasses = [], activeProfiles = [], propertySourceLocations = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@140a3e18, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5d024ecb, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@84b3e7ec, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@9da1, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@e3175c6], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:142)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'redisTemplate': RedisConnectionFactory is required
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1751)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: RedisConnectionFactory is required
//        //       at org.springframework.util.Assert.state(Assert.java:76)
//        //       at org.springframework.data.redis.core.RedisAccessor.afterPropertiesSet(RedisAccessor.java:38)
//        //       at org.springframework.data.redis.core.RedisTemplate.afterPropertiesSet(RedisTemplate.java:130)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1797)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1747)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        portfolioService.rebalance(portfolio, "42 Main St");
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#rebalanceValue(YearMonth, Map, Portfolio, String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRebalanceValue() throws JsonProcessingException, InterruptedException, ExecutionException {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   com.diffblue.fuzztest.shared.proxy.BeanInstantiationException: Could not instantiate bean: redisTemplate defined in null
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: Failed to load ApplicationContext for [MergedContextConfiguration@4c1d8308 testClass = com.trading.application.portfolio.service.DiffblueFakeClass1877, locations = [], classes = [com.trading.application.portfolio.entity.Portfolio, org.springframework.data.redis.core.RedisTemplate, org.springframework.data.redis.listener.ChannelTopic], contextInitializerClasses = [], activeProfiles = [], propertySourceLocations = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@140a3e18, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5d024ecb, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@84b3e7ec, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@9da1, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@e3175c6], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:142)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'redisTemplate': RedisConnectionFactory is required
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1751)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: RedisConnectionFactory is required
//        //       at org.springframework.util.Assert.state(Assert.java:76)
//        //       at org.springframework.data.redis.core.RedisAccessor.afterPropertiesSet(RedisAccessor.java:38)
//        //       at org.springframework.data.redis.core.RedisTemplate.afterPropertiesSet(RedisTemplate.java:130)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1797)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1747)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        YearMonth stockTime = YearMonth.of(1970, Month.JANUARY);
//        HashMap<String, List<PortfolioStock>> portMap = new HashMap<>();
//        portfolioService.rebalanceValue(stockTime, portMap, portfolio, "42 Main St");
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#rebalanceStock(List, float, Portfolio, String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRebalanceStock() throws InterruptedException, ExecutionException {
//        // TODO: Complete this test.
//        //   Reason: R026 Failed to create Spring context.
//        //   Attempt to initialize test context failed with
//        //   com.diffblue.fuzztest.shared.proxy.BeanInstantiationException: Could not instantiate bean: redisTemplate defined in null
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: Failed to load ApplicationContext for [MergedContextConfiguration@1d74d271 testClass = com.trading.application.portfolio.service.DiffblueFakeClass1853, locations = [], classes = [com.trading.application.portfolio.entity.Portfolio, org.springframework.data.redis.core.RedisTemplate, org.springframework.data.redis.listener.ChannelTopic], contextInitializerClasses = [], activeProfiles = [], propertySourceLocations = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@140a3e18, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@5d024ecb, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@84b3e7ec, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@9da1, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@e3175c6], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:142)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'redisTemplate': RedisConnectionFactory is required
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1751)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   java.lang.IllegalStateException: RedisConnectionFactory is required
//        //       at org.springframework.util.Assert.state(Assert.java:76)
//        //       at org.springframework.data.redis.core.RedisAccessor.afterPropertiesSet(RedisAccessor.java:38)
//        //       at org.springframework.data.redis.core.RedisTemplate.afterPropertiesSet(RedisTemplate.java:130)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1797)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1747)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:599)
//        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326)
//        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324)
//        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200)
//        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:961)
//        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:915)
//        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:584)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:221)
//        //       at org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:110)
//        //       at org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:212)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:184)
//        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:118)
//        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:127)
//        //       at java.util.Optional.map(Optional.java:260)
//        //   See https://diff.blue/R026 to resolve this issue.
//
//        ArrayList<PortfolioStock> portStock = new ArrayList<>();
//        portfolioService.rebalanceStock(portStock, 10.0f, portfolio, "42 Main St");
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#getTotalPortfolioValue(String)}
//     */
//    @Test
//    void testGetTotalPortfolioValue() throws InterruptedException, ExecutionException {
//        when(portfolioService.getTotalPortfolioValue(Mockito.<String>any())).thenReturn(10.0f);
//        assertEquals(10.0f, portfolioService.getTotalPortfolioValue("42"));
//        verify(portfolioService).getTotalPortfolioValue(Mockito.<String>any());
//    }
//
//    /**
//     * Method under test: {@link PortfolioService#getAllPublicPortfolios()}
//     */
//    @Test
//    void testGetAllPublicPortfolios() throws InterruptedException, ExecutionException {
//        ArrayList<Portfolio> portfolioList = new ArrayList<>();
//        when(portfolioService.getAllPublicPortfolios()).thenReturn(portfolioList);
//        ArrayList<Portfolio> actualAllPublicPortfolios = portfolioService.getAllPublicPortfolios();
//        assertSame(portfolioList, actualAllPublicPortfolios);
//        assertTrue(actualAllPublicPortfolios.isEmpty());
//        verify(portfolioService).getAllPublicPortfolios();
//    }
//}
//