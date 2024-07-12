package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.PageResponse;
import com.enigmacamp.tokonyadia.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.createCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.updateCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        // block jika terjadi exception pada line 35
        return ResponseEntity.ok("Success Delete Customer By Id");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortType", defaultValue = "ASC") String sortType,
            @RequestParam(name = "property", defaultValue = "name") String property,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "birthDate", required = false) Date birthDate
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortType), property);
        Pageable pageable = PageRequest.of(page, size, sort);

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name(name)
                .phoneNumber(phone)
                .address(address)
                .birthDate(birthDate)
                .build();

        Page<CustomerResponse> customerResponse = customerService.getCustomerPerPage(pageable, customerRequest);

        if (customerResponse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PageResponse<CustomerResponse> pageResponse = new PageResponse<>(customerResponse);

        return ResponseEntity.ok(pageResponse);
    }
}
