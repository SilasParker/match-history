package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class Statistics {
    private SetList setList;
    private CharacterStat[] characterStats;
    private Game gameRef;

    public Statistics(SetList setList, Game game) {
        this.setList = setList;
        this.gameRef = game;
        this.characterStats = generateCharacterStatsInstances();
        fillCharacterStats();
    }

    public String toString() {
        String output = "All Characters and Stats:\n";
        for (CharacterStat charStat : characterStats) {
            output += charStat.toString();
        }
        return output;
    }

    public void sortByMatchCount() {
        Arrays.sort(characterStats);
    }

    public ObservableList<CharacterStatColumn> getCharacterStatColumnObservableList() {
        ObservableList<CharacterStatColumn> characterStatsObservable = FXCollections.observableArrayList();
        int count = 1;
        for (CharacterStat charStat : characterStats) {
            CharacterStatColumn column = charStat.getColumn(count);
            count++;
            characterStatsObservable.add(column);
        }
        return characterStatsObservable;
    }

    public CharacterStat[] getCharacterStats() {
        return this.characterStats;
    }

    public CharacterStat[] generateCharacterStatsInstances() {
        CharacterStat[] charStatArrInstance = new CharacterStat[gameRef.getCharacters().length];
        int count = 0;
        for (Character character : gameRef.getCharacters()) {
            charStatArrInstance[count] = new CharacterStat(character, gameRef);
            count++;
        }

        return charStatArrInstance;
    }

    public void fillCharacterStats() {
        for (Set set : this.setList.getAllSets()) {
            if (set.getWin()) {
                generateSetWins(set);
            } else {
                generateSetLosses(set);
            }
            generateMatchWinsLosses(set);
            if (gameRef.isMap()) {
                generateMapWinsLosses(set);
            }
            generateCharWinsLosses(set);

        }

    }

    private void generateCharWinsLosses(Set set) {
        for (Match match : set.getMatches()) {
            for (Character character : match.getPlayerCharacters()) {
                for (Character oppCharacter : match.getOpponentCharacters()) {
                    if (match.isWin()) {
                        characterStats[getCharacterIndex(character)].incrementCharacterWins(oppCharacter);
                    } else {
                        characterStats[getCharacterIndex(character)].incrementCharacterLosses(oppCharacter);
                    }
                }
            }
        }
    }

    private void generateMapWinsLosses(Set set) {
        for (Match match : set.getMatches()) {
            for (Character character : match.getPlayerCharacters()) {
                if (match.isWin()) {
                    System.out.println("Found win for "+character.getName()+" on "+match.getMap().getName());
                    characterStats[getCharacterIndex(character)].incrementMapWins(match.getMap());
                } else {
                    characterStats[getCharacterIndex(character)].incrementMapLosses(match.getMap());
                }
            }

        }
    }

    private void generateMatchWinsLosses(Set set) {
        for (Match match : set.getMatches()) {
            for (Character character : match.getPlayerCharacters()) {
                if (match.isWin()) {
                    characterStats[getCharacterIndex(character)].incrementMatchWins();
                } else {
                    characterStats[getCharacterIndex(character)].incrementMatchLosses();
                }
            }

        }
    }

    private void generateSetWins(Set set) {
        ArrayList<Character> characters = new ArrayList<>();
        for (Match match : set.getMatches()) {
            if (match.isWin()) {
                for (Character character : match.getPlayerCharacters()) {
                    if (!characters.contains(character)) {
                        characters.add(character);
                    }
                }
            }
        }
        for (Character character : characters) {
            characterStats[getCharacterIndex(character)].incrementSetWins();
        }
    }

    private void generateSetLosses(Set set) {
        ArrayList<Character> characters = new ArrayList<>();
        for (Match match : set.getMatches()) {
            if (!match.isWin()) {
                for (Character character : match.getPlayerCharacters()) {
                    if (!characters.contains(character)) {
                        characters.add(character);
                    }
                }
            }
        }
        for (Character character : characters) {
            characterStats[getCharacterIndex(character)].incrementSetLosses();
        }
    }

    private int getCharacterIndex(Character character) {
        int count = 0;
        for (Character charToCompare : gameRef.getCharacters()) {
            if (character.getName().equals(charToCompare.getName())) {
                return count;
            }
            count++;
        }
        return -1;
    }

}