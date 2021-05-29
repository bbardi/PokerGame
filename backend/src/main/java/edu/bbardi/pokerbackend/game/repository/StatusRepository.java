package edu.bbardi.pokerbackend.game.repository;

import edu.bbardi.pokerbackend.game.model.enums.ELobbyStatus;
import edu.bbardi.pokerbackend.game.model.LobbyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<LobbyStatus, Long> {
    Optional<LobbyStatus> findByName(ELobbyStatus name);
}
