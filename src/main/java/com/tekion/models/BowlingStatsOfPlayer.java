package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BowlingStatsOfPlayer {
    private int wickets;

    @JsonProperty("runs_conceeded")
    private int runsConceeded;

    @JsonProperty("balls_bowled")
    private int ballsBowled;


    public BowlingStatsOfPlayer(){

    }

    public BowlingStatsOfPlayer(int wickets, int runsConceeded, int ballsBowled){
        this.wickets = wickets;
        this.runsConceeded = runsConceeded;
        this.ballsBowled = ballsBowled;
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
