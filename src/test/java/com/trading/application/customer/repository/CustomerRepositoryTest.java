package com.trading.application.customer.repository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;  // Added this import

import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;
import com.trading.application.customer.entity.Customer;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {

    @Mock
    private CollectionReference collectionReference;
    @Mock
    private DocumentReference docReference; // Mock DocumentReference
    @Mock
    private DocumentSnapshot document;      // Mock DocumentSnapshot

    @InjectMocks
    private CustomerRepository customerRepository;

    @Mock
    private FirebaseApp firebaseApp;

    @Mock
    private Firestore firestore;
    @Mock
    private ApiFuture<DocumentSnapshot> apiFuture;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @BeforeEach
    void setup() {

        when(docReference.get()).thenReturn(apiFuture); // Mock the behavior of Firestore methods
        try {
            when(apiFuture.get()).thenReturn(documentSnapshot);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetById() throws ExecutionException, InterruptedException {
        // Arrange
        String documentId = "your_document_id";
        when(document.exists()).thenReturn(true);

        // Create a sample customer to simulate the expected result
        Customer expectedCustomer = new Customer();
        // Set properties on the expectedCustomer as needed

        when(document.toObject(Customer.class)).thenReturn(expectedCustomer);

        // Act
        Customer result = customerRepository.getById(documentId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomer, result);
    }
}
