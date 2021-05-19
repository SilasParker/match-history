package src.main.java.filters;

import src.main.java.Match;
import src.main.java.Set;
import src.main.java.Character;

//Inhereted Filter class to filter by Player's Character
public class PlayerCharacterFilter extends Filter {

    // Determines whether a set should be filtered or not
    // set: The Set to potentially filter
    // character: The Character that is compared to all Player's Characters within
    // the Set's Matches
    // Returns: Whether the Set should be filtered or not
    @Override
    public boolean apply(Set set, Object character) {
        Character characterToCompare = (Character) character;
        Match[] matches = set.getMatches();
        for (int i = 0; i < matches.length; i++) {
            for (int j = 0; j < matches[i].getPlayerCharacters().length; j++) {
                if (matches[i].getPlayerCharacters()[j].getName().equals(characterToCompare.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}