package src.main.java;

public class CharacterStatColumn {
    private final int num, setsPlayed, matchesPlayed;
    private final String characterName, bestMap, worstMap, bestMatchup, worstMatchup;
    private final double setWinRatio, matchWinRatio;
    
    public CharacterStatColumn(int num, String characterName, double setWinRatio, double matchWinRatio, String bestMap, String worstMap, String bestMatchup, String worstMatchup, int setsPlayed, int matchesPlayed) {
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

    public int getNum() {
        return this.num;
    }


    public int getSetsPlayed() {
        return this.setsPlayed;
    }


    public int getMatchesPlayed() {
        return this.matchesPlayed;
    }


    public String getCharacterName() {
        return this.characterName;
    }


    public String getBestMap() {
        return this.bestMap;
    }


    public String getWorstMap() {
        return this.worstMap;
    }


    public String getBestMatchup() {
        return this.bestMatchup;
    }


    public String getWorstMatchup() {
        return this.worstMatchup;
    }


    public double getSetWinRatio() {
        if(Double.isNaN(setWinRatio)) {
            return 0.0;
        }
        return this.setWinRatio;
    }


    public double getMatchWinRatio() {
        if(Double.isNaN(matchWinRatio)) {
            return 0.0;
        }
        return this.matchWinRatio;
    }
    
}
