package com.trading.application.customer.controller;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @InjectMocks
    private CustomerController customerController;

    /**
     * The Customer service.
     */
    @Mock
    private CustomerService customerService;

    /**
     * The Token.
     */
    String token = "abc";

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImpER3QxOC1LQ09lM25wTWZacGhjRiJ9" +
                ".eyJpc3MiOiJodHRwczovL2Rldi1oemtzajg0NjhoZ2o0cTVmLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJuUXZNSmMyQUlrNGFxa2pHUWtCdHFLSVJaWVRnNHBXREBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9nb2xkbWFuLmNvbSIsImlhdCI6MTY5ODc0MTExMywiZXhwIjoxNzAxMzMzMTEzLCJhenAiOiJuUXZNSmMyQUlrNGFxa2pHUWtCdHFLSVJaWVRnNHBXRCIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.gJCNGKAKSrI8366jKpr24-lGjoPPbb-Ce0Q7kFQc0iTDNxuJ57g-3602dRv9bLlN9mltILAF6a1-gqmuGEepG8Ey79wdAvRgZahgCdgRoRd7yc1gYTaSlmSvWrPm41XJOxAtB23d3H4yT38RdAn6CqX3V7vp5KR1t3nUu4-Gk_eel5N6a1cckYk70Fh5OahaYT__-rUPxNcoWzs_FgQefUsAwwZaEzCSlVnk4ZRivx5ph4Ygu8-jp36m_nKwo7g0Es3da_2p_wFSXMKF88Wf35bqicFMVR7L-cCWLyx9ITjx41hUxoXKCVh3hNj3lsky-E9cxqFx555x7_s_l8jnAg";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
    }

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
        when(customerService.deleteCustomerAccount(userId)).thenReturn("AccountDeleted");

        String actualResponse = customerController.deleteCustomerAccount(userId);

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

        assertEquals(expectedCapital, actualCapital);
    }

}