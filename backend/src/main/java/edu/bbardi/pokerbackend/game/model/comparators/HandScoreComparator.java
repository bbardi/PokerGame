package edu.bbardi.pokerbackend.game.model.comparators;

import edu.bbardi.pokerbackend.game.model.HandScore;

import java.util.Comparator;

public class HandScoreComparator implements Comparator<HandScore> {
    @Override
    public int compare(HandScore o1, HandScore o2) {
        if(o1.getRank().ordinal() < o2.getRank().ordinal())
            return -1;
        if(o1.getRank().ordinal() == o2.getRank().ordinal())
            if(o1.getScore() < o2.getScore())
                return -1;
            else
                if(o1.getScore().equals(o2.getScore()))
                    return 0;
                else
                    return 1;
        return 1;
    }
}
