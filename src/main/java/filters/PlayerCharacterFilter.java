package src.main.java.filters;

import src.main.java.Match;
import src.main.java.Set;
import src.main.java.Character;

public class PlayerCharacterFilter extends Filter {
    @Override
    public boolean apply(Set set, Object character) {
        System.out
                .println("Checking for any matches with " + character.toString() + " from set vs " + set.getOpponent());
        Match[] matches = set.getMatches();
        for (int i = 0; i < matches.length; i++) {
            System.out.println("Checking game " + (i + 1));
            for (int j = 0; j < matches[i].getPlayerCharacters().length; j++) {
                System.out.println("Checking character " + (j + 1));
                if ((Character) matches[i].getPlayerCharacters()[j] == character) {
                    System.out.println("Match found!");
                    return true;
                }
            }
        }
        System.out.println("Match not found");
        return false;
    }
}