package com.tekion.helpers;

import com.tekion.CricketGame;
import com.tekion.controllers.GameController;
import com.tekion.models.Player;
import com.tekion.models.Team;

import java.sql.*;

public class DBUpdatesHelperClass {

    /*

    This method will update the winning team id
    after every match.
     */
    public static void updateWinningTeamID(int winningTeamId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "UPDATE match_stats " +
                "SET winning_team_id = " + winningTeamId +  " WHERE id = " + GameController.matchId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /*

    This method will initialise the scoreboard
    by creating records for players of teams playing.
     */
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
                    GameController.matchId + ", " +
                    teamId + ", " +
                    playerId + ", '" + rs.getString("name") + "', " +
                    "0, 0, 0, 0, 0, 0, 0)";
            stmt1.executeUpdate(sql);
        }
        stmt1.close();
        stmt.close();
        conn.close();
    }

    /*

    This method will initialise the match stats
    by creating record for current match
    with both teams info.
     */
    public static void initialiseMatchStatsTable(Team A, Team B, int overs) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into match_stats(id, team_a_id, team_b_id, " +
                "team_a_score, team_b_score, overs, team_a_balls_played, team_b_balls_played, team_a_wickets, team_b_wickets) values("  +
                GameController.matchId + "," +
                A.getTeamId() + "," +
                B.getTeamId() + "," +
                "0, 0, " + overs + ", 0, 0, 0, 0)";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /*

    This is a helper method which calls
    the other methods to initialise and
    update their DBs
     */
    public static void initialiseDBs(Team a, Team b, int totalOvers) throws SQLException {
        DBUpdatesHelperClass.initialiseMatchStatsTable(a, b, totalOvers);
        DBUpdatesHelperClass.initialiseScoreboardForTeam(a.getTeamId());
        DBUpdatesHelperClass.initialiseScoreboardForTeam(b.getTeamId());
    }

    /*

    This method updates the match stats
    in DB with team details.
     */
    public static void updateMatchStatsDb(Team team) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select team_a_id, team_b_id from match_stats where id =" + GameController.matchId;
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int runs = team.getRuns();
        int ballsPlayed = team.getBallsPlayed();
        int wickets = team.getWickets();
        if(rs.getInt("team_a_id") == team.getTeamId()){
            sql = "update match_stats set team_a_score = " +
                    runs + ",team_a_balls_played = " + ballsPlayed +
                    ", team_a_wickets = " + wickets + " where id = " + GameController.matchId;
        }else {
            sql = "update match_stats set team_b_score = " +
                    runs + ", team_b_balls_played = " + ballsPlayed +
                    ", team_b_wickets = " + wickets + " where id = " + GameController.matchId;
        }
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /*

    This method updates the batting stats columns
    of the scoreboard.
     */
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
                id + " and team_id = " + teamId + " and match_id = " + GameController.matchId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /*

    This method updates the bowling stats columns
    of the scoreboard.
     */
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
                id + " and team_id = " + teamId + " and match_id = " + GameController.matchId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /*

    This is helper method to execute the
    update query by passing the query.
     */
    public static void executeUpdateQuery(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

}
