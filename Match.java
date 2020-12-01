import com.google.gson.Gson;

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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}