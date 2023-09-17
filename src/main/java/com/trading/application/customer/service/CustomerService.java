package com.trading.application.customer.service;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    private CustomerRepository customerRepo = new CustomerRepository();

    // create new customer
    public String createCustomer(Customer customer) throws ExecutionException, InterruptedException {

        return customerRepo.addCustomer(customer);

    }

    // get customer by id
    public Customer getCustomer(String id) throws  ExecutionException, InterruptedException {

        return customerRepo.getById(id);

    }

    // update customer First Name
    public String updateCustomerFirstName(String id, String firstName) throws ExecutionException, InterruptedException {

        return customerRepo.updateDocumentField(id, "firstName", firstName);
    }

    // update customer Last Name
    public String updateCustomerLastName(String id, String lastName) throws ExecutionException, InterruptedException {

        return customerRepo.updateDocumentField(id, "lastName", lastName);
    }

    // customer update email
    public String customerUpdateEmail(String id, String email) throws ExecutionException, InterruptedException {

        return customerRepo.updateDocumentField(id, "email", email);
    }

}
