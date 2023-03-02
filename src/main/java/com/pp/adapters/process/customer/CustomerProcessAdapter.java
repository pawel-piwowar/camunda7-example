package com.pp.adapters.process.customer;

import com.pp.domain.model.Customer;
import com.pp.domain.ports.out.CustomerProcessService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import static com.pp.adapters.process.customer.CustomerProcessConstants.CUSTOMER_ID_PROCESS_VARIABLE_NAME;
import static com.pp.adapters.process.customer.CustomerProcessConstants.CUSTOMER_NAME_PROCESS_VARIABLE_NAME;


@Service
public record CustomerProcessAdapter(RuntimeService runtimeService) implements CustomerProcessService {
    @Override
    public void startCustomerApprovalProcess(Customer customer) {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("CustomerApproval");
        runtimeService.setVariable(processInstance.getId(), CUSTOMER_ID_PROCESS_VARIABLE_NAME, customer.getId());
        runtimeService.setVariable(processInstance.getId(), CUSTOMER_NAME_PROCESS_VARIABLE_NAME, customer.getName());

    }
}
