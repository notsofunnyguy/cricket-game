package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/*

This class is a model to pass as a request body
to get required data about the match/series
we are about to play.
 */
public class MatchData {
    @JsonProperty("overs_count")
    private int overs;
    @JsonProperty("first_team_name")
    private String teamAName;
    @JsonProperty("second_team_name")
    private String teamBName;

    public MatchData(int noOfMatches, String teamAName, String teamBName){
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
