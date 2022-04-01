package com.tekion.controllers;

import com.tekion.enums.MatchType;
import com.tekion.models.*;
import com.tekion.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;


@RestController
@RequestMapping("/api/cricket/v1/matches")
@CacheConfig(cacheNames = {"cricket"})
public class MatchController {

    @Autowired
    private MatchService matchService;


    /*

    This method is responsible for sending stats
    of a match as a response to a get request.
    containing the match id.
     */
    @RequestMapping(value = "/single/{id}", method = RequestMethod.GET)
    @Cacheable()
    public ResponseEntity<MatchStats> getMatchById(@PathVariable("id") int id) {
        MatchStats matchStats = matchService.getMatch(id);
        return new ResponseEntity<>(matchStats, HttpStatus.OK);
    }

    /*

    This method is responsible for sending stats
    of a series as a response to a get request
    containing the series id.
     */
    @RequestMapping(value = "/series/{id}", method = RequestMethod.GET)
    @Cacheable()
    public ResponseEntity<SeriesStats> getSeriesById(@PathVariable("id") int id) throws SQLException {
        SeriesStats seriesStats = matchService.getSeries(id);
        return new ResponseEntity<>(seriesStats, HttpStatus.OK);
    }

    /*

    This method is responsible for playing a
    match/series and sending the stats of a
    match/series as a response to a post request
    containing the required data about the
    match/series to be played.
     */
    @RequestMapping(value = "/{matchType}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> playGame(@RequestBody MatchData matchData, @PathVariable("matchType") MatchType matchType,
                                                        @RequestParam(name = "number_of_matches", required = false, defaultValue = "1") int totalGames) throws SQLException {
        Map<String, Object> res = matchService.startGame(new HashMap<>(), matchData, matchType, totalGames);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



}
