package com.tekion.repository;

import com.tekion.models.Player;

public interface PlayerRepository {
    Player findById(int id);
    Player findByName(String name);
    Player PlayerInfoByMatchId(int matchId, int playerId);
}
