package com.tekion.models;

public class SingleMatch {
    int overs;
    String TeamAName;
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
