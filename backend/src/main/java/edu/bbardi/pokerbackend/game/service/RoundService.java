package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.model.*;
import edu.bbardi.pokerbackend.game.model.comparators.HandScoreComparator;
import edu.bbardi.pokerbackend.game.model.dto.GameDTO;
import edu.bbardi.pokerbackend.game.model.dto.MessageDTO;
import edu.bbardi.pokerbackend.game.model.enums.EPlayerStatus;
import edu.bbardi.pokerbackend.game.model.enums.ERoundState;
import edu.bbardi.pokerbackend.game.repository.CardRepository;
import edu.bbardi.pokerbackend.game.repository.RoundRepository;
import edu.bbardi.pokerbackend.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoundService {
    private final PlayingUserService playingUserService;
    private final LobbyService lobbyService;
    private final RoundRepository roundRepository;
    private final CardRepository cardRepository;
    private final GameSender gameSender;
    private final ChatService chatService;
    private final UserManagementService userManagementService;


    private boolean isAnyonelastStanding(RoundState round) {
        Long playersInRound = round.getPlayerStatus().stream()
                .map(PlayerStatus::getStatus)
                .filter(s-> s == EPlayerStatus.INGAME)
                .count();
        return playersInRound == 1L;
    }

    public PlayerStatus declareWinner(RoundState roundState) {
        if(isAnyonelastStanding(roundState)){
            return roundState.getPlayerStatus().stream()
                    .filter(s-> s.getStatus() == EPlayerStatus.INGAME)
                    .collect(Collectors.toList()).get(0);
        }
        List<PlayerStatus> remainingPlayers = roundState.getPlayerStatus().stream()
                .filter(s-> s.getStatus() == EPlayerStatus.INGAME)
                .collect(Collectors.toList());
        List<HandScore> handScores = remainingPlayers.stream()
                .map(p -> HandService.determineScore(p,roundState.getBoardCards()))
                .sorted(new HandScoreComparator())
                .collect(Collectors.toList());

        return handScores.get(handScores.size()-1).getUser();
    }


    public List<Card> putCards(RoundState roundState,int n){
        List<Card> cardsDrawn = roundState.getDeck().takeN(n);
        roundState.getBoardCards().addAll(cardsDrawn);
        return cardsDrawn;
    }

    //existing methods
    public void createRound(Lobby lobby){
        List<Card> cardList = cardRepository.findAll();
        Collections.shuffle(cardList);
        Deck deck = new Deck(cardList,0);
        List<PlayerStatus> statusList = lobby.getUsers().stream()
                .map(u -> PlayerStatus.builder()
                        .user(u)
                        .status(EPlayerStatus.INGAME)
                        .bet(0L)
                        .currentHand(deck.takeN(2))
                        .build())
                .collect(Collectors.toList());
        int firstPlayer = new Random().nextInt(statusList.size());
        statusList.get(firstPlayer).setBet(1L);
        RoundState newState = RoundState.builder()
                .deck(deck)
                .playerStatus(statusList)
                .counter(1)
                .firstPlayerIndex(firstPlayer)
                .build();
        roundRepository.saveState(lobby.getId(), newState);
        gameSender.sendStartSignal(newState);
        gameSender.sendPlayerTurn(newState);
        chatService.sendMessage(MessageDTO.builder()
                .lobbyID(lobby.getId())
                .username("SYSTEM")
                .message("New round started")
                .build());
    }

    public void fold(GameDTO gameDTO) {
        RoundState round = roundRepository.getState(gameDTO.getLobbyID())
                .orElseThrow(()->new RuntimeException("Round doesn't exist for lobby:" + gameDTO.getLobbyID()));
        if(round.getCurrentPlayer().getUser().getName().equals(gameDTO.getUsername())){
            round.getCurrentPlayer().setStatus(EPlayerStatus.FOLDED);
            chatService.sendMessage(MessageDTO.builder()
                    .lobbyID(gameDTO.getLobbyID())
                    .username("SYSTEM")
                    .message(gameDTO.getUsername()+" has FOLDED")
                    .build());
            if(isAnyonelastStanding(round)){
                processWinner(gameDTO.getLobbyID(),round);
            }else{
                round.nextPlayer();
                gameSender.sendPlayerTurn(round);
            }
        }
        else
            gameSender.sendPlayerTurn(round);
    }

    private void advanceRound(Long lobbyID, RoundState round){
        switch(round.getRoundState()){
            case NOCARDS:
                gameSender.sendCards(round.getPlayerStatus(),putCards(round,3));
                round.setRoundState(ERoundState.FLOP);
                roundRepository.saveState(lobbyID,round);
                gameSender.sendPlayerTurn(round);
                break;
            case FLOP:
                gameSender.sendCards(round.getPlayerStatus(),putCards(round,1));
                round.setRoundState(ERoundState.TURN);
                roundRepository.saveState(lobbyID,round);
                gameSender.sendPlayerTurn(round);
                break;
            case TURN:
                gameSender.sendCards(round.getPlayerStatus(),putCards(round,1));
                round.setRoundState(ERoundState.RIVER);
                roundRepository.saveState(lobbyID,round);
                gameSender.sendPlayerTurn(round);
                break;
            case RIVER:
                processWinner(lobbyID,round);
                break;
        }
    }

    public void check(GameDTO gameDTO){
        RoundState round = roundRepository.getState(gameDTO.getLobbyID())
                .orElseThrow(()->new RuntimeException("Round doesn't exist for lobby:" + gameDTO.getLobbyID()));
        if(round.getCurrentPlayer().getUser().getName().equals(gameDTO.getUsername()) && round.getCurrentPlayer().getBet().equals(round.getBetRequired())){
            round.nextPlayer();
            if(round.shouldRevealNextCards())
                advanceRound(gameDTO.getLobbyID(),round);
            else{
                roundRepository.saveState(gameDTO.getLobbyID(),round);
                gameSender.sendPlayerTurn(round);
            }
        }else{
            gameSender.sendPlayerTurn(round);
        }
    }

    public void call(GameDTO gameDTO){
        RoundState round = roundRepository.getState(gameDTO.getLobbyID())
                .orElseThrow(()->new RuntimeException("Round doesn't exist for lobby:" + gameDTO.getLobbyID()));
        if(round.getCurrentPlayer().getUser().getName().equals(gameDTO.getUsername())){
            round.getCurrentPlayer().setBet(round.getBetRequired());
            round.nextPlayer();
            roundRepository.saveState(gameDTO.getLobbyID(),round);
        }
        gameSender.sendPlayerTurn(round);
    }

    private void processWinner(Long lobbyID, RoundState round) {
        PlayerStatus winner = declareWinner(round);
        Long sum = 0L;
        for(PlayerStatus players : round.getPlayerStatus()){
            players.getUser().setInGameBalance(players.getUser().getInGameBalance()- players.getBet());
            sum += players.getBet();
            playingUserService.saveUser(players.getUser());
        }
        winner.getUser().setInGameBalance(winner.getUser().getInGameBalance() + sum);
        playingUserService.saveUser(winner.getUser());
        playingUserService.prune();
        round.getPlayerStatus().stream()
                .map(PlayerStatus::getUser)
                .filter(u-> u.getInGameBalance() == 0)
                .forEach(gameSender::sendLoss);
        chatService.sendMessage(MessageDTO.builder()
                .lobbyID(lobbyID)
                .username("SYSTEM")
                .message(winner.getUser().getName()+" has WON the round")
                .build());
        if(lobbyService.findByID(lobbyID).getUsers().size()>=2)
            createRound(lobbyService.findByID(lobbyID));
        else{
            gameSender.sendWin(winner.getUser());
            playingUserService.deleteUser(winner.getUser());
            lobbyService.deleteLobby(lobbyID);
            userManagementService.addWinnerBalance(winner.getUser());
        }
    }

    public void raise(GameDTO gameDTO) {
        RoundState round = roundRepository.getState(gameDTO.getLobbyID())
                .orElseThrow(()->new RuntimeException("Round doesn't exist for lobby:" + gameDTO.getLobbyID()));
        if(round.getCurrentPlayer().getUser().getName().equals(gameDTO.getUsername()) &&
                gameDTO.getAmount() >= round.getBetRequired() &&
                gameDTO.getAmount() <= round.getBetLimit()) {
            round.getCurrentPlayer().setBet(gameDTO.getAmount());
            round.nextPlayer();
            roundRepository.saveState(gameDTO.getLobbyID(),round);
        }
        gameSender.sendPlayerTurn(round);
    }
}
