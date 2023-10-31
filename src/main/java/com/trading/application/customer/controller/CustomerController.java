package com.trading.application.customer.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.trading.application.CustomError;
import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.service.CustomerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerController {

    /**
     * The Customer service.
     */
    @Autowired
    private CustomerService customerService;

    /**
     * Create customer response entity.
     *
     * @param customer the customer
     * @return the response entity
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws UnirestException     the unirest exception
     */
    @PostMapping
    @RequestMapping("/")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException, UnirestException {

        String accessToken = generateJWT();
        Map<String, Object> customerResponseBody = new HashMap<>();
        customerResponseBody.put("customerData", customerService.createCustomer(customer));
        customerResponseBody.put("token", accessToken);

        return new ResponseEntity<>(customerResponseBody, HttpStatus.OK);

    }

    /**
     * Gets customer by id.
     *
     * @param id the id
     * @return the customer by id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String id) throws ExecutionException, InterruptedException {

        try {
            if (customerService.getCustomer(id) == null) {
                return new ResponseEntity<>(new CustomError(404, "Customer not found"), HttpStatus.NOT_FOUND);
            }



            String accessToken = generateJWT();

            Map<String, Object> customerResponseBody = new HashMap<>();
            customerResponseBody.put("customerData", customerService.getCustomer(id));
            customerResponseBody.put("token", accessToken);

            return new ResponseEntity<>(customerResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomError(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update customer name string.
     *
     * @param customer the customer
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @PutMapping
    @RequestMapping("/updatename")
    public String updateCustomerName(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.updateCustomerName(customer.getId(), customer.getName());
    }

    /**
     * Customer update email string.
     *
     * @param customer the customer
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @PutMapping
    @RequestMapping("/updateemail")
    public String customerUpdateEmail(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.customerUpdateEmail(customer.getId(), customer.getEmail());
    }

    /**
     * Customer update capital string.
     *
     * @param customer the customer
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @PutMapping
    @RequestMapping(value = "/updatecapital", method = {RequestMethod.OPTIONS, RequestMethod.PUT})
    public String customerUpdateCapital(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.customerUpdateCapital(customer.getId(), customer.getTotalCapitalAvailable());
    }

    /**
     * Delete customer account string.
     *
     * @param id the id
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @DeleteMapping
    @RequestMapping("/deletecustomer/{id}")
    public String deleteCustomerAccount(@PathVariable String id) throws ExecutionException, InterruptedException {
        return customerService.deleteCustomerAccount(id);
    }

    /**
     * Gets capital.
     *
     * @param userId the user id
     * @return the capital
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping
    @RequestMapping("/getcapital/{userId}")
    public int getCapital(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return customerService.getCustomerCapital(userId);
    }

    /**
     * Generate jwt string for authoizatin purposes.
     *
     * @return the string
     * @throws UnirestException the unirest exception
     */
    public String generateJWT() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://dev-hzksj8468hgj4q5f.us.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"nQvMJc2AIk4aqkjGQkBtqKIRZYTg4pWD\",\"client_secret\":\"LcTs2xe1i626SADKdF2wqNsvPu_uxPOzWkiLFj0Mrns_wmilpLuOeBIll1QJjF8K\",\"audience\":\"https://goldman.com\",\"grant_type\":\"client_credentials\"}")
                .asString();
        String responseBody = response.getBody();
        JSONObject json = new JSONObject(responseBody);
        return json.getString("access_token");
    }

}
