package edu.bbardi.pokerbackend.game.service;

import edu.bbardi.pokerbackend.game.model.Card;
import edu.bbardi.pokerbackend.game.model.HandScore;
import edu.bbardi.pokerbackend.game.model.PlayerStatus;
import edu.bbardi.pokerbackend.game.model.PlayingUser;
import edu.bbardi.pokerbackend.game.model.enums.ECardNumber;
import edu.bbardi.pokerbackend.game.model.enums.ECardSymbol;
import edu.bbardi.pokerbackend.game.model.enums.EHandRank;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

public class HandService {
    private static Long HighCardScoring(List<Card> cards){
        Integer max = -1;
        for(Card card : cards){
            if(max < card.getCardNumber().ordinal())
                max = card.getCardNumber().ordinal();
        }
        return Long.valueOf(max);
    }
    private static Long PairScoring(List<Card> cards){
        Integer max = -1;
        for(ECardNumber cardNumber : ECardNumber.values()){
            Long cardCount = cards.stream()
                    .filter(c-> c.getCardNumber() == cardNumber)
                    .count();
            if(cardCount >= 2 && cardNumber.ordinal() > max)
                max = cardNumber.ordinal();
        }
        return Long.valueOf(max);
    }
    private static Long TwoPairScoring(List<Card> cards){
        List<Integer> pairList = new LinkedList<>();
        for(ECardNumber cardNumber : ECardNumber.values()){
            Long cardCount = cards.stream()
                    .filter(c-> c.getCardNumber() == cardNumber)
                    .count();
            if(cardCount >= 2)
                pairList.add(cardNumber.ordinal());
        }
        //Last two highest pairs
        try{
        return Long.valueOf(pairList.get(pairList.size()-1) +
                pairList.get(pairList.size()-2));
        }catch(IndexOutOfBoundsException e){
            return -1L;
        }
    }
    private static Long ThreeOfAKindScoring(List<Card> cards){
        Integer max = -1;
        for(ECardNumber cardNumber : ECardNumber.values()){
            Long cardCount = cards.stream()
                    .filter(c-> c.getCardNumber() == cardNumber)
                    .count();
            if(cardCount >= 3 && cardNumber.ordinal() > max)
                max = cardNumber.ordinal();
        }
        return Long.valueOf(max);
    }
    private static Long FourOfAKindScoring(List<Card> cards){
        Integer max = -1;
        for(ECardNumber cardNumber : ECardNumber.values()){
            Long cardCount = cards.stream()
                    .filter(c-> c.getCardNumber() == cardNumber)
                    .count();
            if(cardCount == 4 && cardNumber.ordinal() > max)
                max = cardNumber.ordinal();
        }
        return Long.valueOf(max);
    }
    private static Long FlushScoring(List<Card> cards){
        Long max = -1L;
        for(ECardSymbol cardSymbol : ECardSymbol.values()){
            Long cardCount = cards.stream()
                    .filter(c-> c.getCardSymbol() == cardSymbol)
                    .count();
            Long sum = cards.stream()
                    .filter(c-> c.getCardSymbol() == cardSymbol)
                    .mapToLong(c -> c.getCardNumber().ordinal())
                    .sum();
            if(cardCount >= 3 && sum > max)
                max = sum;
        }
        return max;
    }

    public static HandScore determineScore(PlayerStatus user, List<Card> revealedCards){
        List<Card> cards = new LinkedList<>(revealedCards);
        cards.addAll(user.getCurrentHand());
        if(FourOfAKindScoring(cards) != -1L)
            return HandScore.builder()
                    .rank(EHandRank.FOUR_OF_A_KIND)
                    .score(FourOfAKindScoring(cards))
                    .user(user)
                    .build();
        if(FlushScoring(cards) != -1L)
            return HandScore.builder()
                    .rank(EHandRank.FLUSH)
                    .score(FlushScoring(cards))
                    .user(user)
                    .build();
        if(ThreeOfAKindScoring(cards) != -1L)
            return HandScore.builder()
                    .rank(EHandRank.THREE_OF_A_KIND)
                    .score(ThreeOfAKindScoring(cards))
                    .user(user)
                    .build();
        if(TwoPairScoring(cards) != -1L)
            return HandScore.builder()
                    .rank(EHandRank.TWO_PAIR)
                    .score(TwoPairScoring(cards))
                    .user(user)
                    .build();
        if(PairScoring(cards) != -1L)
            return HandScore.builder()
                    .rank(EHandRank.PAIR)
                    .score(PairScoring(cards))
                    .user(user)
                    .build();
        return HandScore.builder()
                .user(user)
                .rank(EHandRank.HIGH_CARD)
                .score(HighCardScoring(cards))
                .build();
    }
}
