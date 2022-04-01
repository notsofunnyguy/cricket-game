package com.tekion.services;



import com.tekion.controllers.GameController;
import com.tekion.enums.MatchType;
import com.tekion.helpers.Toss;
import com.tekion.models.*;
import com.tekion.constants.StringUtils;
import com.tekion.repository.*;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tekion.helpers.Toss.toss;

@Service
public class MatchService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private JdbcPlayerRepository playerRepository;

    @Autowired
    private JdbcMatchRepository matchRepository;
    /*
    28-02-2022

    This method playMatch called by playGame
    calling method conductInningsInOrder to
    conducts innings in the order.


    @params     A               first team playing in a match
    @params     B               second team playing in a match
    @return match winning team.
     */
    public static Team playMatch(Team A, Team B) throws SQLException {

        InningService inning = new InningService();
        if(toss()) {
            Toss.tossWinningTeamId = A.getTeamId();
            inning.conductInningsInOrder(A, B);
        }
        else {
            Toss.tossWinningTeamId = B.getTeamId();
            inning.conductInningsInOrder(B, A);
        }
        Team winningTeam = null;
        GameController.winningTeamId = -1;
        if(A.getRuns()>B.getRuns()){
            winningTeam =  A;
            GameController.winningTeamId = A.getTeamId();
        }
        else if(A.getRuns()<B.getRuns()){
            GameController.winningTeamId = B.getTeamId();
            winningTeam = B;
        }
        DbUpdates.updateMatchStatsDb(A, B);
        DbUpdates.updateScoreboard(A);
        DbUpdates.updateScoreboard(B);
        return winningTeam;

    }

    public SeriesStats getSeriesStatsFromMatches(List<Match> matches, int id) throws SQLException {
        ArrayList<MatchStats> matchStats = new ArrayList<>();
        for (Match match : matches) {
            matchStats.add(getMatchStatsFromMatch(match));
        }
        int overs = matches.get(0).getOvers();
        SeriesStats seriesStats = new SeriesStats(matchStats.size(), overs, matches.get(0).getTeamAName(), matches.get(0).getTeamBName(),
                matchStats, DbUpdates.getSeriesWinningTeamName(id), DbUpdates.getSeriesWinsRatio(id));
        return seriesStats;
    }

    public MatchStats getMatchStatsFromMatch(Match match) {

        List<Player> playerInSingleMatchForA = playerRepository.getAllPlayersOfTeam(match.getId(), match.getTeamAId());
        List<PlayerStatsInSingleMatch> playerStatsInSingleMatchForA = new ArrayList<>();
        for(Player player: playerInSingleMatchForA)
            playerStatsInSingleMatchForA.add(playerService.getPlayerStatsFromPlayerOfSingleMatch(player));

        List<Player> playerInSingleMatchForB = playerRepository.getAllPlayersOfTeam(match.getId(), match.getTeamBId());
        List<PlayerStatsInSingleMatch> playerStatsInSingleMatchForB = new ArrayList<>();
        for(Player player: playerInSingleMatchForB)
            playerStatsInSingleMatchForB.add(playerService.getPlayerStatsFromPlayerOfSingleMatch(player));

        MatchStats matchStats = getMatchStatsObjectFromMatchObject(match, playerStatsInSingleMatchForA,
                playerStatsInSingleMatchForB);
        return matchStats;
    }

    public Map<String, Object> startGame(Map<String, Object> gameResponse, MatchData matchData, MatchType matchType, int totalGames) throws SQLException {

        GameController.preGameSetUp(matchData, totalGames);
        ArrayList<Match> matches = matchRepository.getLastPlayedNMatches(totalGames);

        Rule singleMatchRule = new RuleBuilder()
                .name(StringUtils.GAME_TYPE_RULE)
                .description(StringUtils.SINGLE_MATCH_TYPE_GAME_DESCRIPTION)
                .when(facts -> (int)facts.get("number")==1)
                .then(facts -> gameResponse.put(String.valueOf(matchType), getMatchStatsFromMatch(matches.get(0))))
                .build();

        Rule seriesMatchesRule = new RuleBuilder()
                .name(StringUtils.GAME_TYPE_RULE)
                .description(StringUtils.SERIES_MATCHES_TYPE_GAME_DESCRIPTION)
                .when(facts -> (int)facts.get("number")>1)
                .then(facts -> gameResponse.put(String.valueOf(matchType), getSeriesStatsFromMatches(matches, GameController.seriesId)))
                .build();

        Facts facts = new Facts();
        facts.put("number", totalGames);

        Rules rules = new Rules();
        rules.register(singleMatchRule);
        rules.register(seriesMatchesRule);
        
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);
        rulesEngine.fire(rules, facts);

        return gameResponse;
    }

    public MatchStats getMatchStatsObjectFromMatchObject(Match match, List<PlayerStatsInSingleMatch> playerStatsInSingleMatchListForA, List<PlayerStatsInSingleMatch> playerStatsInSingleMatchListForB){
        TeamStats teamAStats = new TeamStats(match.getTeamAId(),match.getTeamAName(),match.getTeamARuns(), match.getTeamAWickets(), match.getTeamAOversPlayed());
        TeamStats teamBStats = new TeamStats(match.getTeamBId(),match.getTeamBName(),match.getTeamBRuns(), match.getTeamBWickets(), match.getTeamBOversPlayed());
        MatchStats matchStats = new MatchStats.Builder(match.getId(), match.getOvers())
                .statsOfTeamA(teamAStats)
                .statsOfTeamB(teamBStats)
                .statsOfPlayerOfTeamA(playerStatsInSingleMatchListForA)
                .statsOfPlayerOfTeamB(playerStatsInSingleMatchListForB)
                .winningTeam(match.getWinningTeamId())
                .tossWinningTeam(match.getTossWinningTeamId())
                .build();
        return matchStats;
    }

    public MatchStats getMatch(int id){
        Match match = matchRepository.findMatchById(id);
        MatchStats matchStats = getMatchStatsFromMatch(match);
        return matchStats;
    }

    public SeriesStats getSeries(int id) throws SQLException {
        List<Match> matches = matchRepository.findSeriesById(id);
        SeriesStats seriesStats = getSeriesStatsFromMatches(matches, id);
        return seriesStats;
    }
}
