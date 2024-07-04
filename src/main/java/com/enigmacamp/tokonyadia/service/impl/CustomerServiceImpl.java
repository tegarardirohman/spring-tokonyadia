package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.dto.request.CustomerResponse;
import com.enigmacamp.tokonyadia.entity.Customer;
import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse saveCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFullName(request.getName());
        customer.setPhone(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setBirthDate(request.getBirthDate());

        // save customer
        customer = customerRepository.saveAndFlush(customer);

        CustomerResponse response = new CustomerResponse();
        response.setName(customer.getFullName());
        response.setPhoneNumber(customer.getPhone());
        response.setAddress(customer.getAddress());

        return response;
    }

    @Override
    public List<Customer> getAllCustomers() {
        if (customerRepository.findAll().isEmpty()) {
            return null;
        } else {
            return customerRepository.findAll();
        }
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer putUpdateCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer patchUpdateCustomer(Customer customer) {
        return null;
    }

    @Override
    public boolean deleteCustomerById(String id) {
        try {
            customerRepository.deleteById(id);

            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
