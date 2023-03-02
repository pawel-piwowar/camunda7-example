package com.pp.adapters.process.customer.delegates;

import com.pp.domain.ports.in.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import static com.pp.adapters.process.customer.CustomerProcessConstants.CUSTOMER_ID_PROCESS_VARIABLE_NAME;
import static com.pp.adapters.process.customer.CustomerProcessConstants.NOTES_PROCESS_VARIABLE_NAME;


@Slf4j
@Service
public record CustomerRejectDelegate(CustomerService customerService) implements JavaDelegate {
    public void execute(DelegateExecution delegate)
    {
        log.info("Reject Customer invoked");
        Long CustomerId = (Long) delegate.getVariable(CUSTOMER_ID_PROCESS_VARIABLE_NAME);
        String notes = (String) delegate.getVariable(NOTES_PROCESS_VARIABLE_NAME);
        customerService.rejectCustomer(CustomerId, notes);
    }
}
