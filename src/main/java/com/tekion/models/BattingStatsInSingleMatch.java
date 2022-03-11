package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/*

This class is a model to store our
batting stats of a player in a single match.
 */
public class BattingStatsInSingleMatch {
    @JsonProperty("runs_scored")
    private int runsScored;

    private int fours;
    private int sixes;

    @JsonProperty("balls_played")
    private int ballsPlayed;

    @JsonProperty("wicket_type")
    private String wicketType;

    @JsonProperty("subordinate_id")
    private int subordinateId;

    public BattingStatsInSingleMatch(){

    }

    public BattingStatsInSingleMatch(int runsScored, int fours, int sixes, int ballsPlayed, String wicketType, int subordinateId){
        this.runsScored = runsScored;
        this.fours = fours;
        this.sixes = sixes;
        this.ballsPlayed = ballsPlayed;
        this.wicketType = wicketType;
        this.subordinateId = subordinateId;
    }

    public int getSubordinateId() {
        return subordinateId;
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
        subordinateId = subordinateId;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }
}
