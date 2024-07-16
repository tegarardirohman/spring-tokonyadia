package com.enigmacamp.tokonyadia.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MidtransResponse {
    private String token;
    private String redirect_url;
}
