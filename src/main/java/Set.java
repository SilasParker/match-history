package src.main.java;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Set {
    private Match[] matches;
    private boolean[] scoreOrder;
    private String opponent;
    private String teammate;
    private String tournament;
    private Date date;
    private boolean win;

    public Set(Match[] matches, String opponent, String teammate, String tournament, Date date) {
        this.matches = matches;
        this.scoreOrder = calculateScoreOrder(matches);
        this.opponent = opponent;
        this.teammate = teammate;
        this.tournament = tournament;
        this.date = date;
    }

    private ArrayList<Character> getMostPlayedCharacters(boolean opponent) {
        ArrayList<Character> allChars = new ArrayList<>();
        ArrayList<Integer> charCount = new ArrayList<>();
        for(Match match : matches) {
            if(!opponent) {
                for(Character characters : match.getPlayerCharacters()) {
                    if(!allChars.contains(characters)) {
                        allChars.add(characters);
                        charCount.add(1);
                    } else {
                        int indexOfCharacter = allChars.indexOf(characters);
                        charCount.set(indexOfCharacter,charCount.get(indexOfCharacter)+1);
                    }
                }
            } else {
                for(Character characters : match.getOpponentCharacters()) {
                    if(!allChars.contains(characters)) {
                        allChars.add(characters);
                        charCount.add(1);
                    } else {
                        int indexOfCharacter = allChars.indexOf(characters);
                        charCount.set(indexOfCharacter,charCount.get(indexOfCharacter)+1);
                    }
                }
            }
        }
        //TODO FINISH THIS FUNCTION. NEED TO ORDER ARRAY BY CHARCOUNT
    }

    private boolean[] calculateScoreOrder(Match[] givenMatches) {
        boolean[] calculatedScoreOrder = new boolean[givenMatches.length];
        int score = 0;
        for (int i = 0; i < givenMatches.length; i++) {
            calculatedScoreOrder[i] = givenMatches[i].isWin();
            if (givenMatches[i].isWin()) {
                score++;
            } else {
                score--;
            }
        }
        if (score > 0) {
            this.win = true;
        } else {
            this.win = false;
        }
        return calculatedScoreOrder;
    }

    public String toString() {
        String toPrint = "Set: ";
        for (Match match : matches) {
            toPrint += match.toString() + " ";
        }
        for (boolean score : scoreOrder) {
            toPrint += score + " ";
        }
        toPrint += opponent + " ";
        toPrint += teammate + " ";
        toPrint += tournament + " ";
        toPrint += date.toString() + " ";
        return toPrint;
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        JsonArray matchesArray = new JsonArray();
        JsonArray scoreOrderArray = new JsonArray();
        for (int i = 0; i < this.matches.length; i++) {
            matchesArray.add(this.matches[i].toJsonObject());
            scoreOrderArray.add(this.scoreOrder[i]);
        }
        json.add("matches", matchesArray);
        json.add("scoreOrder", scoreOrderArray);
        json.addProperty("opponent", this.opponent);
        json.addProperty("teammate", this.teammate);
        json.addProperty("tournament", this.tournament);
        SimpleDateFormat sDataFormat = new SimpleDateFormat("DD-MM-yyyy");
        json.addProperty("date", sDataFormat.format(this.date));
        return json;
    }

    public Match[] getMatches() {
        return this.matches;
    }

    public boolean[] getScoreOrder() {
        return this.scoreOrder;
    }

    public String getOpponent() {
        return this.opponent;
    }

    public String getTeammate() {
        return this.teammate;
    }

    public String getTournament() {
        return this.tournament;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean getWin() {
        return this.win;
    }

}
