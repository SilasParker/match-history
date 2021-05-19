package src.main.java.filters;

import src.main.java.Set;

//Inhereted Filter class to filter by Player's Score
public class PlayerScoreFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // filterPlayerWins: The score that is compared to the Player's Score in the Set
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, int filterPlayerWins) {
        boolean[] scoreOrder = set.getScoreOrder();
        int playerWins = 0;
        for (int i = 0; i < scoreOrder.length; i++) {
            if (scoreOrder[i]) {
                playerWins++;
            }
        }
        if (filterPlayerWins == playerWins) {
            return true;
        }
        return false;
    }
}