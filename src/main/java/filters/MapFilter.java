package src.main.java.filters;

import src.main.java.Map;
import src.main.java.Match;
import src.main.java.Set;

public class MapFilter extends Filter {
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