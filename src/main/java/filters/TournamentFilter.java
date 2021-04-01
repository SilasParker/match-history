package src.main.java.filters;

import src.main.java.Set;

public class TournamentFilter extends Filter {
    @Override
    public boolean apply(Set set, String tournament) {
        if (set.getTournament().toLowerCase().equals(tournament.toLowerCase())) {
            return true;
        }
        return false;
    }
}