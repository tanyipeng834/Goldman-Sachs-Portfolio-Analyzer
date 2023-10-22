package com.trading.application.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.trading.application.customer.entity.Customer;
import java.util.concurrent.ExecutionException;
import com.trading.application.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The type Customer service test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceTest {

    /**
     * The Customer service.
     */
    @InjectMocks
    CustomerService customerService;

    /**
     * The Customer repository.
     */
    @Mock
    private CustomerRepository customerRepo;

    /**
     * Method under test: {@link CustomerService#createCustomer(Customer)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCreateCustomer() throws InterruptedException, ExecutionException {
        when(customerRepo.addCustomer(Mockito.any(Customer.class))).thenReturn("CustomerAdded");
        Customer customer = new Customer();

        String actualResponse = customerService.createCustomer(customer);

        assertEquals("CustomerAdded", actualResponse);
        verify(customerRepo).addCustomer(customer);
    }

    /**
     * Method under test: {@link CustomerService#getCustomer(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetCustomer() throws InterruptedException, ExecutionException {
        String customerId = "42";
        Customer expectedCustomer = new Customer();
        when(customerRepo.getById(customerId)).thenReturn(expectedCustomer);

        Customer actualCustomer = customerService.getCustomer(customerId);

        assertSame(expectedCustomer, actualCustomer);
        verify(customerRepo).getById(customerId);
    }

    /**
     * Method under test: {@link CustomerService#updateCustomerName(String, String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testUpdateCustomerName() throws InterruptedException, ExecutionException {
        String customerId = "42";
        String newName = "New Name";

        when(customerRepo.updateDocumentField(customerId, "name", newName)).thenReturn("2020-03-01");
        String updatedDate = customerService.updateCustomerName(customerId, newName);

        assertEquals("2020-03-01", updatedDate);
        verify(customerRepo).updateDocumentField(customerId, "name", newName);
    }

    /**
     * Method under test: {@link CustomerService#customerUpdateEmail(String, String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testCustomerUpdateEmail() throws InterruptedException, ExecutionException {
        String customerId = "42";
        String newEmail = "new.email@example.com";
        when(customerRepo.updateDocumentField(customerId, "email", newEmail)).thenReturn("2020-03-01");

        String updatedDate = customerService.customerUpdateEmail(customerId, newEmail);

        assertEquals("2020-03-01", updatedDate);

        verify(customerRepo).updateDocumentField(customerId, "email", newEmail);

    }

    /**
     * Method under test: {@link CustomerService#deleteCustomerAccount(String)}
     */
    @Test
    void testDeleteCustomerAccount() {
        String customerId = "42";
        when(customerRepo.deleteCustomerAccount(customerId)).thenReturn("Account Deleted");

        String result = customerService.deleteCustomerAccount(customerId);

        assertEquals("Account Deleted", result);

        verify(customerRepo).deleteCustomerAccount(customerId);
    }

    /**
     * Method under test: {@link CustomerService#getCustomerCapital(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetCustomerCapital() throws InterruptedException, ExecutionException {
        String customerId = "42";
        when(customerRepo.getTotalCapitalAvailable(customerId)).thenReturn(12000);

        int capital = customerService.getCustomerCapital(customerId);

        assertEquals(12000, capital);

        verify(customerRepo).getTotalCapitalAvailable(customerId);
    }

    /**
     * Test get customer capital execution exception.
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetCustomerCapitalExecutionException() throws InterruptedException, ExecutionException {
        String customerId = "42";
        when(customerRepo.getTotalCapitalAvailable(customerId)).thenThrow(new ExecutionException("Execution Exception", null));

        int capital = customerService.getCustomerCapital(customerId);

        assertEquals(-1, capital);
    }

    /**
     * Test get customer capital interrupted exception.
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    void testGetCustomerCapitalInterruptedException() throws InterruptedException, ExecutionException {
        String customerId = "42";
        when(customerRepo.getTotalCapitalAvailable(customerId)).thenThrow(new InterruptedException("Interrupted Exception"));

        int capital = customerService.getCustomerCapital(customerId);

        assertEquals(-2, capital);
    }
}

