
class Statistics {
    private SetList setList;

    public Statistics(SetList setList) {
        this.setList = setList;
    }

    public double calculateSetWinRate() {
        int wins = 0;
        int losses = 0;
        for(int i = 0;i < setList.getLength();i++) {
            Set currentSet = setList.getSet(i);
            boolean[] scoreOrder = currentSet.getScoreOrder();
            int scoreCount = 0;
            for(boolean win : scoreOrder) { //replace this with getWinner() from Set
                if(win) {
                    scoreCount++;
                } else {
                    scoreCount--;
                }
            }
            if(scoreCount > 0) {
                wins++;
            } else {
                losses--;
            }
        }
        if(wins == 0) {
            return 0.0;
        } else if(losses == 0) {
            return 100.0;
        } else {
            return wins/losses;
        }
    }

    




}