package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStatInSingleMatch {
    public String name;
    @JsonProperty("batting_stats")
    public BattingStatsOfPlayerInSingleMatch battingStatsOfPlayer;
    @JsonProperty("bowling_stats")
    public BowlingStatsOfPlayer bowlingStatsOfPlayer;

    public PlayerStatInSingleMatch(String name, BattingStatsOfPlayerInSingleMatch battingStatsOfPlayer, BowlingStatsOfPlayer bowlingStatsOfPlayer){
        this.name = name;
        this.battingStatsOfPlayer = battingStatsOfPlayer;
        this.bowlingStatsOfPlayer = bowlingStatsOfPlayer;
    }
}
