import java.text.SimpleDateFormat;
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

    private boolean[] calculateScoreOrder(Match[] givenMatches) { //make this calculate the winner (boolean win)
        boolean[] calculatedScoreOrder = new boolean[givenMatches.length];
        for(int i = 0;i < givenMatches.length;i++) {
            calculatedScoreOrder[i] = givenMatches[i].isWin();
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

}
