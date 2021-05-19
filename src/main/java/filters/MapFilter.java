package src.main.java.filters;

import src.main.java.Map;
import src.main.java.Match;
import src.main.java.Set;

//Inhereted Filter class to filter by Map
public class MapFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // map: The Map that is compared to all the Maps within the Set's Matches
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, Object map) {
        Map mapToCompare = (Map) map;
        Match[] matches = set.getMatches();
        for (int i = 0; i < matches.length; i++) {
            if (matches[i].getMap().getName().equals(mapToCompare.getName())) {
                return true;
            }
        }
        return false;
    }
}