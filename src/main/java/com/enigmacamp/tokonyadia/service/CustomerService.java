package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(CustomerRequest request);
    void deleteCustomer(String id);
    CustomerResponse getCustomerById(String id);
    List<CustomerResponse> getAllCustomer();
    Customer getById(String id);

    Page<CustomerResponse> getCustomerPerPage(Pageable pageable, CustomerRequest customerRequest);
}
