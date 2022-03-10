package com.tekion.helpers;

import com.tekion.models.*;
import com.tekion.repository.DbUpdates;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConvertObjects {

    public static MatchStats getMatchStatsFromMatch(Match match){
        TeamStats teamAStats = new TeamStats(match.getTeamAId(),match.getTeamAName(),match.getTeamARuns(), match.getTeamAWickets(), match.getTeamAOversPlayed());
        TeamStats teamBStats = new TeamStats(match.getTeamBId(),match.getTeamBName(),match.getTeamBRuns(), match.getTeamBWickets(), match.getTeamBOversPlayed());
        MatchStats matchStats = new MatchStats(match.getId(), teamAStats, teamBStats, match.getOvers(), match.getTossWinningTeamId(), match.getWinningTeamId());
        return matchStats;
    }

    public static SeriesStats getSeriesStatsFromMatches(List<Match> matches, int id) throws SQLException {
        ArrayList<MatchStats> matchStats = new ArrayList<>();
        for(Match match:matches){
            matchStats.add(getMatchStatsFromMatch(match));
        }
        int overs = matches.get(0).getOvers();
        SeriesStats seriesStats = new SeriesStats(matchStats.size(), overs, matches.get(0).getTeamAName(), matches.get(0).getTeamBName(),
                matchStats, DbUpdates.getSeriesWinningTeamName(id), DbUpdates.getSeriesWinsRatio(id));
        return seriesStats;
    }

    public static PlayerStats getPlayerStatsFromPlayer(Player player){
        BattingStats battingStats = new BattingStats(player.getRunsScored(), player.getFifties()
        , player.getHundreds(), player.getFours(),player.getSixes(), player.getBallsPlayed());
        BowlingStats bowlingStats = new BowlingStats(player.getWickets(),
                player.getRunsConceeded(), player.getBallsBowled());
        PlayerStats res = new PlayerStats(player.getName(),player.getMatchesPlayed(), battingStats, bowlingStats);
        return res;
    }

    public static PlayerStatsInSingleMatch getPlayerStatsFromPlayerOfSingleMatch(Player player){
        BattingStatsInSingleMatch battingStatsOfPlayer = new BattingStatsInSingleMatch(player.getRunsScored(),
                player.getFours(),player.getSixes(), player.getBallsPlayed(), player.getWicketType(), player.getSubordinateId());
        BowlingStats bowlingStats = new BowlingStats(player.getWickets(),
                player.getRunsConceeded(), player.getBallsBowled());
        PlayerStatsInSingleMatch res = new PlayerStatsInSingleMatch(player.getName(),battingStatsOfPlayer, bowlingStats);
        return res;
    }
}
