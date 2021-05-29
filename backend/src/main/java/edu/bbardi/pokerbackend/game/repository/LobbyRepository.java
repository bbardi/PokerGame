package edu.bbardi.pokerbackend.game.repository;

import edu.bbardi.pokerbackend.game.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {
}
