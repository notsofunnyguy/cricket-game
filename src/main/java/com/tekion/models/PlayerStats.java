package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStats {
    public String name;

    @JsonProperty("matches_count")
    public int matchesPlayed;

    @JsonProperty("batting_stats")
    public BattingStatsOfPlayer battingStatsOfPlayer;

    @JsonProperty("bowling_stats")
    public BowlingStatsOfPlayer bowlingStatsOfPlayer;

    public PlayerStats(String name, int matchesPlayed, BattingStatsOfPlayer battingStatsOfPlayer, BowlingStatsOfPlayer bowlingStatsOfPlayer){
        this.name = name;
        this.matchesPlayed = matchesPlayed;
        this.battingStatsOfPlayer = battingStatsOfPlayer;
        this.bowlingStatsOfPlayer = bowlingStatsOfPlayer;
    }
}
