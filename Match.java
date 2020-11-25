import com.google.gson.Gson;

class Match {
    private int[] playerChars;
    private int[] opponentChars;
    private int map;
    private boolean win;

    public Match(int[] playerChars, int[] opponentChars, int map, boolean win) {
        this.playerChars = playerChars;
        this.opponentChars = opponentChars;
        this.map = map;
        this.win = win;
    }

    public int[] getPlayerCharacters() {
        return this.playerChars;
    }

    private int[] getOpponentCharacters() {
        return this.opponentChars;
    }

    private int getMap() {
        return this.map;
    }

    private boolean isWin() {
        return this.win;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}