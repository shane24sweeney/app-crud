package com.bank.service;

import com.bank.model.Customer;
import com.bank.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService  {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer createCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public void deleteCustomer(Long id) {
       if (!customerRepository.existsById(id)) {
           throw new RuntimeException("Customer with id " + id + " does not exist");
       }
           customerRepository.deleteById(id);

    }


    public Customer updateCustomer(Long id,Customer customer) {
        return customerRepository.findById(id).
                map(existing -> {
                    existing.setFirstName(customer.getFirstName());
                    existing.setLastName(customer.getLastName());
                    existing.setEmail(customer.getEmail());
                    existing.setPhoneNumber(customer.getPhoneNumber());
                    return customerRepository.save(existing);
                })
                .orElseThrow(()-> new RuntimeException("Customer not found with id " + id));
    }


    public Optional<Customer> getCustomerIds(Long id) {
        return customerRepository.findById(id);
    }
}
