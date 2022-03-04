package com.tekion.controllers;

import com.tekion.models.*;
import com.tekion.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerStats> getPlayerById(@PathVariable("id") int id) {
        Player player = playerRepository.findById(id);
        BattingStatsOfPlayer battingStatsOfPlayer = new BattingStatsOfPlayer();
        battingStatsOfPlayer.setBallsPlayed(player.getBallsPlayed());
        battingStatsOfPlayer.setFifties(player.getFifties());
        battingStatsOfPlayer.setFours(player.getFours());
        battingStatsOfPlayer.setHundreds(player.getHundreds());
        battingStatsOfPlayer.setSixes(player.getSixes());
        battingStatsOfPlayer.setRunsScored(player.getRunsScored());

        BowlingStatsOfPlayer bowlingStatsOfPlayer = new BowlingStatsOfPlayer();
        bowlingStatsOfPlayer.setBallsBowled(player.getBallsBowled());
        bowlingStatsOfPlayer.setRunsConceeded(player.getRunsConceeded());
        bowlingStatsOfPlayer.setWickets(player.getWickets());

        PlayerStats res = new PlayerStats();

        res.name = player.getName();
        res.matchesPlayed = player.getMatchesPlayed();
        res.battingStatsOfPlayer = battingStatsOfPlayer;
        res.bowlingStatsOfPlayer = bowlingStatsOfPlayer;

        if (player != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players/{matchId}/{playerId}")
    public ResponseEntity<PlayerStatInSingleMatch> getPlayerInfoByMatchId(@PathVariable("matchId") int matchId, @PathVariable("playerId") int playerId) {
        Player player = playerRepository.PlayerInfoByMatchId(matchId, playerId);
        BattingStatsOfPlayerInSingleMatch battingStatsOfPlayer = new BattingStatsOfPlayerInSingleMatch();
        if(player == null) System.out.println("kat gya");
        System.out.println(player.getBallsPlayed());
        battingStatsOfPlayer.setBallsPlayed(player.getBallsPlayed());
        battingStatsOfPlayer.setFours(player.getFours());
        battingStatsOfPlayer.setSixes(player.getSixes());
        battingStatsOfPlayer.setRunsScored(player.getRunsScored());
        battingStatsOfPlayer.setWicketType(player.getWicketType());
        battingStatsOfPlayer.setSubordinateId(player.getSubordinateId());

        BowlingStatsOfPlayer bowlingStatsOfPlayer = new BowlingStatsOfPlayer();
        bowlingStatsOfPlayer.setBallsBowled(player.getBallsBowled());
        bowlingStatsOfPlayer.setRunsConceeded(player.getRunsConceeded());
        bowlingStatsOfPlayer.setWickets(player.getWickets());

        PlayerStatInSingleMatch res = new PlayerStatInSingleMatch();

        res.name = player.getName();
        res.battingStatsOfPlayer = battingStatsOfPlayer;
        res.bowlingStatsOfPlayer = bowlingStatsOfPlayer;

        if (player != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
