package com.enigmacamp.tokonyadia.model.dto.request;

import com.enigmacamp.tokonyadia.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // Setter Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    private String id;
    @NotBlank(message = "Name is required")
    @Size(min = 1)
    private String name;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Address ir required")
    private String address;
    @Past
    @NotNull
    private Date birthDate;
    private User user;
}