package src.main.java;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//Class representing a Match instance
public class Match {
    
    private Character[] playerChars;
    private Character[] opponentChars;
    private Map map;
    private boolean win;

    // Constructor to initialise a Match instance
    // playerChars: Array of all the Characters played by the Player
    // opponentChars: Array of all the Characters played by the Opponent
    // map: Map played in this Match (if any)
    // win: Whether the player won this game or not
    public Match(Character[] playerChars, Character[] opponentChars, Map map, boolean win) {
        this.playerChars = playerChars;
        this.opponentChars = opponentChars;
        this.map = map;
        this.win = win;
    }

    // Converts all this Matches information into a String
    // Returns: A String containing all this Matches information neatly formatted
    public String toString() {
        String toPrint = "Match: ";
        for (Character playerChar : playerChars) {
            toPrint += playerChar.toString() + " ";
        }
        for (Character opponentChar : opponentChars) {
            toPrint += opponentChar.toString() + " ";
        }
        if (map != null) {
            toPrint += this.map.toString() + " ";
        }
        toPrint += this.win + " ";
        return toPrint;
    }

    // Getter for this Matches Map
    // Returns: The Map
    public Map getMap() {
        return this.map;
    }

    // Getter for the Characters played by the Opponent this Match
    // Returns: Array containing the Opponent's Characters this Match
    public Character[] getOpponentCharacters() {
        return this.opponentChars;
    }

    // Getter for the Characters played by the Player this Match
    // Returns: Array containing the Player's Characters this Match
    public Character[] getPlayerCharacters() {
        return this.playerChars;
    }

    // Getter for whether the player won this Match or not
    // Returns: true if the player won this Match, false if not
    public boolean isWin() {
        return this.win;
    }

    // Converts this Match into a JsonObject
    // Returns: This Match as a JsonObject
    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        JsonArray playerCharsArray = new JsonArray();
        JsonArray opponentCharsArray = new JsonArray();
        for (int i = 0; i < playerChars.length; i++) {
            playerCharsArray.add(playerChars[i].toJsonObject());
            opponentCharsArray.add(opponentChars[i].toJsonObject());
        }
        json.add("playerChars", playerCharsArray);
        json.add("opponentChars", opponentCharsArray);
        if (map != null) {
            json.add("map", this.map.toJsonObject());
        }
        json.addProperty("win", this.win);
        return json;

    }

}