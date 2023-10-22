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

        // Create a Customer instance
        Customer customer = new Customer();

        // Create the expected response
        String accessToken = "abc";
        Map<String, Object> customerResponseBody = new HashMap<>();
        customerResponseBody.put("customerData", customer);
        customerResponseBody.put("token", accessToken);
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(customerResponseBody, HttpStatus.OK);

        when(customerService.createCustomer(customer)).thenReturn(String.valueOf(customer));

        // Call the controller method
        ResponseEntity<Object> actualResponse = customerController.createCustomer(customer);

        // Assert the HTTP status
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());


    }}

//    /**
//     * Should get customer by id.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    void shouldGetCustomerById() throws Exception {
//        String id = "testid";
//        mockMvc.perform(get("/customer/" + id).header("authorization", "Bearer " + token)).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.id").value(id))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.email").value("test@gmail.com"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.name").value("test.user.2021"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.totalCapitalAvailable").value(10000))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.customerData.updatedAt").value("2023-10-01T08:19:57.640Z"));}
//
//    /**
//     * Should update customer name.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    void shouldUpdateCustomerName() throws Exception {
//
//        String id = "testid";
//        String name = "new_name";
//        String email = "test@gmail.com";
//        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
//        String updatedAt = "2023-10-01T08:19:57.640Z";
//        //to confirm data type
//        float totalCapitalAvailable = 10000;
//
//        String body = String.format(
//                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%f,\"updatedAt\":\"%s\"}",
//                email, id, name, picture, totalCapitalAvailable, updatedAt);
//
//        mockMvc.perform(put("/customer/updatename").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    /**
//     * Should update customer email.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    void shouldUpdateCustomerEmail() throws Exception {
//
//        String id = "testid";
//        String name = "new_name";
//        String email = "newtest@gmail.com";
//        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
//        String updatedAt = "2023-10-01T08:19:57.640Z";
//        int totalCapitalAvailable = 10000;
//
//        String body = String.format(
//                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%d,\"updatedAt\":\"%s\"}",
//                email, id, name, picture, totalCapitalAvailable, updatedAt);
//
//        mockMvc.perform(put("/customer/updateemail").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    /**
//     * Should update customer capital.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    void shouldUpdateCustomerCapital() throws Exception {
//        String id = "testid";
//        String name = "new_name";
//        String email = "test@gmail.com";
//        String picture = "https://s.gravatar.com/avatar/f46f055e3618f5939cfc4a8e6572279c?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fxu.png";
//        String updatedAt = "2023-10-01T08:19:57.640Z";
//        int totalCapitalAvailable = 12000;
//
//        String body = String.format(
//                "{\"email\":\"%s\",\"id\":\"%s\",\"name\":\"%s\",\"picture\":\"%s\",\"totalCapitalAvailable\":%d,\"updatedAt\":\"%s\"}",
//                email, id, name, picture, totalCapitalAvailable, updatedAt);
//
//        mockMvc.perform(put("/customer/updatecapital").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    /**
//     * Should delete customer account.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    void shouldDeleteCustomerAccount() throws Exception {
//
//        String id = "testid";
//        mockMvc.perform(delete("/customer/deletecustomer/" + id).header("authorization", "Bearer " + token)).andDo(print()).andExpect(status().isOk());
//
//    }
//
//    /**
//     * Should get capital.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    void shouldGetCapital() throws Exception {
//        String id = "testid";
//        int expectedCapital = 12000;
//
//        mockMvc.perform(get("/customer/getcapital/" + id)
//                        .header("authorization", "Bearer " + token))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(String.valueOf(expectedCapital)));
//
//}
//
//}