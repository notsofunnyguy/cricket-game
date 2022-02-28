package com.tekion.controllers;

import java.sql.*;
import java.util.Scanner;

import com.tekion.CricketGame;

import com.tekion.constants.StringUtils;
import com.tekion.helpers.DBUpdatesHelperClass;
import com.tekion.models.Team;
import com.tekion.services.MatchService;

import static com.tekion.helpers.DisplayHelper.displayResult;


public class GameController {
    public static int matchId;
    public static int totalOvers;
    /*
    28-02-2022

    This method preGameSetUp called by main method
    is used for setting up the game by taking
    required inputs(team names, overs and no
    of matched to be played) and creating some
    objects(teams) and at last calling for
    method playGame to play this game.
     */
    public static void preGameSetUp() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println(StringUtils.GET_FIRST_TEAM_NAME);
        String name = sc.nextLine();
        Team A = new Team(name);

        System.out.println(StringUtils.GET_SECOND_TEAM_NAME);
        name = sc.nextLine();
        Team B = new Team(name);

        System.out.println(StringUtils.MATCH_TYPE);
        int noOfMatches = sc.nextInt();

        System.out.println(StringUtils.GET_TOTAL_OVERS);
        totalOvers = sc.nextInt();

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
        String sql = "select count(*) from match_stats";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int matchesAlreadyBeenPlayed = rs.getInt(1);
        for (int matchNum = matchesAlreadyBeenPlayed+1; matchNum <= matchesAlreadyBeenPlayed+noOfMatches; matchNum++) {
            matchId = matchNum;
            DBUpdatesHelperClass.initialiseDBs(A,B,totalOvers);
            Team winningTeam = MatchService.playMatch(A, B, totalOvers);
            winningTeam.updateWins();
            DBUpdatesHelperClass.updateWinningTeamID(winningTeam.getTeamId());
            A.resetTeam();
            B.resetTeam();
        }
        stmt.close();
        conn.close();
        displayResult(A, B, noOfMatches);
    }

}
