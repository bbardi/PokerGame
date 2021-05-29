package edu.bbardi.pokerbackend.game.repository;

import edu.bbardi.pokerbackend.game.model.RoundState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RoundRepository {
    private static final Map<Long, RoundState> stateMap = new HashMap<>();

    public void saveState(Long lobbyID, RoundState roundState){
        stateMap.put(lobbyID,roundState);
    }

    public Optional<RoundState> getState(Long lobbyID){
        return Optional.ofNullable(stateMap.get(lobbyID));
    }
}
