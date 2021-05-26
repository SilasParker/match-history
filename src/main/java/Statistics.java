package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Class that populates the CharacterStat objects
public class Statistics {

    private SetList setList;
    private CharacterStat[] characterStats;
    private Game gameRef;

    // Constructor to initialise the Statistics instance for this Game
    // setList: The SetList (Match History) that these Statistics are based on
    // game: The Game that the Match History is in reference to
    public Statistics(SetList setList, Game game) {
        this.setList = setList;
        this.gameRef = game;
        this.characterStats = generateCharacterStatsInstances();
        fillCharacterStats();
    }

    // Converts all the Stastics in this object into a String
    // Returns: A String containing all these Statistics neatly formatted
    public String toString() {
        String output = "All Characters and Stats:\n";
        for (CharacterStat charStat : characterStats) {
            output += charStat.toString();
        }
        return output;
    }

    // Getter for all the CharacterStats contained within this object
    // Returns: the CharacterStat array
    public CharacterStat[] getCharacterStats() {
        return this.characterStats;
    }

    // Generates all the CharacterStats for every Character
    // Returns: the generated CharacterStat array
    public CharacterStat[] generateCharacterStatsInstances() {
        CharacterStat[] charStatArrInstance = new CharacterStat[gameRef.getCharacters().length];
        int count = 0;
        for (Character character : gameRef.getCharacters()) {
            charStatArrInstance[count] = new CharacterStat(character, gameRef);
            count++;
        }
        return charStatArrInstance;
    }

    // Fills all the CharacterStat instances with the generated Statistics
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

    // Applies the Set Wins for each Character in their respective CharacterStat
    // instances for a particular Set
    // set: The Set instance that is being evaluated
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

    // Retrieves the index of a particular Character from within the Game's list of
    // Characters
    // character: The Character to get the index of from the Game's list of
    // Characters
    // Returns: the index that is retrieved
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

    // Applies the Set Losses for each Character in their respective CharacterStat
    // instances for a particular Set
    // set: The Set instance that is being evaluated
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

    // Applies the Match Wins and Losses for each Character in their respective
    // CharacterStat instances for a particular Set
    // set: The Set instance that is being evaluated
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

    // Applies the Map Wins and Losses for each Character in their respective
    // CharacterStat instances for a particular Set
    // set: The Set instance that is being evaluated
    private void generateMapWinsLosses(Set set) {
        for (Match match : set.getMatches()) {
            for (Character character : match.getPlayerCharacters()) {
                if (match.isWin()) {
                    characterStats[getCharacterIndex(character)].incrementMapWins(match.getMap());
                } else {
                    characterStats[getCharacterIndex(character)].incrementMapLosses(match.getMap());
                }
            }
        }
    }

    // Applies the Character Wins and Losses for each Character in their respective
    // CharacterStat instances for a particular Set
    // set: The Set instance that is being evaluated
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

    // Calculates the Set Win Ratio per month
    // Returns: an ArrayList containing an ArrayList containing the month name and
    // win ratio
    public ArrayList<ArrayList<Object>> getSetWinRatioOverMonths() {
        ArrayList<Object> monthAndYearArray = new ArrayList<>();
        ArrayList<Integer> setCount = new ArrayList<>();
        SetList setListByDate = setList.getSetListByDate();
        int count = 0;
        for (int i = setListByDate.getLength() - 1; i > -1; i--) {
            String month = setListByDate.getSet(i).getDate().getMonth().toString();
            int year = setListByDate.getSet(i).getDate().getYear();
            if (monthAndYearArray.size() == 0) {
                monthAndYearArray.add(month + " " + year);
                setCount.add(1);
            } else if (!monthAndYearArray.get(count).equals(month + " " + year)) {
                count++;
                monthAndYearArray.add(month + " " + year);
                setCount.add(1);
            } else {
                setCount.set(count, setCount.get(count) + 1);
            }
        }
        ArrayList<Set> setListArray = setListByDate.getAllSets();
        ArrayList<Object> ratioArray = new ArrayList<>();
        ArrayList<ArrayList<Object>> toReturn = new ArrayList<>();
        count = 0;
        while (setListArray.size() > 0) {
            int wins = 0;
            int losses = 0;
            for (int i = 0; i < setCount.get(count); i++) {
                if (setListArray.get(0).getWin()) {
                    wins++;
                } else {
                    losses++;
                }
                setListArray.remove(0);
            }
            if (wins == 0) {
                ratioArray.add(0.0);
            } else {
                Double ratio = (Double) ((double) wins / ((double) wins + (double) losses));
                ratioArray.add(ratio);
            }
            count++;
        }
        toReturn.add(monthAndYearArray);
        toReturn.add(ratioArray);
        return toReturn;
    }

