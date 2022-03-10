package com.tekion.repository;

import com.tekion.controllers.GameController;
import com.tekion.models.Player;
import com.tekion.models.Team;
import com.tekion.CricketGame;


import java.sql.*;
import java.util.ArrayList;

public class DbUpdates {

    public static void updateMatchStatsDb(Team A, Team B) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        int ballsPlayed = A.getBallsPlayed();
        float oversPlayedByTeamA = (float) ((int)(ballsPlayed/6)+(float)(ballsPlayed%6)*0.1);
        ballsPlayed = B.getBallsPlayed();
        float oversPlayedByTeamB = (float) ((int)(ballsPlayed/6)+(float)(ballsPlayed%6)*0.1);
        String sql = "insert into match_stats(team_a_id, team_b_id, " +
                "team_a_runs, team_b_runs, overs, team_a_overs_played, team_b_overs_played, team_a_wickets, team_b_wickets, winning_team_id" +
                ", toss_winning_team_id) values("  +
                A.getTeamId() + "," +
                B.getTeamId() + "," +
                A.getRuns() + "," +
                B.getRuns() + "," +
                GameController.totalOvers + "," +
                oversPlayedByTeamA + "," +
                oversPlayedByTeamB + "," +
                A.getWickets() + "," +
                B.getWickets() + "," +
                GameController.winningTeamId + "," +
                GameController.tossWinningTeamId + ")";
        int cnt = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if(cnt==1) {
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            GameController.matchId = rs.getInt(1);
        }
        stmt.close();
        conn.close();
    }


    public static ArrayList<Integer> getLastPlayedNMatchesIds(int n) throws SQLException {
        ArrayList<Integer> lastPlayedNMatchesIds = new ArrayList<>();
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select id from match_stats order by id desc limit " + n;
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            lastPlayedNMatchesIds.add(rs.getInt(1));
        }
        stmt.close();
        conn.close();
        return lastPlayedNMatchesIds;
    }

    public static void updateSeriesTable(String name, String ratio) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "update series set winning_team_name = '" + name + "', wins_ratio = '"  + ratio + "' where id = " + GameController.seriesId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static String getSeriesWinningTeamName(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select winning_team_name from series where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        String res = "DRAW";
        while(rs.next())
            res = rs.getString(1);
        stmt.close();
        conn.close();
        return res;
    }

    public static String getSeriesWinsRatio(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select wins_ratio from series where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        String res = "";
        while(rs.next())
            res=rs.getString(1);
        stmt.close();
        conn.close();
        return res;
    }

    public static void updateScoreboard(Team team) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        ArrayList<Player> players = team.getPlayers();

        for(Player player:players) {
            String sql = "insert into scoreboard(match_id, team_id, player_id, player_name, " +
                "runs_scored, balls_played, fours, sixes, " +
                "balls_bowled, runs_conceeded, wickets, wicket_type, subordinate_id) values(" +
                GameController.matchId + ", " +
                player.getTeamId() + ", " +
                player.getId() + ", '" + player.getName() + "', " +
                player.getRunsScored() + " , " +
                player.getBallsPlayed() + ", " +
                player.getFours() + ", " +
                player.getSixes() + ", " +
                player.getBallsBowled() + ", " +
                player.getRunsConceeded() + ", " +
                player.getWickets() + ", '" +
                player.getWicketType() + "', " +
                player.getSubordinateId() + ")";
            stmt.executeUpdate(sql);
            sql = "update players set " +
                    "matches_played = matches_played + " + player.getMatchesPlayed() +
                    ", runs_scored = runs_scored + " + player.getRunsScored() +
                    ", balls_played = balls_played + "+ player.getBallsPlayed() +
                    ", fifties = fifties + "+ player.getFifties() +
                    ", hundreds = hundreds + "+ player.getHundreds() +
                    ", fours = fours + "+ player.getFours() +
                    ", sixes = sixes + "+ player.getSixes() +
                    ", wickets = wickets + " + player.getWickets() +
                    ", balls_bowled = balls_bowled + "+ player.getBallsBowled() +
                    ", runs_conceeded = runs_conceeded + "+ player.getRunsConceeded() +
                    " where id = " + player.getId();
            stmt.executeUpdate(sql);
        }
        stmt.close();
        conn.close();
    }
}
