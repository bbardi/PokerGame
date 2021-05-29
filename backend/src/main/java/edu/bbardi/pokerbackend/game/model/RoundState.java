package edu.bbardi.pokerbackend.game.model;

import edu.bbardi.pokerbackend.game.model.enums.EPlayerStatus;
import edu.bbardi.pokerbackend.game.model.enums.ERoundState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoundState {
    private Integer firstPlayerIndex;
    private Integer counter;
    private Deck deck;
    private List<PlayerStatus> playerStatus;
    @Builder.Default
    private ERoundState roundState = ERoundState.NOCARDS;
    @Builder.Default
    private List<Card> boardCards = new LinkedList<>();

    public Long getBetLimit(){
        return playerStatus.stream()
                .map(PlayerStatus::getUser)
                .mapToLong(PlayingUser::getInGameBalance)
                .min().orElseThrow(()-> new RuntimeException("Unable to compute min"));
    }
    public Long getBetRequired(){
        return playerStatus.stream()
                .mapToLong(PlayerStatus::getBet)
                .max().orElseThrow(()-> new RuntimeException("Unable to compute min"));
    }

    public PlayerStatus getCurrentPlayer() {
        return playerStatus.get((firstPlayerIndex + counter) % playerStatus.size());
    }

    public boolean shouldRevealNextCards() {
        return counter % playerStatus.size() == (firstPlayerIndex + 1) % playerStatus.size() &&
                playerStatus.stream().allMatch(p -> p.getBet().equals(getBetRequired()));
    }

    public void nextPlayer() {
        this.counter++;
        while(!playerStatus.get((counter + firstPlayerIndex)%playerStatus.size()).getStatus().equals(EPlayerStatus.INGAME)){
            this.counter++;
        }
    }
}
