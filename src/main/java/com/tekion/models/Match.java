package com.tekion.models;

public class Match {

    int id;
    int overs;
    int teamAId;
    String teamAName;
    int teamARuns;
    int teamAWickets;
    float teamAOversPlayed;
    int teamBId;
    String teamBName;
    int teamBRuns;
    int teamBWickets;
    float teamBOversPlayed;
    int tossWinningTeamId;
    int winningTeamId;

    Match(){

    }

    public Match(int id, int teamAId, int teamBId, String teamAName, String teamBName, int overs){
        this.id = id;
        this.teamAId = teamAId;
        this.teamAName = teamAName;
        this.teamBId = teamBId;
        this.teamBName = teamBName;
        this.overs = overs;
    }

    public int getId() {
        return id;
    }

    public int getOvers() {
        return overs;
    }

    public int getTeamAId() {
        return teamAId;
    }

    public int getTeamARuns() {
        return teamARuns;
    }

    public int getTeamAWickets() {
        return teamAWickets;
    }

    public int getTeamBId() {
        return teamBId;
    }

    public int getTeamBRuns() {
        return teamBRuns;
    }

    public int getTeamBWickets() {
        return teamBWickets;
    }

    public int getTossWinningTeamId() {
        return tossWinningTeamId;
    }

    public int getWinningTeamId() {
        return winningTeamId;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public float getTeamAOversPlayed() {
        return teamAOversPlayed;
    }

    public float getTeamBOversPlayed() {
        return teamBOversPlayed;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public void setTeamAId(int teamAId) {
        this.teamAId = teamAId;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public void setTeamARuns(int teamARuns) {
        this.teamARuns = teamARuns;
    }

    public void setTeamAWickets(int teamAWickets) {
        this.teamAWickets = teamAWickets;
    }

    public void setTeamBId(int teamBId) {
        this.teamBId = teamBId;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public void setTeamBRuns(int teamBRuns) {
        this.teamBRuns = teamBRuns;
    }

    public void setTeamBWickets(int teamBWickets) {
        this.teamBWickets = teamBWickets;
    }

    public void setTossWinningTeamId(int tossWinningTeamId) {
        this.tossWinningTeamId = tossWinningTeamId;
    }

    public void setWinningTeamId(int winningTeamId) {
        this.winningTeamId = winningTeamId;
    }

    public void setTeamAOversPlayed(float teamAOversPlayed) {
        this.teamAOversPlayed = teamAOversPlayed;
    }

    public void setTeamBOversPlayed(float teamBOversPlayed) {
        this.teamBOversPlayed = teamBOversPlayed;
    }


}
