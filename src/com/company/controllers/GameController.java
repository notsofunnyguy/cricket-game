package com.company.controllers;

import java.sql.*;
import java.util.Scanner;

import com.company.CricketGame;

import com.company.helpers.DBUpdatesHelperClass;
import com.company.models.Team;
import static com.company.helpers.DisplayHelper.displayResult;


public class GameController {
    public static int matchId;

    public static void preGameSetUp() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter first team name:");
        String name = sc.nextLine();
        Team A = new Team(name);

        System.out.println("Enter second team name:");
        name = sc.nextLine();
        Team B = new Team(name);

        System.out.println("Type of match : enter 1 for Single Match enter number of matches to played in the Series");
        int noOfMatches = sc.nextInt();

        System.out.println("Enter total number of overs: ");
        int totalOvers = sc.nextInt();

        playGame(A, B, noOfMatches, totalOvers);

    }

    public static void playGame(Team A, Team B, int noOfMatches, int totalOvers) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        Statement stmt1 = conn.createStatement();

        String sql = "select count(*) from match_stats";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int matchesAlreadyBeenPlayed = rs.getInt(1);

        for (int matchNum = matchesAlreadyBeenPlayed+1; matchNum <= matchesAlreadyBeenPlayed+noOfMatches; matchNum++) {

            matchId = matchNum;
            DBUpdatesHelperClass.initialiseDBs(A,B,totalOvers);

            Team winningTeam = MatchController.playMatch(A, B, totalOvers);

            winningTeam.incWins();
            DBUpdatesHelperClass.updateWinningTeamID(winningTeam.getTeamId());

            A.resetTeam();
            B.resetTeam();

        }

        stmt.close();
        stmt1.close();
        conn.close();

        displayResult(A.getWins(), B.getWins(), noOfMatches);

    }

    public static int getMatchId() {
        return matchId;
    }
}
