package edu.bbardi.pokerbackend.game.model;

import edu.bbardi.pokerbackend.game.model.enums.EPlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatus {
    private PlayingUser user;
    private Long bet;
    private EPlayerStatus status;
    private List<Card> currentHand;
}
