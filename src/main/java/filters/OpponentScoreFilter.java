package src.main.java.filters;

import src.main.java.Set;

public class OpponentScoreFilter extends Filter {
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