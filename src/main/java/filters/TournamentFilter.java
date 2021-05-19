package src.main.java.filters;

import src.main.java.Set;

//Inhereted Filter class to filter by Tournament
public class TournamentFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // tournament: The Tournament Name that is compared to the Set's Tournament's Name
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, String tournament) {
        if (set.getTournament().toLowerCase().equals(tournament.toLowerCase())) {
            return true;
        }
        return false;
    }
}