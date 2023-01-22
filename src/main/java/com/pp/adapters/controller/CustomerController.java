package com.pp.adapters.controller;

import com.pp.adapters.controller.dto.CustomerDto;
import com.pp.domain.model.Customer;
import com.pp.domain.ports.in.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("customers")
record CustomerController(CustomerService CustomerService) {

    @PostMapping
    ResponseEntity<Customer> createCustomerCandidate(@RequestBody final CustomerDto CustomerDto) {
        Customer Customer = CustomerService.createCustomer(CustomerDto.toCustomer());
        return new ResponseEntity<>(Customer, HttpStatus.CREATED);
    }

    @GetMapping
    List<Customer> getAllCustomers(Authentication authentication) {
        log.info("User logged: " + authentication.getName());
        authentication.getAuthorities().forEach(auth -> log.info("User Group: " + auth.getAuthority()));

        return CustomerService.getAllCustomers();
    }

}
