package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.mapper.LobbyMapper;
import edu.bbardi.pokerbackend.game.model.enums.ELobbyStatus;
import edu.bbardi.pokerbackend.game.model.Lobby;
import edu.bbardi.pokerbackend.game.model.LobbyStatus;
import edu.bbardi.pokerbackend.game.model.PlayingUser;
import edu.bbardi.pokerbackend.game.model.dto.LobbyDTO;
import edu.bbardi.pokerbackend.game.repository.LobbyRepository;
import edu.bbardi.pokerbackend.game.repository.PlayingUserRepository;
import edu.bbardi.pokerbackend.game.repository.StatusRepository;
import edu.bbardi.pokerbackend.user.model.User;
import edu.bbardi.pokerbackend.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LobbyService {
    private final StatusRepository statusRepository;
    private final LobbyRepository lobbyRepository;
    private final PlayingUserRepository playingUserRepository;
    private final LobbyMapper lobbyMapper;
    private final UserManagementService userService;

    public Lobby findByID(Long id){
        return lobbyRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Unable to find lobby with id: "+id));
    }

    public LobbyDTO create(LobbyDTO lobbyDTO) {
        Lobby lobby = lobbyMapper.fromDTO(lobbyDTO);
        LobbyStatus waiting = statusRepository.findByName(ELobbyStatus.WAITING)
                .orElseThrow(()-> new EntityNotFoundException("Cannot find lobby status WAITING"));
        lobby.setStatus(waiting);
        return lobbyMapper.toDTO(lobbyRepository.save(lobby));
    }

    public List<LobbyDTO> findAll() {
        return lobbyRepository.findAll().stream()
                .map(lobbyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LobbyDTO join(Long id, Long userID) {
        Lobby lobby = findByID(id);
        User user = userService.findByID(userID);

        if(lobby.getStatus().getName() != ELobbyStatus.WAITING)
            throw new RuntimeException("Lobby is not waiting for new users");
        if(lobby.getMinimumBalance() > user.getBalance())
            throw new RuntimeException("User doesn't have enough money");
        PlayingUser newPlayer = PlayingUser.builder()
                .name(user.getUsername())
                .inGameBalance(lobby.getMinimumBalance())
                .currentLobby(lobby)
                .build();
        newPlayer = playingUserRepository.save(newPlayer);
        lobby.getUsers().add(newPlayer);
        return lobbyMapper.toDTO(lobby);
    }

    public LobbyStatus findStatus(ELobbyStatus status) {
        return statusRepository.findByName(status)
                .orElseThrow(()-> new EntityNotFoundException("Lobby status not found: "+ status.name()));
    }
    public void save(Lobby lobby){
        lobbyRepository.save(lobby);
    }

    public void deleteLobby(Long lobbyID) {
        lobbyRepository.deleteById(lobbyID);
    }
}