    // Retrieves all the CharacterStatColumns
    // Returns: An ObservableList of all the CharacterStatColumns
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

    // Sorts the CharacterStats by their total match count
    public void sortByMatchCount() {
        Arrays.sort(characterStats);
    }

    // Inflates the Teammate suggestion list to the user
    // playerChars: Player Characters to take account of
    // opponentChars: Opponent Characters to take account of
    // map: Map to take account of
    // Returns: The list of results to inflate the ListView with
    public ArrayList<String> suggestTeammates(ArrayList<String> playerChars, ArrayList<String> opponentChars,
            String map) {
        ArrayList<String> teammates = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();
        ArrayList<Integer> matchCount = new ArrayList<>();
        for (Set set : setList.getAllSets()) {
            int index;
            if (teammates.contains(set.getTeammate())) {
                index = teammates.indexOf(set.getTeammate());
            } else {
                teammates.add(set.getTeammate());
                wins.add(0);
                matchCount.add(0);
                index = teammates.size() - 1;
            }
            for (Match match : set.getMatches()) {
                if (match.isWin() && matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                    wins.set(index, wins.get(index) + 1);
                    matchCount.set(index, matchCount.get(index) + 1);
                } else if (matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                    matchCount.set(index, matchCount.get(index) + 1);
                }

            }
        }
        ArrayList<Double> averages = new ArrayList<>();
        for (int i = 0; i < teammates.size(); i++) {
            Double average = (double) (wins.get(i) / (double) matchCount.get(i));
            averages.add(average * 100);
        }
        ArrayList<String> teammatesToReturn = new ArrayList<>();
        while (averages.size() > 0) {
            double high = 0.0;
            int index = 0;
            for (int i = 0; i < averages.size(); i++) {
                if (averages.get(i) >= high) {
                    high = averages.get(i);
                    index = i;
                }
            }
            teammatesToReturn.add(teammates.remove(index) + ": " + averages.remove(index) + "%");
        }
        return teammatesToReturn;
    }

    // Inflates a Character suggestion list to the user
    // player: Whether the Characters being suggested are for the Player or Opponent
    // playerChars: Player Characters to take account of
    // opponentChars: Opponent Characters to take account of
    // map: Map to take account of
    // Returns: The list of results to inflate the ListView with
    public ArrayList<String> suggestCharacters(boolean player, ArrayList<String> playerChars,
            ArrayList<String> opponentChars, String map) {
        ArrayList<String> characters = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();
        ArrayList<Integer> matchCount = new ArrayList<>();
        for (Set set : setList.getAllSets()) {
            for (Match match : set.getMatches()) {
                Character[] allCharacters = match.getPlayerCharacters();
                if (!player) {
                    allCharacters = match.getOpponentCharacters();
                }
                for (Character character : allCharacters) {
                    int index;
                    if (characters.contains(character.getName())) {
                        index = characters.indexOf(character.getName());
                    } else {
                        characters.add(character.getName());
                        wins.add(0);
                        matchCount.add(0);
                        index = characters.size() - 1;
                    }
                    if (match.isWin() && matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                        wins.set(index, wins.get(index) + 1);
                        matchCount.set(index, matchCount.get(index) + 1);
                    } else if (matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                        matchCount.set(index, matchCount.get(index) + 1);
                    }

                }
            }
        }
        ArrayList<Double> averages = new ArrayList<>();
        for (int i = 0; i < characters.size(); i++) {
            Double average = (double) (wins.get(i) / (double) matchCount.get(i));
            averages.add(average * 100);
        }
        ArrayList<String> charactersToReturn = new ArrayList<>();
        while (averages.size() > 0) {
            double high = 0.0;
            int index = 0;
            for (int i = 0; i < averages.size(); i++) {
                if (averages.get(i) >= high) {
                    high = averages.get(i);
                    index = i;
                }
            }
            if (averages.get(index).isNaN()) {
                characters.remove(index);
                averages.remove(index);
            } else {
                charactersToReturn.add(characters.remove(index) + ": " + averages.remove(index) + "%");
            }
        }
        return charactersToReturn;
    }

