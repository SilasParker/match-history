package src.main.java.filters;

import src.main.java.Set;

public class PlayerScoreFilter extends Filter {
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