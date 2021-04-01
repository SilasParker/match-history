package src.main.java;

public class CharacterStat implements Comparable<CharacterStat> {
    private Character character;
    private Game gameRef; // reference to the game at hand
    private int[] mapWins, mapLosses, charWins, charLosses;
    private int matchWins, matchLosses, setWins, setLosses, setCount, matchCount;
    private double setWinRatio, matchWinRatio;
    private String characterName, bestMatchup, worstMatchup, bestMap, worstMap;

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

    public CharacterStat(String characterName, Double setWinRatio, Double matchWinRatio, String bestMap, String worstMap, String bestMatchup, String worstMatchup, Integer setCount, Integer matchCount) {
        this.characterName = characterName;
        this.setWinRatio = setWinRatio;
        this.matchWinRatio = matchWinRatio;
        this.bestMap = bestMap;
        this.worstMap = worstMap;
        this.bestMatchup = bestMatchup;
        this.worstMatchup = worstMatchup;
        this.setCount = setCount;
        this.matchCount = matchCount;
    }

    public void setAllFinalisedStats() {
        this.setWinRatio = calculateSetWinPercentage();
        this.matchWinRatio = calculateMatchWinPercentage();
        this.setCount = calculateTotalSetsPlayed();
        this.matchCount = calculateTotalMatchesPlayed();
        this.bestMatchup = calculateBestCharacterWinRate().getName();
        this.worstMatchup = calculateWorstCharacterWinRate().getName();
        this.bestMap = calculateBestMapWinRate().getName();
        this.worstMap = calculateWorstMapWinRate().getName();
        this.characterName = character.getName();
    }

    public double getSetWinRatio() {
        return setWinRatio;
    }

    public double getMatchWinRatio() {
        return matchWinRatio;
    } 

    public int getSetCount() {
        return setCount;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public String getBestMatchup() {
        return bestMatchup;
    }

    public String getWorstMatchup() {
        return worstMatchup;
    }

    public String getBestMap() {
        return bestMap;
    }

    public String getWorstMap() {
        return worstMap;
    }

    public String getCharacterName() {
        return characterName;
    }

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

    private void fillIntArrayWithZero(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    public void incrementMatchWins() {
        this.matchWins++;
    }

    public void incrementMatchLosses() {
        this.matchLosses++;
    }

    public void incrementSetWins() {
        this.setWins++;
    }

    public void incrementSetLosses() {
        this.setLosses++;
    }

    public void incrementMapWins(Map map) {
        for (int i = 0; i < gameRef.getMaps().length; i++) {
            if (gameRef.getMaps()[i] == map) {
                this.mapWins[i]++;
            }
        }
    }

    public void incrementMapLosses(Map map) {
        for (int i = 0; i < gameRef.getMaps().length; i++) {
            if (gameRef.getMaps()[i] == map) {
                this.mapLosses[i]++;
            }
        }
    }

    public void incrementCharacterWins(Character character) {
        for (int i = 0; i < gameRef.getCharacters().length; i++) {
            if (gameRef.getCharacters()[i] == character) {
                this.charWins[i]++;
            }
        }
    }

    public void incrementCharacterLosses(Character character) {
        for (int i = 0; i < gameRef.getCharacters().length; i++) {
            if (gameRef.getCharacters()[i] == character) {
                this.charLosses[i]++;
            }
        }
    }

    private double calculateSetWinPercentage() {
        return (this.setWins / (double) (this.setWins + this.setLosses)) * 100;
    }

    private double calculateMatchWinPercentage() {
        return (this.matchWins / (double) (this.matchWins + this.matchLosses)) * 100;
    }

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
        return this.gameRef.getMaps()[index];
    }

    private Map calculateWorstMapWinRate() {
        int index = 0;
        double low = 100.0;
        int gamesPlayed = 0;
        for (int i = 0; i < mapWins.length; i++) {
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
        return this.gameRef.getMaps()[index];
    }

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
        return this.gameRef.getCharacters()[index];
    }

    private Character calculateWorstCharacterWinRate() {
        int index = 0;
        double low = 100.0;
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
        return this.gameRef.getCharacters()[index];
    }

    private int calculateTotalSetsPlayed() {
        return setWins + setLosses;
    }

    private int calculateTotalMatchesPlayed() {
        return matchWins + matchLosses;
    }

    @Override
    public int compareTo(CharacterStat o) {
        int compareMatchCount = o.calculateTotalMatchesPlayed();
        return compareMatchCount - calculateTotalMatchesPlayed();
    }

}
