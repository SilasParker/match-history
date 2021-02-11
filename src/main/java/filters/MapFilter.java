package src.main.java.filters;

import src.main.java.Map;
import src.main.java.Match;
import src.main.java.Set;

public class MapFilter extends Filter {
    @Override
    public boolean apply(Set set, Object map) {
        Match[] matches = set.getMatches();
        for (int i = 0; i < matches.length; i++) {
            if ((Map) matches[i].getMap() == map) {
                return true;
            }
        }
        return false;
    }
}