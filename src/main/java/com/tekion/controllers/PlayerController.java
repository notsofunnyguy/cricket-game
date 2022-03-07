package com.tekion.controllers;

import com.tekion.helpers.ConvertObjects;
import com.tekion.models.*;
import com.tekion.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping(value = "/api/cricket/v1/players/{id}", method = RequestMethod.GET)
    public ResponseEntity<PlayerStats> getPlayerById(@PathVariable("id") int id) {
        Player player = playerRepository.findById(id);
        if (player != null) {
            return new ResponseEntity<>(ConvertObjects.getPlayerStatsFromPlayer(player), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/cricket/v1/matches/{matchId}/player-id/{playerId}", method = RequestMethod.GET)
    public ResponseEntity<PlayerStatInSingleMatch> getPlayerInfoByMatchId(@PathVariable("matchId") int matchId, @PathVariable("playerId") int playerId) {
        Player player = playerRepository.PlayerInfoByMatchId(matchId, playerId);
        if (player != null) {
            return new ResponseEntity<>(ConvertObjects.getPlayerStatsFromPlayerOfSingleMatch(player), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
