package src.main.java.filters;

import src.main.java.Set;

//Inhereted Filter class to filter by Teammate
public class TeammateFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // teammate: The Teammate Name that is compared to the Set's Teammate's Name
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, String teammate) {
        if (set.getTeammate().toLowerCase().equals(teammate.toLowerCase())) {
            return true;
        }
        return false;
    }
}