package com.pp.domain;

import com.pp.domain.model.*;
import com.pp.domain.ports.out.CustomerRepository;
import com.pp.domain.ports.in.CustomerService;
import com.pp.domain.ports.out.CustomerEventSender;
import com.pp.domain.ports.out.CustomerProcessService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Log
@Service
record CustomerServiceImpl(CustomerProcessService customerProcessService,
                           CustomerRepository customerRepository,
                           CustomerEventSender customerEventSender) implements CustomerService {

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        Customer CustomerFromDb = customerRepository.save(customer);
        customerProcessService.startCustomerApprovalProcess(CustomerFromDb);
        log.info("New Customer candidate added: " + CustomerFromDb);
        customerEventSender.sendCustomerEvent(new CustomerEvent(CustomerFromDb, CustomerEventStatus.CREATED));
        return CustomerFromDb;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer CustomerForUpdate = customerRepository.findById(customer.getId()).orElseThrow();
        Assert.notNull(CustomerForUpdate, "Customer not found");
        CustomerForUpdate.setName(customer.getName());
        CustomerForUpdate.setAddress(customer.getAddress());
        CustomerForUpdate.setNotes(customer.getNotes());
        Customer CustomerFromDb = customerRepository.save(CustomerForUpdate);
        log.info("Customer updated: " + CustomerFromDb);
        return CustomerFromDb;
    }

    @Override
    public void approveCustomer(Long customerId){
        Customer Customer = customerRepository.findById(customerId).orElseThrow();
        Customer.setStatus(CustomerStatus.APPROVED);
        customerRepository.save(Customer);
        log.info("Customer saved: " + Customer);
        customerEventSender.sendCustomerEvent(new CustomerEvent(Customer, CustomerEventStatus.UPDATED));
    }

    @Override
    public void rejectCustomer(Long customerId, String notes){
        Customer Customer = customerRepository.findById(customerId).orElseThrow();
        Customer.setStatus(CustomerStatus.REJECTED);
        Customer.setNotes(notes);
        customerRepository.save(Customer);
        log.info("Customer saved: " + Customer);
    }

    @Override
    public void incompleteCustomer(Long customerId, String notes){
        Customer Customer = customerRepository.findById(customerId).orElseThrow();
        Customer.setStatus(CustomerStatus.INCOMPLETE);
        Customer.setNotes(notes);
        customerRepository.save(Customer);
        log.info("Customer saved: " + Customer);
    }

    @Override
    public void processSyncResponseEvent(SyncResponseEvent syncResponseEvent){
        Customer Customer = customerRepository.findById(Long.parseLong(syncResponseEvent.getCorrelationId())).orElseThrow();
        if (syncResponseEvent.getResultCode().equals(ResponseEventResultCode.SUCCESS)) {
            Customer.setSyncStatus(SyncStatus.SYNC_SUCCESS);
            Customer.setExternalId(syncResponseEvent.getExternalId());
        } else {
            Customer.setSyncStatus(SyncStatus.SYNC_ERROR);
        }
        customerRepository.save(Customer);
        log.info("Customer sync status updated: " + Customer);
    }

}
