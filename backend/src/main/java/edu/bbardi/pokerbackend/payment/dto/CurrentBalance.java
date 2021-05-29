package edu.bbardi.pokerbackend.payment.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentBalance {
    private Long amount;
}
