package com.trading.application.customer.service;
//import src.test.java.com.trading.application.config.FirebaseConfig.java;
import com.trading.application.config.FirebaseConfig;
import com.trading.application.customer.repository.CustomerRepository;
import com.trading.application.customer.repository.CustomerRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.trading.application.customer.entity.Customer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FirebaseConfig.class})
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryTest customerRepository;
    @InjectMocks
    private CustomerService customerService;




    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException {
//        FirebaseConfig.initializeFirebase();
        customerService = new CustomerService();
//        when(customerRepository.addCustomer(any())).thenReturn("Customer created successfully");
        when(customerRepository.addCustomer(any(Customer.class))).thenReturn("Customer created successfully");

    }

    @Test
    void createCustomer() throws ExecutionException, InterruptedException{

//        // Arrange
//        Customer customer = new Customer("John Doe", "john@example.com");
//        when(customerRepository.addCustomer(any())).thenReturn("Customer created successfully");
//
//        // Act
//        String result = customerService.createCustomer(customer);
//
//        // Assert
//        assertEquals("Customer created successfully", result);

//        without firebase
        Customer customer = new Customer("John Doe", "john@example.com");

        // Act
        String result = customerService.createCustomer(customer);

        // Assert
        assertEquals("Customer created successfully", result);
        verify(customerRepository).addCustomer(any());
    }

    @Test
    void getCustomer() {
//        try{
//            underTest.getCustomer("5cJ0NI3WpPLi9hCQKZG0");
//            verify(customerRepository).getById("5cJ0NI3WpPLi9hCQKZG0");
//        }catch(Exception e){
//            e.getStackTrace();
//        }

    }

    @Test
    void updateCustomerName() {
    }

    @Test
    void customerUpdateEmail() {
    }

    @Test
    void customerUpdateCapital() {
    }

    @Test
    void deleteCustomerAccount() {
    }

    @Test
    void getCustomerCapital() {
    }
}