package com.tekion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/*

This class is a model to store
stats of a player. we send object
of this model when asked for
stats of a player.
 */
public class PlayerStats {
    public String name;

    @JsonProperty("matches_count")
    public int matchesPlayed;

    @JsonProperty("batting_stats")
    public BattingStats battingStats;

    @JsonProperty("bowling_stats")
    public BowlingStats bowlingStats;

    public PlayerStats(Builder builder) {
        this.name = builder.name;
        this.bowlingStats = builder.bowlingStats;
        this.battingStats = builder.battingStats;
        this.matchesPlayed = builder.matchesPlayed;
    }

    public static class Builder{
        private String name;
        private int matchesPlayed;
        private BowlingStats bowlingStats;
        private BattingStats battingStats;

        public Builder(String name, int numberOfMatchesPlayed){
            this.name = name;
            this.matchesPlayed = numberOfMatchesPlayed;
        }

        public Builder battingStatsOfPlayer(BattingStats battingStat){
            battingStats = battingStat;
            return this;
        }

        public Builder bowlingStatsOfPlayer(BowlingStats bowlingStat){
            bowlingStats = bowlingStat;
            return this;
        }

        public PlayerStats build() {
            return new PlayerStats(this);
        }
    }

    public PlayerStats(String name, int matchesPlayed, BattingStats battingStats, BowlingStats bowlingStats){
        this.name = name;
        this.matchesPlayed = matchesPlayed;
        this.battingStats = battingStats;
        this.bowlingStats = bowlingStats;
    }
}
