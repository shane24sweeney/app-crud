package com.bank.controller;

import com.bank.model.Customer;
import com.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    // @GetMapping("/api/public/categories")
    @GetMapping
    private ResponseEntity<List<Customer>> getAllCustomers()
    {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity<Customer> getCustomerIds(@PathVariable (name="id")  Long id)
    {
        return customerService.getCustomerIds(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer)
    {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    //@PutMapping("/public/categories/{categoryId}")
    @PutMapping (value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable (name="id") Long id,@RequestBody Customer customer)
    {
        try {

            return ResponseEntity.ok(customerService.updateCustomer(id,customer));
        }
        catch (RuntimeException e)
        {return ResponseEntity.notFound().build();}
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable (name="id")  Long id)
    {
        try {

            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Customer deleted"+id);
        }
        catch (RuntimeException e)
        {return ResponseEntity.notFound().build();}
    }



}

