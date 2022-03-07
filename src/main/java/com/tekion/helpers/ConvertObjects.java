package com.tekion.helpers;

import com.tekion.controllers.GameController;
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
        System.out.println("everything fine");
        return seriesStats;
    }

    public static PlayerStats getPlayerStatsFromPlayer(Player player){
        BattingStatsOfPlayer battingStatsOfPlayer = new BattingStatsOfPlayer(player.getRunsScored(), player.getFifties()
        , player.getHundreds(), player.getFours(),player.getSixes(), player.getBallsPlayed());
        BowlingStatsOfPlayer bowlingStatsOfPlayer = new BowlingStatsOfPlayer(player.getWickets(),
                player.getRunsConceeded(), player.getBallsBowled());
        PlayerStats res = new PlayerStats(player.getName(),player.getMatchesPlayed(),battingStatsOfPlayer, bowlingStatsOfPlayer);
        return res;
    }

    public static PlayerStatInSingleMatch getPlayerStatsFromPlayerOfSingleMatch(Player player){
        BattingStatsOfPlayerInSingleMatch battingStatsOfPlayer = new BattingStatsOfPlayerInSingleMatch(player.getRunsScored(),
                player.getFours(),player.getSixes(), player.getBallsPlayed(), player.getWicketType(), player.getSubordinateId());
        BowlingStatsOfPlayer bowlingStatsOfPlayer = new BowlingStatsOfPlayer(player.getWickets(),
                player.getRunsConceeded(), player.getBallsBowled());
        PlayerStatInSingleMatch res = new PlayerStatInSingleMatch(player.getName(),battingStatsOfPlayer, bowlingStatsOfPlayer);
        return res;
    }
}
