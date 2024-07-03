package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.entity.Customer;
import com.enigmacamp.tokonyadia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }


    // UPDATE PUT and PATCH
    @PutMapping("/update")
    public Customer putProduct(@RequestBody Customer customer) {
        return customerService.putUpdateCustomer(customer);
    }

    @PatchMapping("/update")
    public Customer patchProduct(@RequestBody Customer customer) {
        return customerService.patchUpdateCustomer(customer);
    }

    // Find by id
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }


    // Delete by ID
    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable int id) {
        return customerService.deleteCustomerById(id);
    }
}
