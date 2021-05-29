package edu.bbardi.pokerbackend.game.model.dto;

import edu.bbardi.pokerbackend.game.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CardsDTO {
    List<Card> cardsDrawn;
}
