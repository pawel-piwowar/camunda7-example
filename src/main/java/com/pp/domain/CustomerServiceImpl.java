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
record CustomerServiceImpl(CustomerProcessService CustomerProcessService,
                           CustomerRepository CustomerRepository,
                           CustomerEventSender CustomerEventSender) implements CustomerService {

    @Override
    public List<Customer> getAllCustomers() {
        return CustomerRepository.findAll();
    }

    public Customer createCustomer(Customer Customer) {
        Customer CustomerFromDb = CustomerRepository.save(Customer);
        CustomerProcessService.startCustomerApprovalProcess(CustomerFromDb);
        log.info("New Customer candidate added: " + CustomerFromDb);
        CustomerEventSender.sendCustomerEvent(new CustomerEvent(CustomerFromDb, CustomerEventStatus.CREATED));
        return CustomerFromDb;
    }

    @Override
    public Customer updateCustomer(Customer Customer) {
        Customer CustomerForUpdate = CustomerRepository.findById(Customer.getId()).orElseThrow();
        Assert.notNull(CustomerForUpdate, "Customer not found");
        CustomerForUpdate.setName(Customer.getName());
        CustomerForUpdate.setAddress(Customer.getAddress());
        CustomerForUpdate.setNotes(Customer.getNotes());
        Customer CustomerFromDb = CustomerRepository.save(CustomerForUpdate);
        log.info("Customer updated: " + CustomerFromDb);
        return CustomerFromDb;
    }

    @Override
    public void approveCustomer(Long CustomerId){
        Customer Customer = CustomerRepository.findById(CustomerId).orElseThrow();
        Customer.setStatus(CustomerStatus.APPROVED);
        CustomerRepository.save(Customer);
        log.info("Customer saved: " + Customer);
        CustomerEventSender.sendCustomerEvent(new CustomerEvent(Customer, CustomerEventStatus.UPDATED));
    }

    @Override
    public void rejectCustomer(Long CustomerId, String notes){
        Customer Customer = CustomerRepository.findById(CustomerId).orElseThrow();
        Customer.setStatus(CustomerStatus.REJECTED);
        Customer.setNotes(notes);
        CustomerRepository.save(Customer);
        log.info("Customer saved: " + Customer);
    }

    @Override
    public void incompleteCustomer(Long CustomerId, String notes){
        Customer Customer = CustomerRepository.findById(CustomerId).orElseThrow();
        Customer.setStatus(CustomerStatus.INCOMPLETE);
        Customer.setNotes(notes);
        CustomerRepository.save(Customer);
        log.info("Customer saved: " + Customer);
    }

    @Override
    public void processSyncResponseEvent(SyncResponseEvent syncResponseEvent){
        Customer Customer = CustomerRepository.findById(Long.parseLong(syncResponseEvent.getCorrelationId())).orElseThrow();
        if (syncResponseEvent.getResultCode().equals(ResponseEventResultCode.SUCCESS)) {
            Customer.setSyncStatus(SyncStatus.SYNC_SUCCESS);
            Customer.setExternalId(syncResponseEvent.getExternalId());
        } else {
            Customer.setSyncStatus(SyncStatus.SYNC_ERROR);
        }
        CustomerRepository.save(Customer);
        log.info("Customer sync status updated: " + Customer);
    }

}
