package com.tekion.repository;

import com.tekion.controllers.GameController;
import com.tekion.helpers.Toss;
import com.tekion.models.Player;
import com.tekion.models.Team;
import java.sql.*;
import java.util.ArrayList;
import com.tekion.constants.StringUtils;


public class DbUpdates {

    /*

    This method is responsible for updating
    the match_stats table in our cricket DB.
     */
    public static void updateMatchStatsDb(Team A, Team B) throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
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
                Toss.tossWinningTeamId + ")";
        int cnt = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if(cnt==1) {
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            GameController.matchId = rs.getInt(1);
        }
        stmt.close();
        conn.close();
    }


    /*

    This method is responsible for getting
    ids' of last played n matches.
     */
    public static ArrayList<Integer> getLastPlayedNMatchesIds(int n) throws SQLException {
        ArrayList<Integer> lastPlayedNMatchesIds = new ArrayList<>();
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
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

    /*

    This method is responsible for updating the
    series_stats table with winning team id and
    toss winning team id.
     */
    public static void updateSeriesTable(String name, String ratio) throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
        Statement stmt = conn.createStatement();
        String sql = "update series set winning_team_name = '" + name + "', wins_ratio = '"  + ratio + "' where id = " + GameController.seriesId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /*

    This method is responsible for getting series
    winner team name from series_stats table.
     */
    public static String getSeriesWinningTeamName(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
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

    /*

    This method is responsible for getting series
    wins ratio (winningTeamWins:losingTeamWins) from
    series_stats table.
     */
    public static String getSeriesWinsRatio(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
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


    /*

    this method is used for updating
    player records of both team on the scoreboard.
     */
    public static void updateScoreboard(Team team) throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
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
            int fifty = 0;
            int hundred = 0;
            if(player.getRunsScored()>=100) hundred = 1;
            else if(player.getRunsScored()>=50) fifty = 1;
            sql = "update players set " +
                    "matches_played = matches_played + " + 1 +
                    ", runs_scored = runs_scored + " + player.getRunsScored() +
                    ", balls_played = balls_played + "+ player.getBallsPlayed() +
                    ", fifties = fifties + "+ fifty +
                    ", hundreds = hundreds + "+ hundred +
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

    /*

    this method returns the number of matches
    already been played to know the match of
    next playing match.
     */
    public static int getNumberOfMatchesAlreadyBeenPlayed() throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select max(id) from match_stats";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int res = rs.getInt(1);
        stmt.close();
        conn.close();
        return res;
    }

    /*

    this method is responsible for initialising
    the series_stats table when a series starts
    with the starting and ending match id.
     */
    public static int initSeriesTable(int matchesAlreadyBeenPlayed, int noOfMatches) throws SQLException {
        Connection conn = DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
        Statement st = conn.createStatement();
        int sm = matchesAlreadyBeenPlayed + 1;
        int em = matchesAlreadyBeenPlayed + noOfMatches;
        String sql = "insert into series( start_match_id, end_match_id) values(" +
            sm + "," +
            em + ")";
        st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        int res = rs.getInt(1);
        st.close();
        conn.close();
        return res;
    }
}
