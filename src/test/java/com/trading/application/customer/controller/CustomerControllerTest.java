package com.trading.application.customer.controller;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * The type Customer controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    /**
     * The Mock mvc.
     */
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Customer customer;

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    /**
     * The Token.
     */
    String token = "abc";
//    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InpiQ09XZjV6WkZYanZ1YUxBcDNGSiJ9" +
//        ".eyJpc3MiOiJodHRwczovL2Rldi00cHhuNHpidGN1b3d3NTdsLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiI1QkxFU1AwNVJKOUlKMzl0WDVHQ0tZTWpDcGFHZmNCWkBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9nb2xkbWFuLmNvbSIsImlhdCI6MTY5NzI2MzI3MSwiZXhwIjoxNjk3MzQ5NjcxLCJhenAiOiI1QkxFU1AwNVJKOUlKMzl0WDVHQ0tZTWpDcGFHZmNCWiIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.YaNqWvEjip13dDKBTNpqLO7emDnKk0GiXgQdvUDHAkt7A5d658WxehkzsgHPOjtNqGYOBJJ3zVKyToz5uzg05qivSFdyXO61-KXGqoojJ9xQON7k2EMUcIF-mHa3cbTbARIZhUYerH3wFTclVXGUFX-qpLmF1Lm43sgkWB0OWkDnDOg1gpTPvcU9XFn7VKyws-2hGthmFPIeVXbbIVFKiTmOefV0HRDMQBd6AdvEUEXAGSeoOhVJ0QfnkoaZtKm5fJRGsnlMA5lplfn_idAUHHe3WLtmuWryYrXkEtzCMQmrQb_aTIvq7YdtXUjvP_KZScfP6oh4AQYSQRyR2bk_9A";

    /**
     * Should create customer.
     *
     * @throws Exception the exception
     */
    @Test
    public void shouldCreateCustomer() throws Exception {

        Customer customer = new Customer();

        Map<String, Object> customerResponseBody = new HashMap<>();
        customerResponseBody.put("customerData", customer);
        customerResponseBody.put("token", token);
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(customerResponseBody, HttpStatus.OK);

        when(customerService.createCustomer(customer)).thenReturn(String.valueOf(customer));

        ResponseEntity<Object> actualResponse = customerController.createCustomer(customer);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());


    }

    /**
     * Should get customer by id.
     *
     * @throws Exception the exception
     */
    @Test
    void shouldGetCustomerById() throws Exception {
        String id = "testid";

        Customer expectedCustomer = new Customer();
        when(customerService.getCustomer(id)).thenReturn(expectedCustomer);

        ResponseEntity<Object> actualResponse = customerController.getCustomerById(id);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }
    /**
     * Should update customer name.
     *
     * @throws Exception the exception
     */
    @Test
    void shouldUpdateCustomerName() throws Exception {

        String name = "new_name";
        String id = "auth0_65192b7fe51e9baa2513bb99";

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName(name);

        when(customerService.updateCustomerName(id, name)).thenReturn(null);

        String actualResponse = customerController.updateCustomerName(updatedCustomer);

        assertEquals(null, actualResponse);
    }

    /**
     * Should delete customer account.
     *
     * @throws Exception the exception
     */
    @Test
    void shouldDeleteCustomerAccount() throws Exception {

        String userId = "testid";
        // Mock the behavior of customerService.deleteCustomerAccount
        when(customerService.deleteCustomerAccount(userId)).thenReturn("AccountDeleted");

        // Call the controller method
        String actualResponse = customerController.deleteCustomerAccount(userId);

        // Assert
        assertEquals("AccountDeleted", actualResponse);

    }

    /**
     * Should get capital.
     *
     * @throws Exception the exception
     */
    @Test
    void shouldGetCapital() throws Exception {

        String userId = "testid";
        int expectedCapital = 12000;

        when(customerService.getCustomerCapital(userId)).thenReturn(expectedCapital);

        int actualCapital = customerController.getCapital(userId);

        // Assert
        assertEquals(expectedCapital, actualCapital);

}

}