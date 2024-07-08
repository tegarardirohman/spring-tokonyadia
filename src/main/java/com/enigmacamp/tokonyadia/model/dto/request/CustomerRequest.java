package com.enigmacamp.tokonyadia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // Setter Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String id;
//    @NotBlank(message = "Name is required")
//    @Size(min = 5)
    private String name;
    private String phoneNumber;
    private String address;
    private Date birthDate;
}