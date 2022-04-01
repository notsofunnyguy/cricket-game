package com.tekion.services;

import com.tekion.models.*;
import com.tekion.repository.JdbcPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private JdbcPlayerRepository playerRepository;


    /*

    It is used for converting object of Player
    to PlayerStats type.
     */
    public PlayerStats getPlayerStatsFromPlayer(Player player){
        BattingStats battingStats = new BattingStats(player.getRunsScored(), player.getFifties()
                , player.getHundreds(), player.getFours(),player.getSixes(), player.getBallsPlayed());
        BowlingStats bowlingStats = new BowlingStats(player.getWickets(),
                player.getRunsConceeded(), player.getBallsBowled());

        PlayerStats response = new PlayerStats.Builder(player.getName(),player.getMatchesPlayed())
                .battingStatsOfPlayer(battingStats)
                .bowlingStatsOfPlayer(bowlingStats)
                .build();

        return response;
    }

    /*

    It is used for converting object of Player
    to PlayerStatsInSingleMatch type.
     */
    public PlayerStatsInSingleMatch getPlayerStatsFromPlayerOfSingleMatch(Player player){
        BattingStatsInSingleMatch battingStatsOfPlayer = new BattingStatsInSingleMatch(player.getRunsScored(),
                player.getFours(),player.getSixes(), player.getBallsPlayed(), player.getWicketType(), player.getSubordinateId());
        BowlingStats bowlingStats = new BowlingStats(player.getWickets(),
                player.getRunsConceeded(), player.getBallsBowled());
        PlayerStatsInSingleMatch response = new PlayerStatsInSingleMatch(player.getName(),battingStatsOfPlayer, bowlingStats);
        return response;
    }

    public PlayerStats getPlayer(int id){
        Player player = playerRepository.findById(id);
        PlayerStats playerStats = getPlayerStatsFromPlayer(player);
        return playerStats;
    }

    public PlayerStatsInSingleMatch getPlayerInfoByMatch(int matchId, int playerId) {
        Player player = playerRepository.PlayerInfoByMatchId(matchId, playerId);
        System.out.println(player.getName());
        PlayerStatsInSingleMatch playerStatsInSingleMatch = getPlayerStatsFromPlayerOfSingleMatch(player);
        return playerStatsInSingleMatch;
    }
}
