package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.entity.Customer;
import com.enigmacamp.tokonyadia.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    List<Customer> customers = new ArrayList<>();


    @Override
    public Customer saveCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer getCustomerById(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Customer putUpdateCustomer(Customer customer) {
        Customer existingCustomer = customers.stream().filter(c -> c.getId() == customer.getId()).findFirst().orElse(null);

        if (existingCustomer != null) {
            existingCustomer.setFullName(customer.getFullName());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setBirthDate(customer.getBirthDate());

            return existingCustomer;
        } else {
            return null;
        }

    }

    @Override
    public Customer patchUpdateCustomer(Customer customer) {
        Customer existingCustomer = customers.stream().filter(c -> c.getId() == customer.getId()).findFirst().orElse(null);

        if (existingCustomer != null) {
            if (customer.getFullName() != null) {
                existingCustomer.setFullName(customer.getFullName());
            }
            if (customer.getAddress() != null) {
                existingCustomer.setAddress(customer.getAddress());
            }
            if (customer.getPhone() != null) {
                existingCustomer.setPhone(customer.getPhone());
            }
            if (customer.getBirthDate() != null) {
                existingCustomer.setBirthDate(customer.getBirthDate());
            }

            // Save the updated customer
            return existingCustomer;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCustomerById(int id) {
        if(customers.removeIf(customer -> customer.getId() == id)) {
            return true;
        }
        return false;
    }
}
