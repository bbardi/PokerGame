package edu.bbardi.pokerbackend.game.repository;

import edu.bbardi.pokerbackend.game.model.PlayingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayingUserRepository extends JpaRepository<PlayingUser,Long> {
    Optional<PlayingUser> findByName(String name);
    List<PlayingUser> findAllByInGameBalance(Long balance);
}
