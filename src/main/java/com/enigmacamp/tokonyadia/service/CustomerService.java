package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.dto.request.CustomerResponse;
import com.enigmacamp.tokonyadia.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse saveCustomer(CustomerRequest customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(String id);
    Customer putUpdateCustomer(Customer customer);
    Customer patchUpdateCustomer(Customer customer);
    boolean deleteCustomerById(String id);
}
