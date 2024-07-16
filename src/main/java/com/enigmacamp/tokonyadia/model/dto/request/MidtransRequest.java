package com.enigmacamp.tokonyadia.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MidtransRequest {
    private MidtransDetailsRequest transaction_details;
}
