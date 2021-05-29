package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.model.Card;
import edu.bbardi.pokerbackend.game.model.Lobby;
import edu.bbardi.pokerbackend.game.model.PlayerStatus;
import edu.bbardi.pokerbackend.game.model.PlayingUser;
import edu.bbardi.pokerbackend.game.repository.PlayingUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayingUserService {
    private final PlayingUserRepository playingUserRepository;

    public PlayingUser findByName(String name){
        return playingUserRepository.findByName(name)
                .orElseThrow(()-> new EntityNotFoundException("Player not found with username: "+name));
    }

    public void saveUser(PlayingUser user) {
        playingUserRepository.save(user);
    }
    public void prune(){
        playingUserRepository.deleteAll(playingUserRepository.findAllByInGameBalance(0L));
    }

    public void deleteUser(PlayingUser user) {
        playingUserRepository.delete(user);
    }
}
