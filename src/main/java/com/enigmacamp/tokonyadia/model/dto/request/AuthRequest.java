package com.enigmacamp.tokonyadia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest<T> {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Optional<T> data;
}
