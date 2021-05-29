package edu.bbardi.pokerbackend.payment.dto;

import lombok.Data;

@Data
public class ChargeRequest {
    private String description;
    private String currency;
    private Long amount;
}
