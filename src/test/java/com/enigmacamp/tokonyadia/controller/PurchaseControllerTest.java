package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.TransactionDetailRequest;
import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.MidtransResponse;
import com.enigmacamp.tokonyadia.model.dto.response.TransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void savePurchaseTest() throws Exception {

        // ID
        String customerId = "5bc4585d-e1c2-4387-a0a4-93e4b43d347c";
        String productId = "7f7bfd62-dca9-4385-8abd-194a16b0bf85";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJcIlRva29ueWFkaWEgQXBwIFNob3BcIiIsInN1YiI6IjRjNzY1MmI0LTA4MGItNGE3Zi1iYzRkLTllM2VmNDFkOTc4NyIsImV4cCI6MTcyMTEyNDEyNywiaWF0IjoxNzIxMTIwNTI3LCJyb2xlIjoiQ1VTVE9NRVIifQ.aY8vkoYEddJhLimAP7tkEv4W4OZziLP2S6fG1ZOkm3s";

        // Transaction detail request
        TransactionDetailRequest transactionDetailRequest = TransactionDetailRequest.builder()
                .productId(productId)
                .qty(1)
                .build();

        List<TransactionDetailRequest> details = new ArrayList<>();
        details.add(transactionDetailRequest);

        // transaction request
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .transactionDetails(details)
                .customerId(customerId)
                .build();

        // mock
        mockMvc.perform(
                post(APIUrl.TRANSACTION_API)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            TransactionResponse transactionResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TransactionResponse.class);

            assertEquals(HttpStatus.OK.value(), response.getStatus());

            System.out.println("Token: " + transactionResponse.getMidtransResponse().getToken());
            System.out.println("Redirect_url: " + transactionResponse.getMidtransResponse().getRedirect_url());
        });

    }
}