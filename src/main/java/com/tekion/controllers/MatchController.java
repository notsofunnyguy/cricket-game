package com.tekion.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tekion.helpers.ConvertObjects;
import com.tekion.models.*;
import com.tekion.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @RequestMapping(value = "/api/cricket/v1/matches/{id}", method = RequestMethod.GET)
    public ResponseEntity<MatchStats> getMatchById(@PathVariable("id") int id) {
        Match match = matchRepository.findById(id);
        if (match != null) {
            return new ResponseEntity<>(ConvertObjects.getMatchStatsFromMatch(match), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/cricket/v1/series/{id}", method = RequestMethod.GET)
    public ResponseEntity<SeriesStats> getMatchesBySeriesId(@PathVariable("id") int id) throws SQLException {
        List<Match> matches = matchRepository.findMatchesBySeriesId(id);
        SeriesStats seriesStats = ConvertObjects.getSeriesStatsFromMatches(matches, id);
        if (seriesStats != null) {
            return new ResponseEntity<>(seriesStats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/cricket/v1/play/series", method = RequestMethod.POST)
    public ResponseEntity<SeriesStats> playSeries(@RequestBody Series series) {
        try {
            GameController.preGameSetUp(series);
            ArrayList<Match> matches = matchRepository.getLastPlayedNMatches(series.getNoOfMatches());
            return new ResponseEntity<>(ConvertObjects.getSeriesStatsFromMatches(matches, GameController.seriesId), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/cricket/v1/play/match", method = RequestMethod.POST)
    public ResponseEntity<MatchStats> playMatch(@RequestBody SingleMatch singleMatch) {
        try {
            Series series = new Series(1,singleMatch.getOvers(),singleMatch.getTeamAName(),singleMatch.getTeamBName());
            GameController.preGameSetUp(series);
            ArrayList<Match> match = matchRepository.getLastPlayedNMatches(series.getNoOfMatches());
            return new ResponseEntity<>(ConvertObjects.getMatchStatsFromMatch(match.get(0)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
