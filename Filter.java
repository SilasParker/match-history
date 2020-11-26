import java.util.Date;

abstract class Filter {
    public boolean apply(Set set, String string) {return false;}
    public boolean apply(Set set, int integer) {return false;}
    public boolean apply(Set set, Date date) {return false;}
    public boolean apply(Set set, Object object) {return false;}
}
//Create filter interface and all types of filters

class PlayerCharacterFilter extends Filter {
    @Override
    public boolean apply(Set set, int character) {
        Match[] matches = set.getMatches();
        for(int i = 0;i < matches.length;i++) {
            for(int j = 0;j < matches[i].getPlayerCharacters().length;j++) {
                if(matches[i].getPlayerCharacters()[j] == character) {
                    return true; 
                }
            }
        }
        return false;
    }
}

class OpponentCharacterFilter extends Filter {
    @Override
    public boolean apply(Set set, int character) {
        Match[] matches = set.getMatches();
        for(int i = 0;i < matches.length;i++) {
            for(int j = 0;j < matches[i].getOpponentCharacters().length;j++) {
                if(matches[i].getOpponentCharacters()[j] == character) {
                    return true;
                }
            }
        }
        return false;
    }
}

class MapFilter extends Filter {
    @Override
    public boolean apply(Set set, int map) {
        Match[] matches = set.getMatches();
        for(int i = 0;i < matches.length; i++) {
            if(matches[i].getMap() == map) {
                return true;
            }
        }
        return false;
    }
}

class PlayerScoreFilter extends Filter {
    @Override
    public boolean apply(Set set, int filterPlayerWins) {
        boolean[] scoreOrder = set.getScoreOrder();
        int playerWins = 0;
        for(int i = 0;i < scoreOrder.length;i++) {
            if(scoreOrder[i]) {
                playerWins++;
            }
        }
        if(filterPlayerWins == playerWins) {
            return true;
        }
        return false;
    }
}

class OpponentScoreFilter extends Filter {
    @Override
    public boolean apply(Set set, int filterOpponentWins) {
        boolean[] scoreOrder = set.getScoreOrder();
        int opponentWins = 0;
        for(int i = 0;i < scoreOrder.length;i++) {
            if(!scoreOrder[i]) {
                opponentWins++;
            }
        }
        if(filterOpponentWins == opponentWins) {
            return true;
        }
        return false;
    }
}

class OpponentFilter extends Filter {
    @Override
    public boolean apply(Set set, String opponent) {
        if(set.getOpponent() == opponent) {
            return true;
        }
        return false;
    }
}

class TeammateFilter extends Filter {
    @Override
    public boolean apply(Set set, String teammate) {
        if(set.getTeammate() == teammate) {
            return true;
        }
        return false;
    }
}

class TournamentFilter extends Filter {
    @Override
    public boolean apply(Set set, String tournament) {
        if(set.getTournament() == tournament) {
            return true;
        }
        return false;
    }
}

class DateFilter extends Filter {
    @Override
    public boolean apply(Set set, Date date) {
        if(set.getDate() == date) {
            return true;
        }
        return false;
    }
}