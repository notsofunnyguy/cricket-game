package com.tekion.controllers;

import com.tekion.helpers.ConvertObjects;
import com.tekion.models.*;
import com.tekion.repository.JdbcPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/api/cricket/v1/")
@CacheConfig(cacheNames = {"cricket"})
public class PlayerController {

    @Autowired
    JdbcPlayerRepository playerRepository;

    /*

    This method is responsible for sending stats
    of a player as a response to a get request
    containing the series id.
     */
    @GetMapping("players/{id}")
    @Cacheable()
    public ResponseEntity<PlayerStats> getPlayerById(@PathVariable("id") int id) throws SQLException {
        Player player = playerRepository.findById(id);
        PlayerStats playerStats = ConvertObjects.getPlayerStatsFromPlayer(player);
        if (playerStats != null) {
            return new ResponseEntity<>(playerStats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /*

    This method is responsible for sending stats
    of a player of a single match as a response
    to a get request containing the match id and
    player id.
     */
    @GetMapping("matches/{matchId}/player-id/{playerId}")
    @Cacheable()
    public ResponseEntity<PlayerStatsInSingleMatch> getPlayerInfoByMatchId(@PathVariable("matchId") int matchId, @PathVariable("playerId") int playerId) {
        Player player = playerRepository.PlayerInfoByMatchId(matchId, playerId);
        PlayerStatsInSingleMatch playerStatsInSingleMatch = ConvertObjects.getPlayerStatsFromPlayerOfSingleMatch(player);
        if (playerStatsInSingleMatch != null) {
            return new ResponseEntity<>(playerStatsInSingleMatch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
