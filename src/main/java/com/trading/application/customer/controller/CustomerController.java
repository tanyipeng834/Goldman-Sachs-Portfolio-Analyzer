package com.trading.application.controller;

import com.trading.application.beans.CustomerCreateResponse;
import com.trading.application.entity.Customer;
import com.trading.application.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    @RequestMapping("/")
    public CustomerCreateResponse createCustomer(@RequestBody Customer customer) throws ExecutionException, InterruptedException {
        return customerService.createCustomer(customer);
    }
}
