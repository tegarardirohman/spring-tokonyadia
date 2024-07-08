package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(CustomerRequest request);
    void deleteCustomer(String id);
    CustomerResponse getCustomerById(String id);
    List<CustomerResponse> getAllCustomer();
    Customer getById(String id);
}
