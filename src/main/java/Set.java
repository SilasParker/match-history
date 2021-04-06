package src.main.java;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private LocalDate date;
    private boolean win;

    public Set(Match[] matches, String opponent, String teammate, String tournament, LocalDate date) {
        this.matches = matches;
        this.scoreOrder = calculateScoreOrder(matches);
        this.opponent = opponent;
        this.teammate = teammate;
        this.tournament = tournament;
        this.date = date;
    }

    

    public ArrayList<Character> getMostPlayedCharacters(boolean opponent) {
        ArrayList<Character> allChars = new ArrayList<>();
        ArrayList<Integer> charCount = new ArrayList<>();
        for(Match match : matches) {
            if(!opponent) {
                for(Character characters : match.getPlayerCharacters()) {
                    if(!allChars.contains(characters)) {
                        System.out.println("ADDED: "+characters.getName());
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
        ArrayList<Character> allCharsOrdered = new ArrayList<>();
        System.out.println(allChars.size());
        for(int i = 0; i < allChars.size(); i++) {
            int highNum = 0;
            int highIndex = 0;
            for(int j = 0;i < allChars.size();i++) {
                if(charCount.get(j) > highNum) {
                    highNum = charCount.get(j);
                    highIndex = j;
                }
            }
            //CHECK WHY THIS AINT WORKIN
            System.out.println("SUPER ADD: "+allChars.get(highIndex));
            allCharsOrdered.add(allChars.get(highIndex));
            allChars.remove(highIndex);
            charCount.remove(highIndex);
        }
        return allCharsOrdered;       
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-yyyy");
        json.addProperty("date", date.format(formatter));
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

    public LocalDate getDate() {
        return this.date;
    }

    public boolean getWin() {
        return this.win;
    }

    public Date getLocalDateAsDate() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(date.atStartOfDay(defaultZoneId).toInstant());
    }

}
