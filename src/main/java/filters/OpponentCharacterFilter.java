package src.main.java.filters;

import src.main.java.Match;
import src.main.java.Set;
import src.main.java.Character;

public class OpponentCharacterFilter extends Filter {
    @Override
    public boolean apply(Set set, Object character) {
        Match[] matches = set.getMatches();
        for (int i = 0; i < matches.length; i++) {
            for (int j = 0; j < matches[i].getOpponentCharacters().length; j++) {
                if ((Character) matches[i].getOpponentCharacters()[j] == character) {
                    return true;
                }
            }
        }
        return false;
    }
}