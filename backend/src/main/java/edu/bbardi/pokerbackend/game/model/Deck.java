package edu.bbardi.pokerbackend.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
public class Deck {
    private List<Card> deck;
    private Integer index = 0;

    public List<Card> takeN(int n){
        List<Card> takenCards = new LinkedList<>();
        IntStream.range(0,n).forEach(
                i-> takenCards.add(deck.get(index + i))
        );
        this.index += n;
        return takenCards;
    }
}
