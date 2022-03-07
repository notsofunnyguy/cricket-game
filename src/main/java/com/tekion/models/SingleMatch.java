package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleMatch {
    int overs;
    @JsonProperty("first_team_name")
    String TeamAName;
    @JsonProperty("second_team_name")
    String TeamBName;

    public int getOvers() {
        return overs;
    }

    public String getTeamAName() {
        return TeamAName;
    }

    public String getTeamBName() {
        return TeamBName;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public void setTeamAName(String teamAName) {
        TeamAName = teamAName;
    }

    public void setTeamBName(String teamBName) {
        TeamBName = teamBName;
    }
}
