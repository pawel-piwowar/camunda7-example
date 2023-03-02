package com.pp.domain.ports.out;

import com.pp.domain.model.CustomerEvent;

public interface CustomerEventSender {
    void sendCustomerEvent(CustomerEvent customerEvent);
}
