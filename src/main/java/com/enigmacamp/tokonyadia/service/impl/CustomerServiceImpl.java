package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        // Create entitas baru daru customerRequest
        Customer customer =  new Customer();
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setBirthDate(request.getBirthDate());

        // Save customer
        customer = customerRepository.saveAndFlush(customer);
        return convertToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest request) {
        findByidOrThrowNotFound(request.getId());
        Customer customer = customerRepository.saveAndFlush(
                Customer.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .phoneNumber(request.getPhoneNumber())
                        .birthDate(request.getBirthDate())
                        .address(request.getAddress()).build()
        );
        return convertToCustomerResponse(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = findByidOrThrowNotFound(id);
        customerRepository.delete(customer);
    }


    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = findByidOrThrowNotFound(id);
        return convertToCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAll().stream().map(this::convertToCustomerResponse).toList();
    }

    @Override
    public Customer getById(String id) {
        return findByidOrThrowNotFound(id);
    }

    private Customer findByidOrThrowNotFound(String id){
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not found"));
    }

    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .birthDate(customer.getBirthDate())
                .address(customer.getAddress())
                .build();
    }
}

// semua Response mengguna dto Response
// menambah new ResponseStatusException() -> return Response HTTP bawaan spring, namun jika ingin custom, perlu handling menggunakan global error (RestControllerAdvice)
// menambahkan method untuk convert ke dto response, sehingga bisa digunakan oleh method lain

