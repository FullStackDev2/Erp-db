package com.erp.sales.controller;

import com.erp.sales.dto.CustomerRequestDto;
import com.erp.sales.entity.Customer;
import com.erp.sales.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@Valid @RequestBody CustomerRequestDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return customerRepository.save(customer);
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new com.erp.common.exception.ResourceNotFoundException("Müşteri bulunamadı: " + id));
    }
}