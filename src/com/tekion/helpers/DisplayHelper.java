package com.tekion.helpers;

import com.tekion.CricketGame;
import com.tekion.constants.StringUtils;
import com.tekion.controllers.GameController;
import com.tekion.models.Team;

import java.sql.*;

public abstract class DisplayHelper {

    /*

    This method calls the methods
    to display the batting and bowling stats.
     */
    public static void displayScoreboard(Team A, Team B) throws SQLException {
        System.out.println();
        System.out.println("Team Batting :" + A.getName() + "                  Team Bowling :" + B.getName());
        System.out.println("Runs : " + A.getRuns() + "                  Wickets : " + A.getWickets()+ "                  Overs: " + A.getBallsPlayed()/6 + "."  + A.getBallsPlayed()%6);
        System.out.println();
        displayBattingScoreboard(A);
        displayBowlingScoreboard(B);
    }

    /*

    This method calls the methods
    to display the bowling stats.
     */
    private static void displayBowlingScoreboard(Team B) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        System.out.println("Bowling Scoreboard\n");

        System.out.format(StringUtils.FOUR_MEMBER_FORMAT, "Player", "Balls", "Runs", "Wickets");
        String sql = "select player_name , runs_conceeded, balls_bowled, wickets from scoreboard " +
                "where match_id =" + GameController.matchId + " and team_id = " + B.getTeamId() + " and balls_bowled > 0";
        ResultSet rs = stmt.executeQuery(sql);
        String name;
        int runs, balls, wickets;
        while(rs.next()){
            name = rs.getString("player_name");
            runs = rs.getInt("runs_conceeded");
            balls = rs.getInt("balls_bowled");
            wickets = rs.getInt("wickets");
            System.out.format(StringUtils.FOUR_MEMBER_FORMAT, name, balls, runs, wickets);
        }
        System.out.println();
        stmt.close();
        conn.close();
    }

    /*

    This method calls the methods
    to display the batting stats.
     */
    private static void displayBattingScoreboard(Team A) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();


        System.out.println(StringUtils.BATTING_SCOREBOARD);
        System.out.format(StringUtils.FIVE_MEMBER_FORMAT , "Player", "Runs","Balls","4s","6s");

        String sql = "select player_name , runs_scored, balls_played, fours, sixes from scoreboard " +
                "where match_id =" + GameController.matchId + " and team_id = " + A.getTeamId();
        ResultSet rs = stmt.executeQuery(sql);

        String name;
        int runs, balls, fours, sixes;
        while(rs.next()){
            name = rs.getString("player_name");
            runs = rs.getInt("runs_scored");
            balls = rs.getInt("balls_played");
            fours = rs.getInt("fours");
            sixes = rs.getInt("sixes");
            System.out.format(StringUtils.FIVE_MEMBER_FORMAT,name,runs,balls,fours,sixes);
        }
        System.out.println();
        stmt.close();
        conn.close();
    }

    /*

    This method displays the final
    result after match/series.
     */
    public static void displayResult(Team A, Team B, int noOfMatches){
        String typeOfGame;

        if(noOfMatches==1)
            typeOfGame ="Match";
        else
            typeOfGame ="Series";

        if(A.getWins() > noOfMatches/2)
            displayFinalResult(A, B, typeOfGame, noOfMatches);
        else if(B.getWins() > noOfMatches/2)
            displayFinalResult(B, A, typeOfGame, noOfMatches);
        else
            System.out.println(typeOfGame + " " + StringUtils.DRAW);

        System.out.println();
    }

    private static void displayFinalResult(Team winningTeam, Team losingTeam, String gameType, int noOfMatches){
        System.out.print(winningTeam.getName() + " wins the " + gameType + ". ");
        if(noOfMatches>1){
            System.out.println("By: " + winningTeam.getWins() + ":" + losingTeam.getWins());
        }
    }

}
