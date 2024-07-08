package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.utils.exceptions.ResourceNotFoundException;
import com.enigmacamp.tokonyadia.utils.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;


@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundExeption(ResourceNotFoundException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //TODO: Add more exeption handler
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<CommonResponse<String>> handleValidationExeption(ValidationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }
}
