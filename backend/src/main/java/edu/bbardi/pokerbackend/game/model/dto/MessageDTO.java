package edu.bbardi.pokerbackend.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageDTO {
    private Long lobbyID;
    private String username;
    private String message;
}
