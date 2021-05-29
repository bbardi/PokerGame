package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.model.*;
import edu.bbardi.pokerbackend.game.model.dto.GameDTO;
import edu.bbardi.pokerbackend.game.model.enums.ELobbyStatus;
import edu.bbardi.pokerbackend.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final LobbyService lobbyService;
    private final PlayingUserService playingUserService;
    private final UserManagementService userManagementService;
    private final RoundService roundService;

    private Boolean shouldStart(Lobby lobby){
        return lobby.getUsers().stream().allMatch(PlayingUser::getReady) && lobby.getUsers().size() >= 2;
    }

    private void startGame(Lobby lobby){
        userManagementService.subtractBalance(lobby.getUsers(),lobby.getMinimumBalance());
        roundService.createRound(lobby);
        lobby.setStatus(lobbyService.findStatus(ELobbyStatus.INGAME));
        lobbyService.save(lobby);
    }

    public void setReady(GameDTO gameDTO) {
        Lobby lobby = lobbyService.findByID(gameDTO.getLobbyID());
        PlayingUser user = playingUserService.findByName(gameDTO.getUsername());
        if(lobby.getUsers().stream().map(PlayingUser::getId).collect(Collectors.toList()).contains(user.getId())){
            user.setReady(true);
            playingUserService.saveUser(user);
            lobby = lobbyService.findByID(gameDTO.getLobbyID());
            if(shouldStart(lobby))
                startGame(lobby);
        }
    }

}
