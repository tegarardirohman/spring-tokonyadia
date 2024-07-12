package com.enigmacamp.tokonyadia.utils.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

}
