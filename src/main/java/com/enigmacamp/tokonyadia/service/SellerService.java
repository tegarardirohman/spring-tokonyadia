package com.enigmacamp.tokonyadia.service;


import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.SellerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.SellerResponse;

public interface SellerService {
    SellerResponse createSeller(SellerRequest request);
}
