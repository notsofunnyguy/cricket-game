package com.tekion.models;

import java.util.List;

public class SeriesStats {
    private int noOfMatches;
    private int overs;
    private String teamAName;
    private String teamBName;
    List<MatchStats> matchStats;
    String winningTeam;
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
