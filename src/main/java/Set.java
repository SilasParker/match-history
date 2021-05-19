package src.main.java;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//Clas to represent a Set within the Match History
public class Set {

    private Match[] matches;
    private boolean[] scoreOrder;
    private String opponent;
    private String teammate;
    private String tournament;
    private LocalDate date;
    private boolean win;

    // Constructor to initialise a Set instance
    // matches: Array of all Matches in this Set
    // opponent: Name of Opponent being played against
    // teammate: Name of the player's teammate (if any)
    // tournament: Name of the tournament this Set was in
    // date: Date that this Set took place
    public Set(Match[] matches, String opponent, String teammate, String tournament, LocalDate date) {
        this.matches = matches;
        this.scoreOrder = calculateScoreOrder(matches);
        this.opponent = opponent;
        this.teammate = teammate;
        this.tournament = tournament;
        this.date = date;
    }

    // Converts all this Set's information into a String
    // Returns: A String containing all this Set's information neatly formatted
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

    // Getter for this Set's Date
    // Returns: The Date
    public LocalDate getDate() {
        return this.date;
    }

    // Getter for all this Set's Matches
    // Returns: The Matches in an array
    public Match[] getMatches() {
        return this.matches;
    }

    // Getter for this Set's opponent
    // Returns: The Opponent Name
    public String getOpponent() {
        return this.opponent;
    }

    // Getter for this Set's score order
    // Returns: The score order in a boolean array (true meaning win, false meaning
    // loss)
    public boolean[] getScoreOrder() {
        return this.scoreOrder;
    }

    // Getter for this Set's teammate
    // Returns: The Teammate Name
    public String getTeammate() {
        return this.teammate;
    }

    // Getter for the tournament this Set took place within
    // Returns: The Tournament Name
    public String getTournament() {
        return this.tournament;
    }

    // Getter for whether the player won this Set or not
    // Returns: Whether this Set was a win for the player or not
    public boolean getWin() {
        return this.win;
    }

    // Calculates the score order of this Set's Matches
    // givenMatches: All the Matches to account for
    // Returns: The score order in a boolean array (true meaning a win, false
    // meaning a loss)
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

    // Calculates who the most played Characters in this Set were
    // opponent: Whether it's calculating the player or opponent's most played
    // Characters
    // Returns: An ArrayList containing all Characters from most played to least
    public ArrayList<Character> getMostPlayedCharacters(boolean opponent) {
        ArrayList<Character> allChars = new ArrayList<>();
        ArrayList<Integer> charCount = new ArrayList<>();
        for (Match match : matches) {
            if (!opponent) {
                for (Character characters : match.getPlayerCharacters()) {
                    if (!allChars.contains(characters)) {
                        allChars.add(characters);
                        charCount.add(1);
                    } else {
                        int indexOfCharacter = allChars.indexOf(characters);
                        charCount.set(indexOfCharacter, charCount.get(indexOfCharacter) + 1);
                    }
                }
            } else {
                for (Character characters : match.getOpponentCharacters()) {
                    if (!allChars.contains(characters)) {
                        allChars.add(characters);
                        charCount.add(1);
                    } else {
                        int indexOfCharacter = allChars.indexOf(characters);
                        charCount.set(indexOfCharacter, charCount.get(indexOfCharacter) + 1);
                    }
                }
            }
        }
        ArrayList<Character> allCharsOrdered = new ArrayList<>();
        for (int i = 0; i <= allChars.size(); i++) {
            int highNum = 0;
            int highIndex = 0;
            for (int j = 0; j < allChars.size(); j++) {
                if (charCount.get(j) > highNum) {
                    highNum = charCount.get(j);
                    highIndex = j;
                }
            }
            allCharsOrdered.add(allChars.get(highIndex));
            allChars.remove(highIndex);
            charCount.remove(highIndex);
        }
        return allCharsOrdered;
    }

    // Converts this Set into a JsonObject
    // Returns: This Set as a JsonObject
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-yyyy");
        json.addProperty("date", date.format(formatter));
        return json;
    }

    // Converts the LocalDate object attribute into a Date object
    // Returns: The Date version of the LocalDate
    public Date getLocalDateAsDate() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(date.atStartOfDay(defaultZoneId).toInstant());
    }

}
