package com.tekion.controllers;

import java.sql.*;

import com.tekion.CricketGame;

import com.tekion.models.Data;
import com.tekion.models.Team;
import com.tekion.services.MatchService;

import static com.tekion.helpers.DisplayHelper.updateResult;


public class GameController {
    public static int matchId;
    public static int totalOvers;
    public static int seriesId;
    public static int tossWinningTeamId;
    public static int winningTeamId;
    /*
    28-02-2022

    This method preGameSetUp called by main method
    is used for setting up the game by taking
    required inputs(team names, overs and no
    of matched to be played) and creating some
    objects(teams) and at last calling for
    method playGame to play this game.
     */
    public static void preGameSetUp(Data data, int noOfMatches) throws SQLException {
        Team A = new Team(data.getTeamAName());

        Team B = new Team(data.getTeamBName());

        totalOvers = data.getOvers();

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
    @params     totalOvers      stores the total overs that this game have.
     */
    public static void playGame(Team A, Team B, int noOfMatches) throws SQLException {

        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        Statement st = conn.createStatement();
        String sql = "select max(id) from match_stats";

        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int matchesAlreadyBeenPlayed = rs.getInt(1);

        if(noOfMatches>1) {
            int sm = matchesAlreadyBeenPlayed + 1;
            int em = matchesAlreadyBeenPlayed + noOfMatches;
            sql = "insert into series( start_match_id, end_match_id) values(" +
                    sm + "," +
                    em + ")";
            int cnt = st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if(cnt==1) {
                rs = stmt.getGeneratedKeys();
                rs.next();
                seriesId = rs.getInt(1);
            }
        }

        for (int matchNum = matchesAlreadyBeenPlayed+1; matchNum <= matchesAlreadyBeenPlayed+noOfMatches; matchNum++) {
            Team winningTeam = MatchService.playMatch(A, B);
            if(winningTeam!=null) {
                winningTeam.updateWins();
            }
            A.resetTeam();
            B.resetTeam();
        }
        st.close();
        stmt.close();
        conn.close();
        updateResult(A, B, noOfMatches);
    }

}
