package com.tekion.models;

public class BattingStatsOfPlayerInSingleMatch {
    private int runsScored;
    private int fours;
    private int sixes;
    private int ballsPlayed;
    private String wicketType;
    private int SubordinateId;

    public BattingStatsOfPlayerInSingleMatch(){

    }

    public int getSubordinateId() {
        return SubordinateId;
    }

    public String getWicketType() {
        return wicketType;
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

    public void setFours(int fours) {
        this.fours = fours;
    }


    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public void setSubordinateId(int subordinateId) {
        SubordinateId = subordinateId;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }
}
