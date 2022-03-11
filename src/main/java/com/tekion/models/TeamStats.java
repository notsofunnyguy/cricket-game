package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/*

this class stores the brief stats
of a team.
 */
public class TeamStats {
    int id;
    String name;
    int runs;
    int wickets;
    @JsonProperty("overs_played")
    float oversPlayed;

    TeamStats(){

    }

    public TeamStats(int id, String name, int runs, int wickets, float oversPlayed){
        this.id = id;
        this.name = name;
        this.runs = runs;
        this.wickets = wickets;
        this.oversPlayed = oversPlayed;
    }

    public int getWickets() {
        return wickets;
    }

    public int getId() {
        return id;
    }

    public int getRuns() {
        return runs;
    }

    public String getName() {
        return name;
    }

    public float getOversPlayed() {
        return oversPlayed;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public void setOversPlayed(float oversPlayed) {
        this.oversPlayed = oversPlayed;
    }
}
