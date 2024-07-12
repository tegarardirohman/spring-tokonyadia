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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthenticationService {
    private final RoleService roleService;
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterResponse registerCustomer(AuthRequest<CustomerRequest> request) {
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
