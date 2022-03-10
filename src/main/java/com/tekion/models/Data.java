package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    @JsonProperty("overs_count")
    private int overs;
    @JsonProperty("first_team_name")
    private String teamAName;
    @JsonProperty("second_team_name")
    private String teamBName;

    public Data(int noOfMatches, String teamAName, String teamBName){
        this.overs = noOfMatches;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }


    public void setOvers(int noOfMatches) {
        this.overs = noOfMatches;
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
}
