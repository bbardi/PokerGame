package edu.bbardi.pokerbackend.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnDTO {
    private Long currentAmount;
    private Long betAmount;
    private Long betRequired;
    private Long betLimit;
}
