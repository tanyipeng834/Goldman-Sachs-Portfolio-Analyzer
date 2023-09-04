package com.trading.application.customer.service;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    CustomerRepository customerRepo = new CustomerRepository();

    // create new customer
    public String createCustomer(Customer customer) throws ExecutionException, InterruptedException {

        return customerRepo.addCustomer(customer);

    }

    // get customer by id
    public Customer getCustomer(String id) throws  ExecutionException, InterruptedException {

        return customerRepo.getById(id, "customer");

    }

    // update customer name
    public String customerUpdateName(String id, String name) throws ExecutionException, InterruptedException {

        return customerRepo.updateDocumentField(id, "name", name);
    }

    // customer update email
    public String customerUpdateEmail(String id, String email) throws ExecutionException, InterruptedException {

        return customerRepo.updateDocumentField(id, "email", email);
    }

}
