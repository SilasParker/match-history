import java.util.Date;

public class Set implements Comparable<Set> {
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
    
    private boolean[] getScoreOrder() {
        return this.scoreOrder;
    }

    private String getOpponent() {
        return this.opponent;
    }

    private String getTeammate() {
        return this.teammate;
    }

    private String getTournament() {
        return this.tournament;
    }

    Date getDate() {
        return this.date;
    }

    @Override
    public int compareTo(Set o) {
        return getDate().compareTo(o.getDate());
    }
}
