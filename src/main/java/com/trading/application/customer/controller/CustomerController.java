package com.trading.application.customer.controller;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

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

    // update customer name
    @PostMapping
    @RequestMapping("/updatename")
    public String customerUpdateName(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.customerUpdateName(customer.getId(), customer.getName());
    }

}
