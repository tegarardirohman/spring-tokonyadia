package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.dto.request.CustomerResponse;
import com.enigmacamp.tokonyadia.entity.Customer;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customer) {
        CustomerResponse createdCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
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
    public Customer getCustomer(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }


    // Delete by ID
    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomerById(id);
    }
}
