package com.enigmacamp.tokonyadia.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.request.SellerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import com.enigmacamp.tokonyadia.model.dto.response.RegisterResponse;
import com.enigmacamp.tokonyadia.model.entity.AppUser;
import com.enigmacamp.tokonyadia.model.entity.Role;
import com.enigmacamp.tokonyadia.model.entity.User;
import com.enigmacamp.tokonyadia.repository.UserRepository;
import com.enigmacamp.tokonyadia.security.JwtUtil;
import com.enigmacamp.tokonyadia.service.*;
import com.enigmacamp.tokonyadia.utils.exceptions.ResourceNotFoundException;
import com.enigmacamp.tokonyadia.utils.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthenticationService {
    private final RoleService roleService;
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Validator validator;

    @Override
    @Transactional
    public RegisterResponse registerCustomer(AuthRequest<CustomerRequest> request) {
        Set<ConstraintViolation<AuthRequest<CustomerRequest>>> constraintViolations = validator.validate(request);
        Set<ConstraintViolation<CustomerRequest>> dataConstraint = validator.validate(request.getData().get());

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        if (!dataConstraint.isEmpty()) {
            throw new ConstraintViolationException(dataConstraint);
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already in use");
        }


        Role role = roleService.getOrSave(Role.ERole.CUSTOMER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder().username(request.getUsername().toLowerCase()).password(passwordEncoder.encode(request.getPassword())).roles(roles).build();

        user = userRepository.saveAndFlush(user);

        CustomerRequest requestData = request.getData().orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        requestData.setUser(user);

        customerService.createCustomer(requestData);

        return RegisterResponse.builder().username(user.getUsername()).role(role.getName()).build();
    }

    @Override
    public LoginResponse login(AuthRequest<String> request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // mengambil data user yang telah terautentikasi
        AppUser appUser = (AppUser) authentication.getPrincipal();

        // Create algorithm
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder().token(token).role(appUser.getRole()).build();
    }

    @Override
    public RegisterResponse registerSeller(AuthRequest<SellerRequest> request) {
        Role role = roleService.getOrSave(Role.ERole.SELLER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder().username(request.getUsername().toLowerCase()).password(passwordEncoder.encode(request.getPassword())).roles(roles).build();
        user = userRepository.saveAndFlush(user);
        SellerRequest requestData = request.getData().orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
        requestData.setUser(user);

        sellerService.createSeller(requestData);

        return RegisterResponse.builder().username(user.getUsername()).role(role.getName()).build();
    }
}
