package com.tekion.controllers;

import java.sql.*;
import com.tekion.models.MatchData;
import com.tekion.models.Team;
import com.tekion.repository.DbUpdates;
import com.tekion.services.MatchService;

import static com.tekion.helpers.ResultHelper.updateResult;


public class GameController {
    public static int matchId;
    public static int totalOvers;
    public static int seriesId;
    public static int winningTeamId;

    /*
    28-02-2022

    This method preGameSetUp called by main method
    is used for setting up the game by taking
    required inputs(matchData and no
    of matched to be played) and creating some
    objects(teams) and at last calling for
    method playGame to play this game.

    @params     matchData       an object which stores the required data about the match to be played.
    @params     noOfMatches     no. of matches to be played(1 for single and n for series).
     */
    public static void preGameSetUp(MatchData matchData, int noOfMatches) throws SQLException {
        Team A = new Team(matchData.getTeamAName());
        Team B = new Team(matchData.getTeamBName());
        totalOvers = matchData.getOvers();
        playGame(A, B, noOfMatches);
    }

    /*
    28-02-2022

    This method playGame called by preGameSetUp method
    is calling for methods like
    playMatch() to play a match, initialiseDBs() to
    initialise the DB's(Scoreboard, match) with
    teams and players details.

    @params     A               first team playing in a game.
    @params     B               second team playing in a game.
    @params     noOfMatches     stores the no. of matches to be played
     */
    public static void playGame(Team A, Team B, int noOfMatches) throws SQLException {

        int matchesAlreadyBeenPlayed = DbUpdates.getNumberOfMatchesAlreadyBeenPlayed();
        if(noOfMatches>1) seriesId = DbUpdates.initSeriesTable(matchesAlreadyBeenPlayed, noOfMatches);
        for (matchId = matchesAlreadyBeenPlayed+1; matchId <= matchesAlreadyBeenPlayed+noOfMatches; matchId++) {
            Team winningTeam = MatchService.playMatch(A, B);
            if(winningTeam!=null) {
                winningTeam.updateWins();
            }
            A.resetTeam();
            B.resetTeam();
        }
        if(noOfMatches>1)
            updateResult(A, B, noOfMatches);

    }

}
