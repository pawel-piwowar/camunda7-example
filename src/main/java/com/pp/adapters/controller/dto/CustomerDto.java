package com.pp.adapters.controller.dto;

import com.pp.domain.model.Customer;
import com.pp.domain.model.CustomerStatus;
import lombok.Data;

@Data
public class CustomerDto {
    private String name;
    private String address;

    public Customer toCustomer(){
        return Customer.builder()
                .name(this.name)
                .address(this.address)
                .status(CustomerStatus.CANDIDATE)
                .build();
    }
}
