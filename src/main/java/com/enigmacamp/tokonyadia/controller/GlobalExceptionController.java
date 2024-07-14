package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.utils.exceptions.ResourceNotFoundException;
import com.enigmacamp.tokonyadia.utils.exceptions.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<CommonResponse<String>> handleValidationException(ValidationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<CommonResponse<String>> responStatusException(ResponseStatusException exception) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(exception.getStatusCode().value())
                .message(exception.getReason())
                .data(Optional.empty())
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

}
