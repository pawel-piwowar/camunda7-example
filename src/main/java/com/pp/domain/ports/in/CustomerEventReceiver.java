package com.pp.domain.ports.in;

import com.pp.domain.model.CustomerEventStatus;
import com.pp.domain.model.Customer;

public interface CustomerEventReceiver {
    void receiveCustomerEvent(Customer customer, CustomerEventStatus customerEventStatus);
}
