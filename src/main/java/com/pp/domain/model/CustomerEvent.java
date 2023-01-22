package com.pp.domain.model;

import lombok.Data;

@Data
public class CustomerEvent {

    public CustomerEvent(Customer Customer, CustomerEventStatus CustomerEventStatus){
        this.correlationId = Customer.getId();
        this.CustomerEventStatus = CustomerEventStatus;
        this.CustomerStatus = Customer.getStatus();
        this.name = Customer.getName();
        this.address = Customer.getAddress();
        this.notes = Customer.getNotes();
    }

    private Long correlationId;
    private CustomerEventStatus CustomerEventStatus;
    private CustomerStatus CustomerStatus;
    private String name;
    private String address;
    private String notes;
}
