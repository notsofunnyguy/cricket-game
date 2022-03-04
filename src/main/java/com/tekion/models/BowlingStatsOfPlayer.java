package com.tekion.models;

public class BowlingStatsOfPlayer {
    private int wickets;
    private int runsConceeded;
    private int ballsBowled;

    public BowlingStatsOfPlayer(){

    }

    public int getRunsConceeded() {
        return runsConceeded;
    }

    public int getWickets() {
        return wickets;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public void setRunsConceeded(int runsConceeded) {
        this.runsConceeded = runsConceeded;
    }

    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }
}
