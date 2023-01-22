package com.pp.adapters.events;

import com.pp.domain.model.CustomerEvent;
import com.pp.domain.ports.out.CustomerEventSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerEventSenderAdapter implements CustomerEventSender {

    public static final String Customer_EVENT = "CustomerEvent";

    private final StreamBridge streamBridge;
    @Override
    public void sendCustomerEvent(CustomerEvent CustomerEvent) {
        streamBridge.send(Customer_EVENT, CustomerEvent);
        log.info("Event " + CustomerEvent + "sent to channel : " + Customer_EVENT);
    }

}
