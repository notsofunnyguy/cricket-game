package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Series {
    @JsonProperty("matches_count")
    private int noOfMatches;
    @JsonProperty("overs_count")
    private int overs;
    @JsonProperty("first_team_name")
    private String teamAName;
    @JsonProperty("second_team_name")
    private String teamBName;

    public Series(int noOfMatches, int overs, String teamAName, String teamBName){
        this.noOfMatches = noOfMatches;
        this.overs = overs;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
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
}
