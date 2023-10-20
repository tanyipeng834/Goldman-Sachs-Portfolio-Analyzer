package com.trading.application.customer.service;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    private CustomerRepository customerRepo = new CustomerRepository();

    public String createCustomer(Customer customer) throws ExecutionException, InterruptedException {
        return customerRepo.addCustomer(customer);
    }

    public Customer getCustomer(String id) throws ExecutionException, InterruptedException {
        return customerRepo.getById(id);
    }

    public String updateCustomerName(String id, String name) throws ExecutionException, InterruptedException {
        return customerRepo.updateDocumentField(id, "name", name);
    }

    public String customerUpdateEmail(String id, String email) throws ExecutionException, InterruptedException {
        return customerRepo.updateDocumentField(id, "email", email);
    }

    public String customerUpdateCapital(String id, int totalCapitalAvailable) throws ExecutionException, InterruptedException {
        return customerRepo.updateTotalCapitalAvailable(id, "totalCapitalAvailable", totalCapitalAvailable);
    }

    public String deleteCustomerAccount(String id) {
        return customerRepo.deleteCustomerAccount(id);
    }

    public int getCustomerCapital(String id){

        try {
            return customerRepo.getTotalCapitalAvailable(id);
        } catch (ExecutionException e) {
            return -1;
        } catch (InterruptedException e) {
            return -2;
        }
    }

}
