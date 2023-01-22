package com.pp.domain.ports.in;


import com.pp.domain.model.Customer;
import com.pp.domain.model.SyncResponseEvent;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer createCustomer(Customer Customer);
    Customer updateCustomer(Customer Customer);
    void  approveCustomer(Long CustomerId);
    void  rejectCustomer(Long CustomerId, String notes);
    void  incompleteCustomer(Long CustomerId, String notes);

    void processSyncResponseEvent(SyncResponseEvent syncResponseEvent);

}
