package com.tekion.controllers;

import com.tekion.models.*;
import com.tekion.repository.DbUpdates;
import com.tekion.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/match/{id}")
    public ResponseEntity<MatchStats> getMatchById(@PathVariable("id") int id) {
        Match match = matchRepository.findById(id);
        TeamStats teamAStats = new TeamStats(match.getTeamAId(),match.getTeamAName(),match.getTeamARuns(), match.getTeamAWickets(), match.getTeamAOversPlayed());
        TeamStats teamBStats = new TeamStats(match.getTeamBId(),match.getTeamBName(),match.getTeamBRuns(), match.getTeamBWickets(), match.getTeamBOversPlayed());
        MatchStats matchStats = new MatchStats(match.getId(), teamAStats, teamBStats, match.getOvers(), match.getTossWinningTeamId(), match.getWinningTeamId());
        if (match != null) {
            return new ResponseEntity<>(matchStats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<SeriesStats> getMatchesBySeriesId(@PathVariable("id") int id) throws SQLException {
        List<Match> matches = matchRepository.findMatchesBySeriesId(id);
        List<MatchStats> matchStats = new ArrayList<>();
        for(Match match:matches){
            TeamStats teamAStats = new TeamStats(match.getTeamAId(),match.getTeamAName(),match.getTeamARuns(), match.getTeamAWickets(), match.getTeamAOversPlayed());
            TeamStats teamBStats = new TeamStats(match.getTeamBId(),match.getTeamBName(),match.getTeamBRuns(), match.getTeamBWickets(), match.getTeamBOversPlayed());
            MatchStats matchStat = new MatchStats(match.getId(), teamAStats, teamBStats, match.getOvers(), match.getTossWinningTeamId(), match.getWinningTeamId());
            matchStats.add(matchStat);
        }
        int overs = matches.get(0).getOvers();
        System.out.println("ncc");
        SeriesStats seriesStats = new SeriesStats(matchStats.size(), overs, matches.get(0).getTeamAName(), matches.get(0).getTeamBName(),
        matchStats, DbUpdates.getSeriesWinningTeamName(id), DbUpdates.getSeriesWinsRatio(id));
        if (seriesStats != null) {
            return new ResponseEntity<>(seriesStats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/play/series")
    public ResponseEntity<SeriesStats> playSeries(@RequestBody Series series) {
        try {
            GameController.preGameSetUp(series);
            ArrayList<Match> matches = matchRepository.getLastPlayedNMatches(series.getNoOfMatches());
            ArrayList<MatchStats> matchStats = new ArrayList<>();
            for(Match match:matches){
                System.out.println(match.getTeamAName());
                TeamStats teamAStats = new TeamStats(match.getTeamAId(),match.getTeamAName(),match.getTeamARuns(), match.getTeamAWickets(), match.getTeamAOversPlayed());
                TeamStats teamBStats = new TeamStats(match.getTeamBId(),match.getTeamBName(),match.getTeamBRuns(), match.getTeamBWickets(), match.getTeamBOversPlayed());
                MatchStats matchStat = new MatchStats(match.getId(), teamAStats, teamBStats, match.getOvers(), match.getTossWinningTeamId(), match.getWinningTeamId());
                matchStats.add(matchStat);
            }
            int overs = matches.get(0).getOvers();
            SeriesStats seriesStats = new SeriesStats(matchStats.size(), overs, matches.get(0).getTeamAName(), matches.get(0).getTeamBName(),
                    matchStats, DbUpdates.getSeriesWinningTeamName(GameController.seriesId), DbUpdates.getSeriesWinsRatio(GameController.seriesId));
            return new ResponseEntity<>(seriesStats, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("jbsascjb");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/play/match")
    public ResponseEntity<MatchStats> playMatch(@RequestBody SingleMatch singleMatch) {
        try {
            Series series = new Series(1,singleMatch.getOvers(),singleMatch.getTeamAName(),singleMatch.getTeamBName());
            GameController.preGameSetUp(series);
            ArrayList<Match> matchs = matchRepository.getLastPlayedNMatches(series.getNoOfMatches());
            Match match = matchs.get(0);
            TeamStats teamAStats = new TeamStats(match.getTeamAId(),match.getTeamAName(),match.getTeamARuns(), match.getTeamAWickets(), match.getTeamAOversPlayed());
            TeamStats teamBStats = new TeamStats(match.getTeamBId(),match.getTeamBName(),match.getTeamBRuns(), match.getTeamBWickets(), match.getTeamBOversPlayed());
            MatchStats matchStats = new MatchStats(match.getId(), teamAStats, teamBStats, match.getOvers(), match.getTossWinningTeamId(), match.getWinningTeamId());
            return new ResponseEntity<>(matchStats, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
