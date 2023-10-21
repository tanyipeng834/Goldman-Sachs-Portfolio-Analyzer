package com.trading.application.customer.service;

import com.trading.application.customer.entity.Customer;
import com.trading.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * The type Customer service.
 */
@Service
public class CustomerService {

    /**
     * The Customer repo.
     */
    private CustomerRepository customerRepo = new CustomerRepository();

    /**
     * Create customer string.
     *
     * @param customer the customer
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String createCustomer(Customer customer) throws ExecutionException, InterruptedException {
        return customerRepo.addCustomer(customer);
    }

    /**
     * Gets customer.
     *
     * @param id the id
     * @return the customer
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Customer getCustomer(String id) throws ExecutionException, InterruptedException {
        return customerRepo.getById(id);
    }

    /**
     * Update customer name string.
     *
     * @param id   the id
     * @param name the name
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String updateCustomerName(String id, String name) throws ExecutionException, InterruptedException {
        return customerRepo.updateDocumentField(id, "name", name);
    }

    /**
     * Customer update email string.
     *
     * @param id    the id
     * @param email the email
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String customerUpdateEmail(String id, String email) throws ExecutionException, InterruptedException {
        return customerRepo.updateDocumentField(id, "email", email);
    }

    /**
     * Customer update capital string.
     *
     * @param id                    the id
     * @param totalCapitalAvailable the total capital available
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String customerUpdateCapital(String id, int totalCapitalAvailable) throws ExecutionException, InterruptedException {
        return customerRepo.updateTotalCapitalAvailable(id, "totalCapitalAvailable", totalCapitalAvailable);
    }

    /**
     * Delete customer account string.
     *
     * @param id the id
     * @return the string
     */
    public String deleteCustomerAccount(String id) {
        return customerRepo.deleteCustomerAccount(id);
    }

    /**
     * Get customer capital int.
     *
     * @param id the id
     * @return the int
     */
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
