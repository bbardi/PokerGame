package edu.bbardi.pokerbackend.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private Long lobbyID;
    private String username;
    private Long amount;
}
