package com.trading.application.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.trading.application.customer.entity.Customer;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * The type Customer service test.
 */
@ContextConfiguration(classes = {Customer.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    /**
     * The Customer.
     */
    @Autowired
    private Customer customer;

    /**
     * The Customer service.
     */
    @MockBean
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerService#createCustomer(Customer)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCreateCustomer() throws InterruptedException, ExecutionException {
        when(customerService.createCustomer(Mockito.<Customer>any())).thenReturn("Create Customer");
        assertEquals("Create Customer", customerService.createCustomer(customer));
        verify(customerService).createCustomer(Mockito.<Customer>any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomer(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetCustomer() throws InterruptedException, ExecutionException {
        Customer customer = new Customer();
        when(customerService.getCustomer(Mockito.<String>any())).thenReturn(customer);
        assertSame(customer, customerService.getCustomer("42"));
        verify(customerService).getCustomer(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomerName(String, String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testUpdateCustomerName() throws InterruptedException, ExecutionException {
        when(customerService.updateCustomerName(Mockito.<String>any(), Mockito.<String>any())).thenReturn("2020-03-01");
        assertEquals("2020-03-01", customerService.updateCustomerName("42", "Name"));
        verify(customerService).updateCustomerName(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#customerUpdateEmail(String, String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCustomerUpdateEmail() throws InterruptedException, ExecutionException {
        when(customerService.customerUpdateEmail(Mockito.<String>any(), Mockito.<String>any())).thenReturn("2020-03-01");
        assertEquals("2020-03-01", customerService.customerUpdateEmail("42", "jane.doe@example.org"));
        verify(customerService).customerUpdateEmail(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#customerUpdateCapital(String, int)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCustomerUpdateCapital() throws InterruptedException, ExecutionException {
        when(customerService.customerUpdateCapital(Mockito.<String>any(), anyInt())).thenReturn("2020-03-01");
        assertEquals("2020-03-01", customerService.customerUpdateCapital("42", 1));
        verify(customerService).customerUpdateCapital(Mockito.<String>any(), anyInt());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomerAccount(String)}
     */
    @Test
    void testDeleteCustomerAccount() {
        when(customerService.deleteCustomerAccount(Mockito.<String>any())).thenReturn("3");
        assertEquals("3", customerService.deleteCustomerAccount("42"));
        verify(customerService).deleteCustomerAccount(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerCapital(String)}
     */
    @Test
    void testGetCustomerCapital() {
        when(customerService.getCustomerCapital(Mockito.<String>any())).thenReturn(1);
        assertEquals(1, customerService.getCustomerCapital("42"));
        verify(customerService).getCustomerCapital(Mockito.<String>any());
    }
}

