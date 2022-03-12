package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;


/*

This class is a model to store the
info about the player in a single match.
 */
public class PlayerStatsInSingleMatch {
    public String name;
    @JsonProperty("batting_stats")
    public BattingStatsInSingleMatch battingStatsOfPlayer;
    @JsonProperty("bowling_stats")
    public BowlingStats bowlingStats;

    public PlayerStatsInSingleMatch(String name, BattingStatsInSingleMatch battingStatsOfPlayer, BowlingStats bowlingStats){
        this.name = name;
        this.battingStatsOfPlayer = battingStatsOfPlayer;
        this.bowlingStats = bowlingStats;
    }
}
