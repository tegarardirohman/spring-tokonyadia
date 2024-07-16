package com.enigmacamp.tokonyadia.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MidtransDetailsRequest {
    private String order_id;
    private Long gross_amount;
}
