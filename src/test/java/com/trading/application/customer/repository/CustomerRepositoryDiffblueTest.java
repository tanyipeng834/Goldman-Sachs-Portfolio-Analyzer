package com.trading.application.customer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {Customer.class, String.class})
@ExtendWith(SpringExtension.class)
class CustomerRepositoryDiffblueTest {
    @Autowired
    private Customer customer;

    @MockBean
    private CustomerRepository customerRepository;

    /**
     * Method under test: {@link CustomerRepository#getReferenceById(String)}
     */
    @Test
    void testGetReferenceById() {
        when(customerRepository.getReferenceById(Mockito.<String>any())).thenReturn(null);
        assertNull(customerRepository.getReferenceById("42"));
        verify(customerRepository).getReferenceById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerRepository#addCustomer(Customer)}
     */
    @Test
    void testAddCustomer() throws InterruptedException, ExecutionException {
        when(customerRepository.addCustomer(Mockito.<Customer>any())).thenReturn("Add Customer");
        assertEquals("Add Customer", customerRepository.addCustomer(customer));
        verify(customerRepository).addCustomer(Mockito.<Customer>any());
    }

    /**
     * Method under test: {@link CustomerRepository#getById(String)}
     */
    @Test
    void testGetById() throws InterruptedException, ExecutionException {
        Customer customer = new Customer("Name", "jane.doe@example.org");

        when(customerRepository.getById(Mockito.<String>any())).thenReturn(customer);
        assertSame(customer, customerRepository.getById("42"));
        verify(customerRepository).getById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerRepository#updateDocumentField(String, String, String)}
     */
    @Test
    void testUpdateDocumentField() throws InterruptedException, ExecutionException {
        when(customerRepository.updateDocumentField(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("2020-03-01");
        assertEquals("2020-03-01", customerRepository.updateDocumentField("42", "Field", "42"));
        verify(customerRepository).updateDocumentField(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerRepository#updateTotalCapitalAvailable(String, String, int)}
     */
    @Test
    void testUpdateTotalCapitalAvailable() throws InterruptedException, ExecutionException {
        when(customerRepository.updateTotalCapitalAvailable(Mockito.<String>any(), Mockito.<String>any(), anyInt()))
                .thenReturn("2020-03-01");
        assertEquals("2020-03-01", customerRepository.updateTotalCapitalAvailable("42", "Field", 42));
        verify(customerRepository).updateTotalCapitalAvailable(Mockito.<String>any(), Mockito.<String>any(), anyInt());
    }

    /**
     * Method under test: {@link CustomerRepository#deleteCustomerAccount(String)}
     */
    @Test
    void testDeleteCustomerAccount() {
        when(customerRepository.deleteCustomerAccount(Mockito.<String>any())).thenReturn("3");
        assertEquals("3", customerRepository.deleteCustomerAccount("42"));
        verify(customerRepository).deleteCustomerAccount(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerRepository#getTotalCapitalAvailable(String)}
     */
    @Test
    void testGetTotalCapitalAvailable() throws InterruptedException, ExecutionException {
        when(customerRepository.getTotalCapitalAvailable(Mockito.<String>any())).thenReturn(10.0f);
        assertEquals(10.0f, customerRepository.getTotalCapitalAvailable("42"));
        verify(customerRepository).getTotalCapitalAvailable(Mockito.<String>any());
    }
}

