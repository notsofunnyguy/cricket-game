package com.tekion.controllers;

import java.io.PrintStream;
import java.sql.*;

import com.tekion.CricketGame;

import com.tekion.constants.StringUtils;
import com.tekion.models.Series;
import com.tekion.repository.DbUpdates;
import com.tekion.models.Team;
import com.tekion.services.MatchService;

import static com.tekion.helpers.DisplayHelper.displayResult;


public class GameController {
    public static int matchId;
    public static int totalOvers;
    public static int seriesId;
    /*
    28-02-2022

    This method preGameSetUp called by main method
    is used for setting up the game by taking
    required inputs(team names, overs and no
    of matched to be played) and creating some
    objects(teams) and at last calling for
    method playGame to play this game.
     */
    public static void preGameSetUp(Series series) throws SQLException {
        Team A = new Team(series.getTeamAName());

        Team B = new Team(series.getTeamBName());

        totalOvers = series.getOvers();
        System.out.println(series.getNoOfMatches());

        playGame(A, B, series.getNoOfMatches());
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
    @params     totalOvers      stores the total overs that this game have.
     */
    public static void playGame(Team A, Team B, int noOfMatches) throws SQLException {

        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        Statement st = conn.createStatement();
        String sql = "select count(*) from match_stats";

        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int matchesAlreadyBeenPlayed = rs.getInt(1);

        if(noOfMatches>1) {
            System.out.println(noOfMatches);
            int sm = matchesAlreadyBeenPlayed + 1;
            int em = matchesAlreadyBeenPlayed + noOfMatches;
            sql = "select max(id) from series";
            rs = stmt.executeQuery(sql);
            rs.next();
            seriesId = rs.getInt(1);
            seriesId++;
            sql = "insert into series( start_match_id, end_match_id) values(" +
                    sm + "," +
                    em + ")";
            System.out.println(sql);
            st.executeUpdate(sql);
            System.out.println(sm + " " + em);
        }



        for (int matchNum = matchesAlreadyBeenPlayed+1; matchNum <= matchesAlreadyBeenPlayed+noOfMatches; matchNum++) {
            System.out.println(matchNum);
            matchId = matchNum;
            DbUpdates.initialiseDBs(A, B);
            Team winningTeam = MatchService.playMatch(A, B);
            System.out.println(winningTeam.getTeamId());
            if(winningTeam!=null) {
                winningTeam.updateWins();
                DbUpdates.updateWinningTeamID(winningTeam.getTeamId());
            }
            A.resetTeam();
            B.resetTeam();
        }
        st.close();
        stmt.close();
        conn.close();
        displayResult(A, B, noOfMatches);
    }

}
