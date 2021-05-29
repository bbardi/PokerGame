package edu.bbardi.pokerbackend.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Payment {
    private String username;
    private String paymentIntent;
}
