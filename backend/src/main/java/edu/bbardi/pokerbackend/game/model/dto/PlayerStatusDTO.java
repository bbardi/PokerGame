package edu.bbardi.pokerbackend.game.model.dto;

import edu.bbardi.pokerbackend.game.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatusDTO {
    private String username;
    private Long bet;
    private List<Card> hand;
}
