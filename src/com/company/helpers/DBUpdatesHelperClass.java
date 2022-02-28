package com.company.helpers;

import com.company.CricketGame;
import com.company.controllers.GameController;
import com.company.models.Player;
import com.company.models.Team;

import java.sql.*;

public class DBUpdatesHelperClass {

    public static void updateWinningTeamID(int winningTeamId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "UPDATE match_stats " +
                "SET winning_team_id = " + winningTeamId +  " WHERE id = " + GameController.getMatchId();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void initialiseScoreboardForTeam(int teamId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        Statement stmt1 = conn.createStatement();
        String sql = "select id,name from players where team_id = " + teamId;
        ResultSet rs = stmt.executeQuery(sql);
        int playerId;
        while (rs.next()){
            playerId = rs.getInt("id");
            sql = "insert into scoreboard(match_id, team_id, player_id, player_name, " +
                    "runs_scored, balls_played, fours, sixes, " +
                    "balls_bowled, runs_conceeded, wickets) values(" +
                    GameController.getMatchId() + ", " +
                    teamId + ", " +
                    playerId + ", '" + rs.getString("name") + "', " +
                    "0, 0, 0, 0, 0, 0, 0)";
            stmt1.executeUpdate(sql);
        }
        stmt1.close();
        stmt.close();
        conn.close();
    }

    public static void initialiseMatchStatsTable(Team A, Team B, int overs) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into match_stats(id, team_a_id, team_b_id, " +
                "team_a_score, team_b_score, overs, team_a_balls_played, team_b_balls_played, team_a_wickets, team_b_wickets) values("  +
                GameController.getMatchId() + "," +
                A.getTeamId() + "," +
                B.getTeamId() + "," +
                "0, 0, " + overs + ", 0, 0, 0, 0)";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void initialiseDBs(Team a, Team b, int totalOvers) throws SQLException {
        DBUpdatesHelperClass.initialiseMatchStatsTable(a, b, totalOvers);
        DBUpdatesHelperClass.initialiseScoreboardForTeam(a.getTeamId());
        DBUpdatesHelperClass.initialiseScoreboardForTeam(b.getTeamId());
    }

    public static void updateMatchStatsDb(Team team) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select team_a_id, team_b_id from match_stats where id =" + GameController.getMatchId();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int runs = team.getRuns();
        int ballsPlayed = team.getBallsPlayed();
        int wickets = team.getWickets();
        if(rs.getInt("team_a_id") == team.getTeamId()){
            sql = "update match_stats set team_a_score = " +
                    runs + ",team_a_balls_played = " + ballsPlayed +
                    ", team_a_wickets = " + wickets + " where id = " + GameController.getMatchId();
        }else {
            sql = "update match_stats set team_b_score = " +
                    runs + ", team_b_balls_played = " + ballsPlayed +
                    ", team_b_wickets = " + wickets + " where id = " + GameController.getMatchId();
        }
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void updateBatsmanOnScoreboardDb(Player batsman) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        int runs = batsman.getRuns();
        int balls = batsman.getBallsPlayed();
        int fours = batsman.getFours();
        int sixes = batsman.getSixes();
        int id = batsman.getJerseyNumber();
        int teamId = batsman.getTeamId();
        String sql = "update scoreboard set runs_scored = " + runs +
                ", balls_played = "+ balls +
                ", fours = " + fours + ", sixes = " + sixes + " where player_id = " +
                id + " and team_id = " + teamId + " and match_id = " + GameController.getMatchId();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void updateBowlerOnScoreboardDb(Player bowler) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        int runs = bowler.getRunsConceeded();
        int balls = bowler.getBallsBowled();
        int wickets = bowler.getWickets();
        int id = bowler.getJerseyNumber();
        int teamId = bowler.getTeamId();
        String sql = "update scoreboard set runs_conceeded = " + runs +
                ", balls_bowled = "+ balls +
                ", wickets = " + wickets + " where player_id = " +
                id + " and team_id = " + teamId + " and match_id = " + GameController.getMatchId();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void executeUpdateQuery(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

}
