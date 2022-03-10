package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStats {
    public String name;

    @JsonProperty("matches_count")
    public int matchesPlayed;

    @JsonProperty("batting_stats")
    public BattingStats battingStats;

    @JsonProperty("bowling_stats")
    public BowlingStats bowlingStats;

    public PlayerStats(String name, int matchesPlayed, BattingStats battingStats, BowlingStats bowlingStats){
        this.name = name;
        this.matchesPlayed = matchesPlayed;
        this.battingStats = battingStats;
        this.bowlingStats = bowlingStats;
    }
}
