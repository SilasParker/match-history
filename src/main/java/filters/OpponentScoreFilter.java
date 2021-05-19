package src.main.java.filters;

import src.main.java.Set;

//Inhereted Filter class to filter by Opponent's Score
public class OpponentScoreFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // filterOpponentWins: The score that is compared to the Opponent's Score within
    // the Set
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, int filterOpponentWins) {
        boolean[] scoreOrder = set.getScoreOrder();
        int opponentWins = 0;
        for (int i = 0; i < scoreOrder.length; i++) {
            if (!scoreOrder[i]) {
                opponentWins++;
            }
        }
        if (filterOpponentWins == opponentWins) {
            return true;
        }
        return false;
    }
}