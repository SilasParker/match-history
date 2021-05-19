package src.main.java;

//Class to represent a Character's Column withint the Table tab
public class CharacterStatColumn {
    private final int num, setsPlayed, matchesPlayed;
    private final String characterName, bestMap, worstMap, bestMatchup, worstMatchup;
    private final double setWinRatio, matchWinRatio;

    // Constructor to initialise a column instance
    // num: The unique id assigned to this Character' column
    // characterName: The name of this Character
    // setWinRatio: The win ratio of this Character's Sets
    // matchWinRatio: The win ratio of this Character's Matches
    // bestMap: The name of the Map that this Character has the best win rate on
    // worstMap: The name of the Map that this Character has the worst win rate on
    // bestMatchup: The name of the Character that this Character has the best win
    // rate against
    // worstMatchup: The name of the Character that this Character has the worst win
    // rate against
    // setsPlayed: The total number of set played by this Character
    // matchesPlayed: The total number of matches played by this Character
    public CharacterStatColumn(int num, String characterName, double setWinRatio, double matchWinRatio, String bestMap,
            String worstMap, String bestMatchup, String worstMatchup, int setsPlayed, int matchesPlayed) {
        this.num = num;
        this.characterName = characterName;
        this.setWinRatio = setWinRatio;
        this.matchWinRatio = matchWinRatio;
        this.bestMap = bestMap;
        this.worstMap = worstMap;
        this.bestMatchup = bestMatchup;
        this.worstMatchup = worstMatchup;
        this.setsPlayed = setsPlayed;
        this.matchesPlayed = matchesPlayed;
    }

    // Getter for the Map with the best win ratio
    // Returns: Name of the Map
    public String getBestMap() {
        return this.bestMap;
    }

    // Getter for the Character with the best win ratio against
    // Returns: Name of the Character
    public String getBestMatchup() {
        return this.bestMatchup;
    }

    // Getter for the Character that this column focuses on
    // Returns: Name of the Character
    public String getCharacterName() {
        return this.characterName;
    }

    // Getter for the total number of Matches played by this Character
    // Returns: The total
    public int getMatchesPlayed() {
        return this.matchesPlayed;
    }

    // Getter for the Match win ratio with this Character
    // Returns: The ratio
    public double getMatchWinRatio() {
        if (Double.isNaN(matchWinRatio)) {
            return 0.0;
        }
        return this.matchWinRatio;
    }

    // Getter for this column's unique ID
    // Returns: the ID
    public int getNum() {
        return this.num;
    }

    // Getter for the total number of Sets played by this Character
    // Returns: The total
    public int getSetsPlayed() {
        return this.setsPlayed;
    }

    // Getter for the Set win ratio with this Character
    // Returns: The ratio
    public double getSetWinRatio() {
        if (Double.isNaN(setWinRatio)) {
            return 0.0;
        }
        return this.setWinRatio;
    }

    // Getter for the Map with the worst win ratio
    // Returns: Name of the Map
    public String getWorstMap() {
        return this.worstMap;
    }

    // Getter for the Character with the best win ratio against
    // Returns: Name of the Character
    public String getWorstMatchup() {
        return this.worstMatchup;
    }

}
