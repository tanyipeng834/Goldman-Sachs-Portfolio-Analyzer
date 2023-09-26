package com.trading.application.customer.controller;

import com.trading.application.CustomError;
import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // create new customer
    @PostMapping
    @RequestMapping("/")
    public String createCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.createCustomer(customer);
    }

    // get customer by id
    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String id) throws ExecutionException, InterruptedException {
//        return customerService.getCustomer(id);

        try {
            if (customerService.getCustomer(id) == null) {
                return new ResponseEntity<>(new CustomError(404, "Customer not found"), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
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

    @DeleteMapping
    @RequestMapping("/deletecustomer/{id}")
    public String deleteCustomerAccount(@PathVariable String id) throws ExecutionException, InterruptedException {
        return customerService.deleteCustomerAccount(id);
    }

}
