package edu.bbardi.pokerbackend.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EndgameDTO {
    private String username;
    private Boolean winner;
    private Long sum;
}
