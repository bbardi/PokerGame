package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.model.Card;
import edu.bbardi.pokerbackend.game.model.PlayerStatus;
import edu.bbardi.pokerbackend.game.model.PlayingUser;
import edu.bbardi.pokerbackend.game.model.RoundState;
import edu.bbardi.pokerbackend.game.model.dto.CardsDTO;
import edu.bbardi.pokerbackend.game.model.dto.EndgameDTO;
import edu.bbardi.pokerbackend.game.model.dto.PlayerStatusDTO;
import edu.bbardi.pokerbackend.game.model.dto.TurnDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.bbardi.pokerbackend.UrlMapping.GAME;

@Service
@RequiredArgsConstructor
public class GameSender {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendStartSignal(RoundState round){
        for(PlayerStatus status : round.getPlayerStatus()){
            simpMessagingTemplate.convertAndSend(
                    GAME + "/"+ status.getUser().getName(),
                    PlayerStatusDTO.builder()
                            .bet(status.getBet())
                            .username(status.getUser().getName())
                            .hand(status.getCurrentHand())
                            .build()
            );
        }
    }

    public void sendPlayerTurn(RoundState round) {
        PlayerStatus playerStatus = round.getCurrentPlayer();
        simpMessagingTemplate.convertAndSend(
                GAME + "/" + playerStatus.getUser().getName(),
                TurnDTO.builder()
                        .betAmount(playerStatus.getBet())
                        .betLimit(round.getBetLimit())
                        .betRequired(round.getBetRequired())
                        .currentAmount(playerStatus.getUser().getInGameBalance())
                        .build()
        );
    }

    public void sendLoss(PlayingUser playingUser) {
        simpMessagingTemplate.convertAndSend(
                GAME + "/" + playingUser.getName(),
                EndgameDTO.builder()
                    .username(playingUser.getName())
                    .winner(false)
                    .sum(playingUser.getCurrentLobby().getMinimumBalance())
                    .build()
        );
    }

    public void sendWin(PlayingUser playingUser) {
        simpMessagingTemplate.convertAndSend(
                GAME + "/" + playingUser.getName(),
                EndgameDTO.builder()
                        .username(playingUser.getName())
                        .winner(true)
                        .sum(playingUser.getInGameBalance())
                        .build()
        );
    }

    public void sendCards(List<PlayerStatus> players,List<Card> cards) {
        for(PlayerStatus status : players) {
            simpMessagingTemplate.convertAndSend(
                    GAME + "/" + status.getUser().getName(),
                    CardsDTO.builder()
                            .cardsDrawn(cards)
                            .build());
        }
    }
}
