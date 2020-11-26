import java.util.Date;

public class Set {
    private Match[] matches;
    private boolean[] scoreOrder;
    private String opponent;
    private String teammate;
    private String tournament;
    private Date date;

    public Set(Match[] matches, boolean[] scoreOrder, String opponent, String teammate, String tournament, Date date) {
        this.matches = matches;
        this.scoreOrder = scoreOrder;
        this.opponent = opponent;
        this.teammate = teammate;
        this.tournament = tournament;
        this.date = date;
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
