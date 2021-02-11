package src.main.java.filters;

import src.main.java.Set;

public class OpponentFilter extends Filter {
    @Override
    public boolean apply(Set set, String opponent) {
        if (set.getOpponent().equals(opponent)) {
            return true;
        }
        return false;
    }
}