package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.model.Lobby;
import edu.bbardi.pokerbackend.game.model.PlayingUser;
import edu.bbardi.pokerbackend.game.model.dto.MessageDTO;
import edu.bbardi.pokerbackend.game.repository.LobbyRepository;
import edu.bbardi.pokerbackend.game.repository.PlayingUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.stream.Collectors;

import static edu.bbardi.pokerbackend.UrlMapping.CHAT;
import static edu.bbardi.pokerbackend.UrlMapping.RECEIVE_MESSAGE;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LobbyRepository lobbyRepository;
    private final PlayingUserRepository playingUserRepository;

    public void sendMessage(MessageDTO messageRequest) {
        Lobby lobby = lobbyRepository.findById(messageRequest.getLobbyID())
                .orElseThrow(() -> new EntityNotFoundException("Lobby not found with id: " + messageRequest.getLobbyID()));
        if (messageRequest.getUsername().equals("SYSTEM")) {
            simpMessagingTemplate.convertAndSend(CHAT + "/" +
                            messageRequest.getLobbyID() +
                            RECEIVE_MESSAGE,
                    messageRequest
            );
        } else {
            PlayingUser author = playingUserRepository.findByName(messageRequest.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + messageRequest.getUsername()));
            if (lobby.getUsers().stream().map(PlayingUser::getId).collect(Collectors.toList()).contains(author.getId())) {
                simpMessagingTemplate.convertAndSend(CHAT + "/" +
                                messageRequest.getLobbyID() +
                                RECEIVE_MESSAGE,
                        messageRequest
                );
            }
        }
    }
}
