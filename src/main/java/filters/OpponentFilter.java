package src.main.java.filters;

import src.main.java.Set;

//Inhereted Filter class to filter by Opponent
public class OpponentFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // opponent: The Opponent's name that is compared to the Set's Opponent
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, String opponent) {
        if (set.getOpponent().toLowerCase().equals(opponent.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}