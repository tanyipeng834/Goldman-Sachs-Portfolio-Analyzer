package com.trading.application.customer.controller;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customer")
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
    public Customer getCustomerById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return customerService.getCustomer(id);
    }

    // update customer First Name
    @PostMapping
    @RequestMapping("/updatefirstname")
    public String updateCustomerFirstName(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.updateCustomerFirstName(customer.getId(), customer.getFirstName());
    }

    // update customer Last Name
    @PostMapping
    @RequestMapping("/updatelastname")
    public String updateCustomerLastName(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.updateCustomerLastName(customer.getId(), customer.getLastName());
    }

    // update customer email
    @PostMapping
    @RequestMapping("/updateemail")
    public String customerUpdateEmail(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.customerUpdateEmail(customer.getId(), customer.getEmail());
    }

}
