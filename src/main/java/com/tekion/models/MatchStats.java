package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchStats {
    int id;
    int overs;

    @JsonProperty("first_team_stats")
    TeamStats teamAStats;

    @JsonProperty("second_team_stats")
    TeamStats teamBStats;

    @JsonProperty("toss_won_by")
    int tossWinningTeamId;

    @JsonProperty("winner")
    int winningTeamId;

    MatchStats(){

    }

    public MatchStats(int id, TeamStats teamAStats, TeamStats teamBStats, int overs, int tossWinningTeamId, int winningTeamId){
        this.id = id;
        this.overs = overs;
        this.winningTeamId = winningTeamId;
        this.tossWinningTeamId = tossWinningTeamId;
        this.teamAStats = teamAStats;
        this.teamBStats = teamBStats;
    }

    public int getId() {
        return id;
    }

    public int getOvers() {
        return overs;
    }

    public int getTossWinningTeamId() {
        return tossWinningTeamId;
    }

    public int getWinningTeamId() {
        return winningTeamId;
    }

    public TeamStats getTeamAStats() {
        return teamAStats;
    }

    public TeamStats getTeamBStats() {
        return teamBStats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public void setTeamAStats(TeamStats teamAStats) {
        this.teamAStats = teamAStats;
    }

    public void setTeamBStats(TeamStats teamBStats) {
        this.teamBStats = teamBStats;
    }

    public void setTossWinningTeamId(int tossWinningTeamId) {
        this.tossWinningTeamId = tossWinningTeamId;
    }

    public void setWinningTeamId(int winningTeamId) {
        this.winningTeamId = winningTeamId;
    }

}
