package com.trading.application.customer.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.customer.entity.Customer;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;

/**
 * The type Customer repository.
 */
@Repository
public class CustomerRepository {

    /**
     * The Firestore.
     */
    private Firestore firestore = FirestoreClient.getFirestore();
    /**
     * The Document snapshot api future.
     */
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    /**
     * The Write result api future.
     */
    private ApiFuture<WriteResult> writeResultApiFuture;

    /**
     * Get reference by id document reference.
     *
     * @param documentId the document id
     * @return the document reference
     */
    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("customer").document(documentId);
    }

    /**
     * Add customer string.
     *
     * @param customer the customer
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String addCustomer(Customer customer) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("customer").document(customer.getId());
        customer.setTotalCapitalAvailable(10000);
        writeResultApiFuture = docReference.set(customer);
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

    }

    /**
     * Gets by id.
     *
     * @param documentId the document id
     * @return the by id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public Customer getById(String documentId) throws ExecutionException, InterruptedException {

        DocumentReference docReference = getReferenceById(documentId);
        documentSnapshotApiFuture = docReference.get();
        DocumentSnapshot document = documentSnapshotApiFuture.get();

        Customer customer = null;

        if(document.exists()){
            customer = document.toObject(Customer.class);
            return customer;
        }
        else
        {
            return null;
        }

    }

    /**
     * Update document field string.
     *
     * @param documentId the document id
     * @param field      the field
     * @param fieldValue the field value
     * @return the string
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updateDocumentField(String documentId, String field, String fieldValue) throws InterruptedException, ExecutionException{

        DocumentReference docReference = getReferenceById(documentId);
        writeResultApiFuture = docReference.update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();

    }

    /**
     * Update total capital available string.
     *
     * @param documentId the document id
     * @param field      the field
     * @param fieldValue the field value
     * @return the string
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String updateTotalCapitalAvailable(String documentId, String field, int fieldValue) throws InterruptedException, ExecutionException {

        DocumentReference docReference = getReferenceById(documentId);
        writeResultApiFuture = docReference.update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();

    }


    /**
     * Delete customer account string.
     *
     * @param documentId the document id
     * @return the string
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public String deleteCustomerAccount(String documentId){

        try {
            writeResultApiFuture = getReferenceById(documentId).delete();
            return "Successfully deleted " + documentId;
        } catch (FirestoreException e) {
            return "Error deleting " + documentId;
        }

    }

    /**
     * Gets total capital available.
     *
     * @param documentId the document id
     * @return the total capital available
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Retryable(retryFor = {ExecutionException.class, InterruptedException.class}, maxAttempts = 2, backoff =
    @Backoff(delay = 100))
    public int getTotalCapitalAvailable(String documentId) throws InterruptedException, ExecutionException {

        DocumentReference docRef = getReferenceById(documentId);
        try {
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                Long totalCapital = document.getLong("totalCapitalAvailable");

                if (totalCapital != null) {
                    return totalCapital.intValue();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
            } catch (InterruptedException e) {
                throw new InterruptedException("Failed to retrieve customer capital: " + e.getMessage());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
        }
    }

}


