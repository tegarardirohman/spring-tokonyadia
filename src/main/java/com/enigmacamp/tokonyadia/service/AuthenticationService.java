package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.SellerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse registerCustomer(AuthRequest<CustomerRequest> authRequest);
    LoginResponse login(AuthRequest<String> loginRequest);

    // seller
    RegisterResponse registerSeller(AuthRequest<SellerRequest> authRequest);

    // admin
//    RegisterResponse registerAdmin(AuthRequest<CustomerRequest> authRequest);


}
