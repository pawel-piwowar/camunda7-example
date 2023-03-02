package com.pp.domain.ports.in;


import com.pp.domain.model.Customer;
import com.pp.domain.model.SyncResponseEvent;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void  approveCustomer(Long customerId);
    void  rejectCustomer(Long customerId, String notes);
    void  incompleteCustomer(Long customerId, String notes);

    void processSyncResponseEvent(SyncResponseEvent syncResponseEvent);

}
