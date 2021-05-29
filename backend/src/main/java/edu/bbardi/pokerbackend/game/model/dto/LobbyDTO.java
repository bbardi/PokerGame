package edu.bbardi.pokerbackend.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class LobbyDTO {
    private Long id;
    private Float minimumBalance;
    private String title;
    private String status;
    private Set<String> users;
}
