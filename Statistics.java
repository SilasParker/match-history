
class Statistics {
    private SetList setList;

    public Statistics(SetList setList) {
        this.setList = setList;
    }

    public double calculateSetWinRate() {
        int wins = 0;
        int losses = 0;
        for(int i = 0;i < setList.getLength();i++) {
            if(setList.getSet(i).getWin()) {
                wins++;
            } else {
                losses++;
            }
        }
        if(wins == 0) {
            return 0.0;
        } else if(losses == 0) {
            return 100.0;
        } else {
            return (wins / (double) (wins+losses))*100;
        }
    }

    public double calculateGameWinRate() {
        int wins = 0;
        int losses = 0;
        for(int i = 0;i < setList.getLength();i++) {
            for(boolean win : setList.getSet(i).getScoreOrder()) {
                if(win) {
                    wins++;
                } else {
                    losses++;
                }
            }
        }
        if(wins == 0) {
            return 0.0;
        } else if(losses == 0) {
            return 100.0;
        } else {
            return (wins / (double) (wins+losses))*100;
        }
    }

    public int calculateTotalSetWinsLosses(boolean win) {
        int total = 0;
        for(Set set : setList.getAllSets()) {
            if(set.getWin() == win) {
                total++;
            }
        }
        return total;
    }

    public int calculateTotalGameWinsLosses(boolean win) {
        int total = 0;
        for(Set set : setList.getAllSets()) {
            for(boolean result : set.getScoreOrder()) {
                if(result == win) {
                    total++;
                }
            }
        }
        return total;
    }



    




}