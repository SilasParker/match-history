package src.main.java.filters;

import src.main.java.Set;

public class TeammateFilter extends Filter {
    @Override
    public boolean apply(Set set, String teammate) {
        if (set.getTeammate().equals(teammate)) {
            return true;
        }
        return false;
    }
}