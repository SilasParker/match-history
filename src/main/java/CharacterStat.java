package src.main.java;

//Class that calculates and handles a single Character's statistics
public class CharacterStat implements Comparable<CharacterStat> {
    private Character character;
    private Game gameRef; // reference to the game at hand
    private int[] mapWins, mapLosses, charWins, charLosses;
    private int matchWins, matchLosses, setWins, setLosses;

    // Constructor to initialise a Character's Stat's instance
    // character: The Character who's statistics are being calculated and stored
    // game: The Game in which the Character is contained
    public CharacterStat(Character character, Game game) {
        this.character = character;
        this.gameRef = game;
        this.mapWins = new int[gameRef.getMaps().length];
        this.matchWins = 0;
        this.matchLosses = 0;
        this.setWins = 0;
        this.setLosses = 0;
        this.mapLosses = new int[mapWins.length];
        this.charWins = new int[gameRef.getCharacters().length];
        this.charLosses = new int[charWins.length];
        fillIntArrayWithZero(mapWins);
        fillIntArrayWithZero(mapLosses);
        fillIntArrayWithZero(charWins);
        fillIntArrayWithZero(charLosses);
    }

    // Converts all this Character's statistics into a String
    // Returns: A String containing all this Character's statistics neatly formatted
    public String toString() {
        String output = character.getName() + "\n";
        output += "Total Sets Played: " + calculateTotalSetsPlayed() + "\n";
        output += "Set Win/Loss: " + setWins + "/" + setLosses + "\n";
        output += "Total Set Winrate (%): " + calculateSetWinPercentage() + "\n\n";

        output += "Total Matches Played: " + calculateTotalMatchesPlayed() + "\n";
        output += "Match Win/Loss: " + matchWins + "/" + matchLosses + "\n";
        output += "Total Match Winrate (%): " + calculateMatchWinPercentage() + "\n\n";

        output += "Map Win/Loss: \n";
        for (int i = 0; i < mapWins.length; i++) {
            output += gameRef.getMaps()[i].getName() + ": " + mapWins[i] + "/" + mapLosses[i] + "\n";
        }
        output += "Best Map: " + calculateBestMapWinRate().getName() + "\n";
        output += "Worst Map: " + calculateWorstMapWinRate().getName() + "\n\n";

        output += "Character Win/Loss:\n";
        for (int i = 0; i < charWins.length; i++) {
            output += gameRef.getCharacters()[i].getName() + ": " + charWins[i] + "/" + charLosses[i] + "\n";
        }
        output += "Best Character Matchup" + calculateBestCharacterWinRate().getName() + "\n";
        output += "Worst Character Matchup" + calculateWorstCharacterWinRate().getName() + "\n";
        return output + "\n";
    }

    // Getter for the Character
    public Character getCharacter() {
        return this.character;
    }

    // Getter for the Character's loss count versus every Character in this Game
    // Returns: List of integers each representing the number of losses versus every
    // Character
    public int[] getCharacterLosses() {
        return this.charLosses;
    }

    // Getter for the Character's win count versus every Character in this Game
    // Returns: List of integers each representing the number of wins versus every
    // Character
    public int[] getCharacterWins() {
        return this.charWins;
    }

    // Getter for the Character's loss count on every Map in this Game
    // Returns: List of integers each representing the number of losses on every Map
    public int[] getMapLosses() {
        return this.mapLosses;
    }

    // Getter for the Character's win count on every Map in this Game
    // Returns: List of integers each representing the number of wins on every Map
    public int[] getMapWins() {
        return this.mapWins;
    }

