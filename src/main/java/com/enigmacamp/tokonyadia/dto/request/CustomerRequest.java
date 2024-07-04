package com.enigmacamp.tokonyadia.dto.request;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String name;
    private String phoneNumber;
    private String address;
    private Date birthDate;
}
