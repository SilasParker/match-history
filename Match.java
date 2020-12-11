import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

class Match {
    private Character[] playerChars;
    private Character[] opponentChars;
    private Map map;
    private boolean win;

    public Match(Character[] playerChars, Character[] opponentChars, Map map, boolean win) {
        this.playerChars = playerChars;
        this.opponentChars = opponentChars;
        this.map = map;
        this.win = win;
    }

    public String toString() {
        String toPrint = "Match: ";
        for (Character playerChar : playerChars) {
            toPrint += playerChar.toString() + " ";
        }
        for (Character opponentChar : opponentChars) {
            toPrint += opponentChar.toString() + " ";
        }
        toPrint += this.map.toString() + " ";
        toPrint += this.win + " ";
        return toPrint;
    }

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
        json.add("map", this.map.toJsonObject());
        json.addProperty("win", this.win);
        return json;

    }

    public Character[] getPlayerCharacters() {
        return this.playerChars;
    }

    public Character[] getOpponentCharacters() {
        return this.opponentChars;
    }

    public Map getMap() {
        return this.map;
    }

    public boolean isWin() {
        return this.win;
    }

}