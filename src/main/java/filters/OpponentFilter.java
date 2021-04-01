package src.main.java.filters;

import src.main.java.Set;

public class OpponentFilter extends Filter {
    @Override
    public boolean apply(Set set, String opponent) {
        System.out.println("Checking " + set.getOpponent() + " against " + opponent);
        if (set.getOpponent().toLowerCase().equals(opponent.toLowerCase())) {
            System.out.println("Opponent found");
            return true;
        } else {
            return false;
        }
    }
}