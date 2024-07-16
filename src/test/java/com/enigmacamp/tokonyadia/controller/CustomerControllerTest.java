package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.entity.AppUser;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.Role;
import com.enigmacamp.tokonyadia.model.entity.User;
import com.enigmacamp.tokonyadia.repository.CustomerRepository;
import com.enigmacamp.tokonyadia.repository.UserRepository;
import com.enigmacamp.tokonyadia.security.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Test
    void updateCustomerSuccess() throws Exception {
        // generate Token
        Optional<User> user = userRepository.findByUsername("seller");

        AppUser appUser = AppUser.builder()
                .id(user.get().getId())
                .password(user.get().getPassword())
                .role(Role.ERole.SELLER)
                .build();

        String token = jwtUtil.generateToken(appUser);

        Customer customer = Customer.builder()
                .name("john")
                .address("2000-10-15")
                .address("Jakarta")
                .phoneNumber("0822222222")
                .build();

        // Mock repository behavior
        String sDate1="15/10/2000";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

        Customer cust = customerRepository.saveAndFlush(customer);

        Customer updatedCustomer = Customer.builder()
                .id(cust.getId())
                .name("john Chena")
                .address("2000-10-15")
                .birthDate(date)
                .phoneNumber("0822222222")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            CommonResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            Customer update = customerRepository.findById(cust.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            assertEquals(update.getId(), updatedCustomer.getId());
            assertEquals(update.getName(), updatedCustomer.getName());
            assertEquals(update.getAddress(), updatedCustomer.getAddress());
            assertEquals(update.getPhoneNumber(), updatedCustomer.getPhoneNumber());
        });

    }

    @Test
    void updateCustomerFailedInvalidToken() throws Exception {
        // Generate invalid Token (for example, by modifying a valid token)
        String invalidToken = "invalid_token_here";

        Customer customer = Customer.builder()
                .name("john")
                .address("Jakarta")
                .phoneNumber("0822222222")
                .build();

        // Save the initial customer data
        Customer savedCustomer = customerRepository.saveAndFlush(customer);

        // Setup data request with updated information
        String sDate1 = "2000-10-15";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);

        Customer updatedCustomer = Customer.builder()
                .id(savedCustomer.getId())
                .name("john Chena")  // Updated name
                .address("Jakarta")  // Updated address
                .birthDate(date)  // Updated birthDate
                .phoneNumber("0822222222")  // Updated phoneNumber
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken)  // Token tidak valid
        ).andExpect(
                status().isUnauthorized()
        ).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();

            System.out.println(response.getContentAsString());
            assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        });

    }


    @Test
    void deleteCustomerSuccess() throws Exception {

    }

    @Test
    void getCustomerSucess() throws Exception {

    }

    @Test
    void getAllCustomer() {
    }
}