    // Inflates the Map suggestion list to the user
    // playerChars: Player Characters to take account of
    // opponentChars: Opponent Characters to take account of
    // map: Map to take account of
    // Returns: The list of results to inflate the ListView with
    public ArrayList<String> suggestMaps(ArrayList<String> playerChars, ArrayList<String> opponentChars, String map) {
        ArrayList<String> maps = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();
        ArrayList<Integer> matchCount = new ArrayList<>();
        for (Set set : setList.getAllSets()) {
            for (Match match : set.getMatches()) {
                int index;
                if (maps.contains(match.getMap().getName())) {
                    index = maps.indexOf(match.getMap().getName());
                } else {
                    maps.add(match.getMap().getName());
                    wins.add(0);
                    matchCount.add(0);
                    index = maps.size() - 1;
                }
                if (match.isWin() && matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                    wins.set(index, wins.get(index) + 1);
                    matchCount.set(index, matchCount.get(index) + 1);
                } else if (matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                    matchCount.set(index, matchCount.get(index) + 1);
                }
            }
        }
        ArrayList<Double> averages = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            Double average = (double) (wins.get(i) / (double) matchCount.get(i));
            averages.add(average * 100);
        }
        ArrayList<String> mapsToReturn = new ArrayList<>();
        while (averages.size() > 0) {
            double high = 0.0;
            int index = 0;
            for (int i = 0; i < averages.size(); i++) {
                if (averages.get(i) >= high) {
                    high = averages.get(i);
                    index = i;
                }
            }
            if (averages.get(index).isNaN()) {
                maps.remove(index);
                averages.remove(index);
            } else {
                mapsToReturn.add(maps.remove(index) + ": " + averages.remove(index) + "%");
            }
        }
        return mapsToReturn;
    }

    // Inflates the Opponent suggestion list to the user
    // playerChars: Player Characters to take account of
    // opponentChars: Opponent Characters to take account of
    // map: Map to take account of
    // Returns: The list of results to inflate the ListView with
    public ArrayList<String> suggestOpponents(ArrayList<String> playerChars, ArrayList<String> opponentChars,
            String map) {
        ArrayList<String> opponents = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();
        ArrayList<Integer> matchCount = new ArrayList<>();
        for (Set set : setList.getAllSets()) {
            int index;
            if (opponents.contains(set.getOpponent())) {
                index = opponents.indexOf(set.getOpponent());
            } else {
                opponents.add(set.getOpponent());
                wins.add(0);
                matchCount.add(0);
                index = opponents.size() - 1;
            }
            for (Match match : set.getMatches()) {
                if (match.isWin() && matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                    wins.set(index, wins.get(index) + 1);
                    matchCount.set(index, matchCount.get(index) + 1);
                } else if (matchesSuggestionParams(playerChars, opponentChars, map, match)) {
                    matchCount.set(index, matchCount.get(index) + 1);
                }

            }
        }
        ArrayList<Double> averages = new ArrayList<>();
        for (int i = 0; i < opponents.size(); i++) {
            Double average = (double) (wins.get(i) / (double) matchCount.get(i));
            averages.add(average * 100);
        }
        ArrayList<String> opponentsToReturn = new ArrayList<>();
        while (averages.size() > 0) {
            double high = 0.0;
            int index = 0;
            for (int i = 0; i < averages.size(); i++) {
                if (averages.get(i) >= high) {
                    high = averages.get(i);
                    index = i;
                }
            }
            opponentsToReturn.add(opponents.remove(index) + ": " + averages.remove(index) + "%");
        }
        return opponentsToReturn;
    }

    // Determines whether the match passed in has matching characteristics to all
    // other passed parameters
    // playerChars: All Player Characters to match
    // opponentChars: All Opponent Characters to match
    // map: Map to match
    // match: Match to match
    // Returns true if the match matches the parameters, false if otherwise
    private boolean matchesSuggestionParams(ArrayList<String> playerChars, ArrayList<String> opponentChars, String map,
            Match match) {
        boolean playerFilter = !playerChars.isEmpty();
        boolean opponentFilter = !opponentChars.isEmpty();
        boolean mapFilter = true;
        if (map == null) {
            mapFilter = false;
        }
        if (playerFilter) {
            int count = 0;
            for (String character : playerChars) {
                for (Character actualCharacter : match.getPlayerCharacters()) {
                    if (actualCharacter.getName().equals(character)) {
                        count++;
                    }
                }
            }
            if (count != playerChars.size()) {
                return false;
            }
        }
        if (opponentFilter) {
            int count = 0;
            for (String character : opponentChars) {
                for (Character actualCharacter : match.getOpponentCharacters()) {
                    if (actualCharacter.getName().equals(character)) {
                        count++;
                    }
                }
            }
            if (count != match.getOpponentCharacters().length) {
                return false;
            }
        }
        if (mapFilter) {
            if (!match.getMap().getName().equals(map)) {
                return false;
            }
        }
        return true;
    }
}