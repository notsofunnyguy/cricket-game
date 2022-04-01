package com.tekion.repository;

import com.tekion.models.Player;
import com.tekion.models.PlayerStats;
import com.tekion.models.PlayerStatsInSingleMatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlayerRepository {
    Player findById(int id) throws SQLException;
    Player findByName(String name);
    Player PlayerInfoByMatchId(int matchId, int playerId);
    List<Player> getAllPlayersOfTeam(int matchId, int teamId);
}
