package com.tekion.controllers;

import com.tekion.models.*;
import com.tekion.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/api/cricket/v1")
@CacheConfig(cacheNames = {"cricket"})
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    /*

    This method is responsible for sending stats
    of a player as a response to a get request
    containing the series id.
     */
    @GetMapping("/players/{id}")
    @Cacheable()
    public ResponseEntity<PlayerStats> getPlayerStatById(@PathVariable("id") int id) throws SQLException {
        PlayerStats playerStats = playerService.getPlayer(id);
        return new ResponseEntity<>(playerStats, HttpStatus.OK);
    }


    /*

    This method is responsible for sending stats
    of a player of a single match as a response
    to a get request containing the match id and
    player id.
     */
    @GetMapping("/matches/{matchId}/player-id/{playerId}")
    @Cacheable()
    public ResponseEntity<PlayerStatsInSingleMatch> getPlayerInfoByMatchId(@PathVariable("matchId") int matchId, @PathVariable("playerId") int playerId) {
        PlayerStatsInSingleMatch playerStatsInSingleMatch = playerService.getPlayerInfoByMatch(matchId, playerId);
        return new ResponseEntity<>(playerStatsInSingleMatch, HttpStatus.OK);
    }

}
