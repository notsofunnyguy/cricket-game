package com.tekion.repository;

import com.tekion.models.Player;
import com.tekion.models.PlayerStats;

import java.sql.SQLException;

public interface PlayerRepository {
    Player findById(int id) throws SQLException;
    Player findByName(String name);
    Player PlayerInfoByMatchId(int matchId, int playerId);
}
