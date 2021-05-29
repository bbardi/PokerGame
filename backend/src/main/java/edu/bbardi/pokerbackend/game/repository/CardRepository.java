package edu.bbardi.pokerbackend.game.repository;

import edu.bbardi.pokerbackend.game.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
