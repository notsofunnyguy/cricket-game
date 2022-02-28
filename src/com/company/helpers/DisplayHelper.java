package com.company.helpers;

import com.company.CricketGame;
import com.company.controllers.GameController;
import com.company.models.Team;

import java.sql.*;

public abstract class DisplayHelper {

    public static void displayScoreboard(Team A, Team B) throws SQLException {

        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();

        System.out.println();
        System.out.println("Team Batting :" + A.getName() + "                  Team Bowling :" + B.getName());
        System.out.println("Runs : " + A.getRuns() + "                  Wickets : " + A.getWickets()+ "                  Overs: " + A.getBallsPlayed()/6 + "."  + A.getBallsPlayed()%6);
        System.out.println();
        System.out.println("Batting Scorecard\n");
        String format = "%1$-20s%2$-20s%3$-20s%4$-20s%5$-20s\n";
        System.out.format(format, "Player", "Runs","Balls","Fours","Sixes");
        String sql = "select player_name , runs_scored, balls_played, fours, sixes from scoreboard " +
                "where match_id =" + GameController.getMatchId() + " and team_id = " + A.getTeamId();
        ResultSet rs = stmt.executeQuery(sql);

        String name;
        int runs, balls, fours, sixes, wickets;
        while(rs.next()){
            name = rs.getString("player_name");
            runs = rs.getInt("runs_scored");
            balls = rs.getInt("balls_played");
            fours = rs.getInt("fours");
            sixes = rs.getInt("sixes");
            System.out.format(format,name,runs,balls,fours,sixes);
        }

        System.out.println();

        format = "%1$-20s%2$-20s%3$-20s%4$-20s\n";

        System.out.println("Bowling Scoreboard\n");

        System.out.format(format, "Player", "Balls", "Runs", "Wickets");
        sql = "select player_name , runs_conceeded, balls_bowled, wickets from scoreboard " +
                "where match_id =" + GameController.getMatchId() + " and team_id = " + B.getTeamId() + " and balls_bowled > 0";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            name = rs.getString("player_name");
            runs = rs.getInt("runs_conceeded");
            balls = rs.getInt("balls_bowled");
            wickets = rs.getInt("wickets");
            System.out.format(format, name, balls, runs, wickets);
        }
        System.out.println();
        stmt.close();
        conn.close();
    }

    public static void displayResult(int indiaWins, int pakistanWins, int noOfMatches){
        String typeOfGame;
        if(noOfMatches==1){
            typeOfGame ="Match";
        }
        else{
            typeOfGame ="Series";
        }

        if(indiaWins >noOfMatches/2) {
            System.out.print("India wins the " + typeOfGame + ". ");
            if(noOfMatches>1){
                System.out.println("By: " + indiaWins + ":" + pakistanWins);
            }
        }else if(pakistanWins >noOfMatches/2) {
            System.out.print("Pakistan wins the " + typeOfGame + ". ");
            if(noOfMatches>1){
                System.out.println("By: " + pakistanWins + ":" + indiaWins);
            }
        }else {
            System.out.println(typeOfGame + " Draw.");
        }
        System.out.println();
    }

}
