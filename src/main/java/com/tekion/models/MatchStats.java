package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/*

This class is a model to store
stats of a match, and we send object
of this class as a response to get match stats
request.
 */
public class MatchStats {
    int id;
    int overs;

    @JsonProperty("first_team_stats")
    TeamStats teamAStats;

    @JsonProperty("first_team_players_stats")
    List<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForA;

    @JsonProperty("second_team_stats")
    TeamStats teamBStats;

    @JsonProperty("second_team_players_stats")
    List<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForB;

    @JsonProperty("toss_won_by")
    int tossWinningTeamId;

    @JsonProperty("winner")
    int winningTeamId;

    MatchStats(){

    }

    public MatchStats(Builder builder) {
        this.id = builder.id;
        this.overs = builder.overs;
        this.teamAStats = builder.teamAStats;
        this.teamBStats = builder.teamBStats;
        this.playerStatsInSingleMatchesForA = builder.playerStatsInSingleMatchesForA;
        this.playerStatsInSingleMatchesForB = builder.playerStatsInSingleMatchesForB;
        this.winningTeamId = builder.winningTeamId;
        this.tossWinningTeamId = builder.tossWinningTeamId;
    }

    public static class Builder {

        private int id;
        private int overs;
        private TeamStats teamAStats;
        private TeamStats teamBStats;
        private int winningTeamId;
        private int tossWinningTeamId;
        private List<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForA;
        private List<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForB;

        public Builder(int id,int overs) {
            this.id = id;
            this.overs = overs;
        }

        public Builder statsOfTeamA(TeamStats teamStats) {
            teamAStats = teamStats;
            return this;
        }

        public Builder statsOfTeamB(TeamStats teamStats) {
            teamBStats = teamStats;
            return this;
        }

        public Builder statsOfPlayerOfTeamA(List<PlayerStatsInSingleMatch> playerStatsInSingleMatches){
            playerStatsInSingleMatchesForA = playerStatsInSingleMatches;
            return this;
        }
        public Builder statsOfPlayerOfTeamB(List<PlayerStatsInSingleMatch> playerStatsInSingleMatches){
            playerStatsInSingleMatchesForB = playerStatsInSingleMatches;
            return this;
        }

        public Builder winningTeam(int winningTeam) {
            winningTeamId = winningTeam;
            return this;
        }

        public Builder tossWinningTeam(int tossWinningTeam) {
            tossWinningTeamId = tossWinningTeam;
            return this;
        }

        public MatchStats build() {
            return new MatchStats(this);
        }
    }

    public MatchStats(int id, TeamStats teamAStats, TeamStats teamBStats, int overs, int tossWinningTeamId, int winningTeamId,
                      List<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForA, List<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForB){
        this.id = id;
        this.overs = overs;
        this.winningTeamId = winningTeamId;
        this.tossWinningTeamId = tossWinningTeamId;
        this.teamAStats = teamAStats;
        this.teamBStats = teamBStats;
        this.playerStatsInSingleMatchesForA = playerStatsInSingleMatchesForA;
        this.playerStatsInSingleMatchesForB = playerStatsInSingleMatchesForB;
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

    public List<PlayerStatsInSingleMatch> getPlayerStatsInSingleMatchesForA() {
        return playerStatsInSingleMatchesForA;
    }

    public List<PlayerStatsInSingleMatch> getPlayerStatsInSingleMatchesForB() {
        return playerStatsInSingleMatchesForB;
    }

    public void setPlayerStatsInSingleMatchesForA(ArrayList<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForA) {
        this.playerStatsInSingleMatchesForA = playerStatsInSingleMatchesForA;
    }

    public void setPlayerStatsInSingleMatchesForB(ArrayList<PlayerStatsInSingleMatch> playerStatsInSingleMatchesForB) {
        this.playerStatsInSingleMatchesForB = playerStatsInSingleMatchesForB;
    }
}
