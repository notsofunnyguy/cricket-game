package com.tekion.controllers;

import com.tekion.enums.MatchType;
import com.tekion.helpers.ConvertObjects;
import com.tekion.models.*;
import com.tekion.repository.JdbcMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("")
@CacheConfig(cacheNames = {"cricket"})
public class MatchController {

    @Autowired
    private JdbcMatchRepository matchRepository;


    /*

    This method is responsible for sending stats
    of a match as a response to a get request.
    containing the match id.
     */
    @RequestMapping(value = "/api/cricket/v1/matches/single/{id}", method = RequestMethod.GET)
    @Cacheable()
    public ResponseEntity<MatchStats> getMatchById(@PathVariable("id") int id) {
        Match match = matchRepository.findById(id);
        if (match != null) {
            return new ResponseEntity<>(ConvertObjects.getMatchStatsFromMatch(match), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*

    This method is responsible for sending stats
    of a series as a response to a get request
    containing the series id.
     */
    @RequestMapping(value = "/api/cricket/v1/matches/series/{id}", method = RequestMethod.GET)
    @Cacheable()
    public ResponseEntity<SeriesStats> getMatchesBySeriesId(@PathVariable("id") int id) throws SQLException {
        List<Match> matches = matchRepository.findMatchesBySeriesId(id);
        SeriesStats seriesStats = ConvertObjects.getSeriesStatsFromMatches(matches, id);
        if (seriesStats != null) {
            return new ResponseEntity<>(seriesStats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*

    This method is responsible for playing a
    match/series and sending the stats of a
    match/series as a response to a post request
    containing the required data about the
    match/series to be played.
     */
    @RequestMapping(value = "/api/cricket/v1/matches/{matchType}", method = RequestMethod.POST)
    @Cacheable()
    public ResponseEntity<Map<String, Object>> playMatch(@RequestBody MatchData matchData, @PathVariable("matchType") MatchType matchType, @RequestParam(name="number_of_matches",required = false, defaultValue = "1") int totalGames) {
        try {
            if(matchType.compareTo(MatchType.SINGLEMATCH)==0) totalGames = 1;
            GameController.preGameSetUp(matchData, totalGames);
            ArrayList<Match> matches = matchRepository.getLastPlayedNMatches(totalGames);
            Map<String, Object> res = new LinkedHashMap<>();
            if(totalGames==1)
                res.put(String.valueOf(matchType), ConvertObjects.getMatchStatsFromMatch(matches.get(0)));
            else  res.put(String.valueOf(matchType), ConvertObjects.getSeriesStatsFromMatches(matches, GameController.seriesId));
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
