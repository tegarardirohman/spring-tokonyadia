package com.enigmacamp.tokonyadia.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
