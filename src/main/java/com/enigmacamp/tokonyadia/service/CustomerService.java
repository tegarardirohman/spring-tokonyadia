package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.entity.Customer;
import com.enigmacamp.tokonyadia.entity.Product;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
    Customer putUpdateCustomer(Customer customer);
    Customer patchUpdateCustomer(Customer customer);
    boolean deleteCustomerById(int id);
}
