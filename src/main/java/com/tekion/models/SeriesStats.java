package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/*

This class is a model to store the
info about the series and also send
object of this class as response to
request when asked for series stats.
 */
public class SeriesStats {
    @JsonProperty("matches_count")
    private int noOfMatches;
    @JsonProperty("overs_count")
    private int overs;
    @JsonProperty("first_team_name")
    private String teamAName;
    @JsonProperty("second_team_name")
    private String teamBName;
    @JsonProperty("matches_stats")
    List<MatchStats> matchStats;
    @JsonProperty("winner")
    String winningTeam;
    @JsonProperty("wins_ratio")
    String winsRatio;

    public SeriesStats(int noOfMatches, int overs, String teamAName, String teamBName, List<MatchStats> matchStats,
                       String winningTeam, String winsRatio){
        this.noOfMatches = noOfMatches;
        this.overs = overs;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.matchStats = matchStats;
        this.winsRatio = winsRatio;
        this.winningTeam = winningTeam;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public void setNoOfMatches(int noOfMatches) {
        this.noOfMatches = noOfMatches;
    }

    public void setMatchStats(List<MatchStats> matchStats) {
        this.matchStats = matchStats;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public void setWinsRatio(String winsRatio) {
        this.winsRatio = winsRatio;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public int getOvers() {
        return overs;
    }

    public int getNoOfMatches() {
        return noOfMatches;
    }

    public List<MatchStats> getMatchStats() {
        return matchStats;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public String getWinsRatio() {
        return winsRatio;
    }
}
