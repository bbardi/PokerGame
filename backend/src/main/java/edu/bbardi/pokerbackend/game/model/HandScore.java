package edu.bbardi.pokerbackend.game.model;

import edu.bbardi.pokerbackend.game.model.enums.EHandRank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HandScore {
    private PlayerStatus user;
    private EHandRank rank;
    private Long score;
}
