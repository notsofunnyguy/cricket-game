package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BattingStatsOfPlayer {

    @JsonProperty("runs_scored")
    private int runsScored;

    int fifties;
    int hundreds;
    private int fours;
    private int sixes;

    @JsonProperty("balls_played")
    private int ballsPlayed;

    public BattingStatsOfPlayer(){

    }

    public BattingStatsOfPlayer(int runsScored, int fifties, int hundreds, int fours, int sixes, int ballsPlayed){
        this.runsScored = runsScored;
        this.fifties = fifties;
        this.hundreds = hundreds;
        this.fours = fours;
        this.sixes = sixes;
        this.ballsPlayed = ballsPlayed;
    }

    public int getHundreds() {
        return hundreds;
    }

    public int getFifties() {
        return fifties;
    }

    public int getFours() {
        return fours;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public int getSixes() {
        return sixes;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public void setHundreds(int hundreds) {
        this.hundreds = hundreds;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public void setFifties(int fifties) {
        this.fifties = fifties;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }
}

