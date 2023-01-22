package com.pp.domain.ports.out;


import com.pp.domain.model.Customer;

public interface CustomerProcessService {
    void startCustomerApprovalProcess(Customer Customer);
}
