package edu.bbardi.pokerbackend.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntentResponse {
    private String clientSecret;
}
