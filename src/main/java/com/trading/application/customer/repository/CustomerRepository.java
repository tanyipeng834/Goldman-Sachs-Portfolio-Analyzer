package com.trading.application.customer.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.trading.application.customer.entity.Customer;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;

@Repository
public class CustomerRepository {

    private Firestore firestore = FirestoreClient.getFirestore();
    private ApiFuture<DocumentSnapshot> documentSnapshotApiFuture;
    private ApiFuture<WriteResult> writeResultApiFuture;

    public DocumentReference getReferenceById(String documentId){

        return firestore.collection("customer").document(documentId);

    }

    // add new customer
    public String addCustomer(Customer customer) throws ExecutionException, InterruptedException {

        DocumentReference docReference = firestore.collection("customer").document();
        customer.setId(docReference.getId());
        writeResultApiFuture = docReference.set(customer);
        return writeResultApiFuture.get().getUpdateTime().toDate().toString();

    }

    // Get document by documentId
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

    // Update a document's field
    public String updateDocumentField(String documentId, String field, String fieldValue) throws InterruptedException, ExecutionException{

        DocumentReference docReference = getReferenceById(documentId);
        writeResultApiFuture = docReference.update(field, fieldValue);
        return "Result: " + writeResultApiFuture.get();
    }

    public String deleteCustomerAccount(String documentId){
        try {
            writeResultApiFuture = getReferenceById(documentId).delete();
            return "Successfully deleted " + documentId;
        } catch (FirestoreException e) {
            e.printStackTrace();
            return "Error deleting " + documentId;
        }
    }

}
