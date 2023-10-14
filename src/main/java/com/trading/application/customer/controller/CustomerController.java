package com.trading.application.customer.controller;

import com.fasterxml.jackson.databind.JsonNode;
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

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // create new customer
    @PostMapping
    @RequestMapping("/")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException,UnirestException {
        String accessToken ="";
//        String accessToken = generateJWT();
        Map<String, Object> customerResponseBody = new HashMap<>();
        customerResponseBody.put("customerData", customerService.createCustomer(customer));
        customerResponseBody.put("token", accessToken);

        return new ResponseEntity<>(customerResponseBody, HttpStatus.OK);



    }

    // get customer by id
    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String id) throws ExecutionException, InterruptedException {


        try {
            if (customerService.getCustomer(id) == null) {
                return new ResponseEntity<>(new CustomError(404, "Customer not found"), HttpStatus.NOT_FOUND);
            }

            String accessToken ="";

//            String accessToken = generateJWT();


// Extract the access_token from the JSON response


            // Create a response object containing the customer data and the token
            Map<String, Object> customerResponseBody = new HashMap<>();
            customerResponseBody.put("customerData", customerService.getCustomer(id));
            customerResponseBody.put("token", accessToken);

            return new ResponseEntity<>(customerResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomError(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update customer name
    @PostMapping
    @RequestMapping("/updatename")
    public String updateCustomerName(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.updateCustomerName(customer.getId(), customer.getName());
    }

    // update customer email
    @PutMapping
    @RequestMapping("/updateemail")
    public String customerUpdateEmail(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.customerUpdateEmail(customer.getId(), customer.getEmail());
    }

    @RequestMapping(value = "/updatecapital", method = {RequestMethod.OPTIONS, RequestMethod.PUT})
    public String customerUpdateCapital(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.customerUpdateCapital(customer.getId(), customer.getTotalCapitalAvailable());
    }

    @DeleteMapping
    @RequestMapping("/deletecustomer/{id}")
    public String deleteCustomerAccount(@PathVariable String id) throws ExecutionException, InterruptedException {
        return customerService.deleteCustomerAccount(id);
    }

    @GetMapping
    @RequestMapping("/getcapital/{userId}")
    public float getCapital(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return customerService.getCustomerCapital(userId);
    }


    public String generateJWT() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://dev-4pxn4zbtcuoww57l.us.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"5BLESP05RJ9IJ39tX5GCKYMjCpaGfcBZ\",\"client_secret\":\"0rGysyH68zlJjtbKm38IoZ6ZDhL8bJ-Ydpps8Uo4dKCHFieXavk5tnST_jJek1mj\",\"audience\":\"https://goldman.com\",\"grant_type\":\"client_credentials\"}")
                .asString();
        String responseBody = response.getBody();
        JSONObject json = new JSONObject(responseBody);
        String accessToken = json.getString("access_token");
        return accessToken;

    }

}
