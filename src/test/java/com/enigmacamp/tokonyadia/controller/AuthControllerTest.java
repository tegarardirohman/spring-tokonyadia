package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.request.AuthRequest;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AllArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() throws Exception {
//        userRepository.deleteAll();
    }

    @Test
    void registerCustumerSuccess() throws Exception {

        // Date
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("15-10-2000");

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("Tegar Ardi Rohman")
                .phoneNumber("0821313444")
                .address("Trenggalek")
                .birthDate(date)
                .build();

        AuthRequest<Object> authRequest = AuthRequest.builder()
                .username("usernameBaru")
                .password("Admin#1234")
                .data(Optional.ofNullable(customerRequest)).build();

        mockMvc.perform(
                post("/api/v1/auth/register/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))

        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
            assertNotNull(response.getData());
        });

    }

    @Test
    void registerCustumerFailed() throws Exception {

        // Date
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("15-10-2000");

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("")
                .phoneNumber("0821313444")
                .address("Trenggalek")
                .birthDate(date)
                .build();

        AuthRequest<Object> authRequest = AuthRequest.builder()
                .username("newbaruheha")
                .password("Admin#1234")
                .data(Optional.ofNullable(customerRequest)).build();

        mockMvc.perform(
                post("/api/v1/auth/register/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))

        ).andExpectAll(
                status().is4xxClientError()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
            assertNotNull(response.getData());
        });

    }

    @Test
    void loginSuccess() throws Exception {
        AuthRequest<String> authRequest = AuthRequest.<String>builder()
                .username("seller")
                .password("seller")
                .data(Optional.empty())
                .build();

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            System.out.println(response.toString());
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        });
    }

    @Test
    void loginFailed() throws Exception {
        AuthRequest<String> authRequest = AuthRequest.<String>builder()
                .username("seller")
                .password("sellerTapiSalah")
                .data(Optional.empty())
                .build();

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))
        ).andExpectAll(
                status().is4xxClientError()
        ).andDo(result -> {
            CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonResponse.class);

            assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
        });
    }

    @Test
    void registerSeller() {
    }
}