    // Fills an integer array with zeroes
    // array: integer array to fill with zeroes
    public void fillIntArrayWithZero(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    // Calculates the total amount of Sets played with this Character
    // Returns: The total
    private int calculateTotalSetsPlayed() {
        return setWins + setLosses;
    }

    // Calculates the Set Win Ratio for this Character
    // Returns: The ratio
    private double calculateSetWinPercentage() {
        return (this.setWins / (double) (this.setWins + this.setLosses)) * 100;
    }

    // Calculates the total amount of Matches played with this Character
    // Returns: The total
    public int calculateTotalMatchesPlayed() {
        return matchWins + matchLosses;
    }

    // Calculates the Match Win Ratio for this Character
    // Returns: The ratio
    public double calculateMatchWinPercentage() {
        double result = (this.matchWins / (double) (this.matchWins + this.matchLosses)) * 100;
        if (Double.isNaN(result)) {
            return 0.0;
        }
        return (this.matchWins / (double) (this.matchWins + this.matchLosses)) * 100;
    }

    // Calculates the Map with the highest win ratio for this Character
    // Returns: The Map
    private Map calculateBestMapWinRate() {
        int index = 0;
        double high = 0.0;
        int gamesPlayed = 0;
        for (int i = 0; i < mapWins.length; i++) {
            double winrate = (this.mapWins[i] / (double) (this.mapWins[i] + this.mapLosses[i]));
            if (winrate > high) {
                high = winrate;
                index = i;
                gamesPlayed = this.mapWins[i] + this.mapLosses[i];
            } else if (winrate == high && (this.mapWins[i] + this.mapLosses[i]) > gamesPlayed) {
                high = winrate;
                index = i;
                gamesPlayed = this.mapWins[i] + this.mapLosses[i];
            }
        }
        if (high == 0.0) {
            return new Map("N/A");
        }
        return this.gameRef.getMaps()[index];
    }

    // Calculates the Map with the lowest win ratio for this Character
    // Returns: The Map
    private Map calculateWorstMapWinRate() {
        int index = 0;
        double low = 1.0;
        int gamesPlayed = 0;
        for (int i = 0; i < mapLosses.length; i++) {
            double winrate = (this.mapWins[i] / (double) (this.mapWins[i] + this.mapLosses[i]));
            if (winrate < low) {
                low = winrate;
                index = i;
                gamesPlayed = this.mapWins[i] + this.mapLosses[i];
            } else if (winrate == low && (this.mapWins[i] + this.mapLosses[i]) > gamesPlayed) {
                low = winrate;
                index = i;
                gamesPlayed = this.mapWins[i] + this.mapLosses[i];
            }
        }
        if (low == 1.0) {
            return new Map("N/A");
        }
        return this.gameRef.getMaps()[index];
    }

    // Calculates the Character with the highest win ratio for this Character
    // Returns: The Character
    private Character calculateBestCharacterWinRate() {
        int index = 0;
        double high = 0.0;
        int gamesPlayed = 0;
        for (int i = 0; i < charWins.length; i++) {
            double winrate = (this.charWins[i] / (double) (this.charWins[i] + this.charLosses[i]));
            if (winrate > high) {
                high = winrate;
                index = i;
                gamesPlayed = this.charWins[i] + this.charLosses[i];
            } else if (winrate == high && (this.charWins[i] + this.charLosses[i]) > gamesPlayed) {
                high = winrate;
                index = i;
                gamesPlayed = this.charWins[i] + this.charLosses[i];
            }
        }
        if (high == 0.0) {
            return new Character("N/A", null);
        }
        return this.gameRef.getCharacters()[index];
    }

    // Calculates the Character with the lowest win ratio for this Character
    // Returns: The Character
    private Character calculateWorstCharacterWinRate() {
        int index = 0;
        double low = 1.0;
        int gamesPlayed = 0;
        for (int i = 0; i < charWins.length; i++) {
            double winrate = (this.charWins[i] / (double) (this.charWins[i] + this.charLosses[i]));
            if (winrate < low) {
                low = winrate;
                index = i;
            } else if (winrate == low && (this.charWins[i] + this.charLosses[i]) > gamesPlayed) {
                low = winrate;
                index = i;
                gamesPlayed = this.charWins[i] + this.charLosses[i];
            }
        }
        if (low == 1.0) {
            return new Character("N/A", null);
        }
        return this.gameRef.getCharacters()[index];
    }

    // Generates a CharacterStatColumn to represent this Character for the Table tab
    // num: The unique number associated with this character
    // Returns: The initialised CharacterStatColumn
    public CharacterStatColumn getColumn(int num) {
        return new CharacterStatColumn(num, character.getName(), calculateSetWinPercentage(),
                calculateMatchWinPercentage(), calculateBestMapWinRate().getName(),
                calculateWorstMapWinRate().getName(), calculateBestCharacterWinRate().getName(),
                calculateWorstCharacterWinRate().getName(), calculateTotalSetsPlayed(), calculateTotalMatchesPlayed());
    }

    // Increments the number of wins on the specified Map for this Character
    /// map: The Map to increment the wins for
    public void incrementMapWins(Map map) {
        for (int i = 0; i < gameRef.getMaps().length; i++) {
            if (gameRef.getMaps()[i].getName().equals(map.getName())) {
                this.mapWins[i]++;
            }
        }
    }

    // Increments the number of losses on the specified Map for this Character
    /// map: The Map to increment the losses for
    public void incrementMapLosses(Map map) {
        for (int i = 0; i < gameRef.getMaps().length; i++) {
            if (gameRef.getMaps()[i].getName().equals(map.getName())) {
                this.mapLosses[i]++;
            }
        }
    }

    // Increments the number of wins against the specified Character for this
    // Character
    /// character: The Character to increment the wins against
    public void incrementCharacterWins(Character character) {
        for (int i = 0; i < gameRef.getCharacters().length; i++) {
            if (gameRef.getCharacters()[i].getName().equals(character.getName())) {
                this.charWins[i]++;
            }
        }
    }

    // Increments the number of losses against the specified Character for this
    // Character
    /// character: The Character to increment the losses against
    public void incrementCharacterLosses(Character character) {
        for (int i = 0; i < gameRef.getCharacters().length; i++) {
            if (gameRef.getCharacters()[i].getName().equals(character.getName())) {
                this.charLosses[i]++;
            }
        }
    }

    // Allows the comparison between another CharacterStat instance based on the
    // total number of Matches played
    // o: CharacterStat to compare against
    // Returns: the difference in total Matches played
    @Override
    public int compareTo(CharacterStat o) {
        int compareMatchCount = o.calculateTotalMatchesPlayed();
        return compareMatchCount - calculateTotalMatchesPlayed();
    }

    // Increments the number of Matches lost with this Character
    public void incrementMatchLosses() {
        this.matchLosses++;
    }

    // Increments the number of Matches won with this Character
    public void incrementMatchWins() {
        this.matchWins++;
    }

    // Increments the number of Sets lost with this Character
    public void incrementSetLosses() {
        this.setLosses++;
    }

    // Increments the number of Sets won with this Character
    public void incrementSetWins() {
        this.setWins++;
    }

}
