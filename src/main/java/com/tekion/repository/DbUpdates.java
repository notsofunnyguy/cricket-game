package com.tekion.repository;

import com.tekion.controllers.GameController;
import com.tekion.models.Player;
import com.tekion.models.Team;
import com.tekion.CricketGame;


import java.sql.*;
import java.util.ArrayList;

public class DbUpdates {

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

    public static void updateTossWinningTeamID(int tossWinningTeamId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "UPDATE match_stats " +
                "SET toss_winning_team_id = " + tossWinningTeamId +  " WHERE id = " + GameController.matchId;
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
                    "balls_bowled, runs_conceeded, wickets, subordinate_id) values(" +
                    GameController.matchId + ", " +
                    teamId + ", " +
                    playerId + ", '" + rs.getString("name") + "', " +
                    "0, 0, 0, 0, 0, 0, 0, -1)";
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
    public static void initialiseMatchStatsTable(int teamAId, int teamBId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into match_stats(id, team_a_id, team_b_id, " +
                "team_a_runs, team_b_runs, overs, team_a_overs_played, team_b_overs_played, team_a_wickets, team_b_wickets) values("  +
                GameController.matchId + "," +
                teamAId + "," +
                teamBId + "," +
                "0, 0, " + GameController.totalOvers + ", 0, 0, 0, 0)";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }


    /*

    This is a helper method which calls
    the other methods to initialise and
    update their DBs
     */
    public static void initialiseDBs(Team a, Team b) throws SQLException {
        int firstTeamId = a.getTeamId();
        int secondTeamId = b.getTeamId();
        DbUpdates.initialiseMatchStatsTable(firstTeamId, secondTeamId);
        DbUpdates.initialiseScoreboardForTeam(firstTeamId);
        DbUpdates.initialiseScoreboardForTeam(secondTeamId);
    }


    /*

    This method updates the batting stats columns
    of the scoreboard.
     */
    public static void updateBatsmanOnScoreboardDb(Player batsman) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        int runs = batsman.getRunsScored();
        int balls = batsman.getBallsPlayed();
        int fours = batsman.getFours();
        int sixes = batsman.getSixes();
        int id = batsman.getId();
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
        int id = bowler.getId();
        int teamId = bowler.getTeamId();
        String sql = "update scoreboard set runs_conceeded = " + runs +
                ", balls_bowled = "+ balls +
                ", wickets = " + wickets + " where player_id = " +
                id + " and team_id = " + teamId + " and match_id = " + GameController.matchId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void updateMatchStatsDb(Team team) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select team_a_id, team_b_id from match_stats where id =" + GameController.matchId;
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int runs = team.getRuns();
        int ballsPlayed = team.getBallsPlayed();
        float overs = (float) ((int)(ballsPlayed/6)+(float)(ballsPlayed%6)*0.1);
        int wickets = team.getWickets();
        if(rs.getInt("team_a_id") == team.getTeamId()){
            sql = "update match_stats set team_a_runs = " +
                    runs + ",team_a_overs_played = " + overs +
                    ", team_a_wickets = " + wickets + " where id = " + GameController.matchId;
        }else {
            sql = "update match_stats set team_b_runs = " +
                    runs + ", team_b_overs_played = " + overs +
                    ", team_b_wickets = " + wickets + " where id = " + GameController.matchId;
        }
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

    public static void updateBallTable(int ballNo, int battingTeamId, int bowlingTeamId, int batsmanId, int bowlerId, int runs, String outType) throws SQLException {
        int wicket = 1;
        if(outType.compareTo(" ")==0) wicket = 0;
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into ball_updates values(" +
                GameController.matchId + ", " +
                battingTeamId + ", " +
                bowlingTeamId + ", " +
                ballNo + ", " +
                batsmanId + ", " +
                bowlerId + ", " +
                runs + ", " +
                wicket + ", '" +
                outType + "', " +
                bowlerId + ")";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void updatePlayersStats(Player player) throws SQLException {
        int matchesPlayed = 1;
        int runsScored = player.getRunsScored();
        int runsConceeded = player.getRunsConceeded();
        int hundreds = 0, fifties = 0;
        if(runsScored>=100) hundreds++;
        else if(runsScored>=50) fifties++;
        int wickets = player.getWickets();
        int ballsPlayed = player.getBallsPlayed();
        int ballsBowled = player.getBallsBowled();
        int fours = player.getFours();
        int sixes = player.getSixes();
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "update players set " +
                "matches_played = matches_played + " + matchesPlayed +
                ", runs_scored = runs_scored + " + runsScored +
                ", balls_played = balls_played + "+ ballsPlayed +
                ", fifties = fifties + "+ fifties +
                ", hundreds = hundreds + "+ hundreds +
                ", fours = fours + "+ fours +
                ", sixes = sixes + "+ sixes +
                ", wickets = wickets + " + wickets +
                ", balls_bowled = balls_bowled + "+ ballsBowled +
                ", runs_conceeded = runs_conceeded + "+ runsConceeded +
                " where id = " + player.getId();
        stmt.executeUpdate(sql);
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
        System.out.println(name+  " "  + ratio + " " + GameController.seriesId);
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
        System.out.println(id);
        String sql = "select winning_team_name from series where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        String res=rs.getString(1);
        stmt.close();
        conn.close();
        return res;
    }

    public static String getSeriesWinsRatio(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select wins_ratio from series where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        String res=rs.getString(1);
        stmt.close();
        conn.close();
        return res;
    }
}
