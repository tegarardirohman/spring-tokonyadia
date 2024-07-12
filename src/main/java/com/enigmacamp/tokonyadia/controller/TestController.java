package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/customer")
    public ResponseEntity<CommonResponse<LoginResponse>> testCustomer() {

        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Customer")
                .data(null)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("/seller")
    public ResponseEntity<CommonResponse<LoginResponse>> testSeller() {

        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Seller")
                .data(null)
                .build();
        return ResponseEntity.ok(commonResponse);
    }


}
