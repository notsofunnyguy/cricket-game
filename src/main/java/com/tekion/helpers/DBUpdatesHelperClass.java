package main.java.com.tekion.helpers;

import main.java.com.tekion.CricketGame;
import main.java.com.tekion.controllers.GameController;
import main.java.com.tekion.enums.Innings;
import main.java.com.tekion.models.Player;
import main.java.com.tekion.models.Team;


import java.sql.*;

public class DBUpdatesHelperClass {

    /*

    This method will update the winning team id
    after every match.
     */
    public static void updateWinningTeamID(int winningTeamId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "UPDATE matches " +
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
    public static void initialiseMatchesTable(int teamAId, int teamBId) throws SQLException {
        int firstInningId = GameController.currentInningId+1;
        int secondInningId = firstInningId+1;
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into matches(id, team_a_id, team_b_id, " +
                "overs) values("  +
                GameController.matchId + "," +
                teamAId + ", " +
                teamBId + ", " +
                GameController.totalOvers + " )";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void updateInningsIdOnMatchesTable(Innings inningType) throws SQLException {
        String inning;
        if(inningType.compareTo(Innings.FIRSTINNING)==0) inning = "first";
        else inning = "second";
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "update matches set " +
                inning + "_inning_id = "  +
                GameController.currentInningId + " where id = " + GameController.matchId;
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
        DBUpdatesHelperClass.initialiseMatchesTable(firstTeamId, secondTeamId);
        DBUpdatesHelperClass.initialiseScoreboardForTeam(firstTeamId);
        DBUpdatesHelperClass.initialiseScoreboardForTeam(secondTeamId);
    }

    public static void initialiseInningsDB(int battingTeamId, int bowlingTeamId) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into innings(batting_team_id, bowling_team_id, " +
                "runs, overs, wickets) values(" +
                battingTeamId + "," +
                bowlingTeamId + "," +
                " 0, 0, 0 ) ";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }


    /*

    This method updates the match stats
    in DB with team details.
     */
    public static void updateInningsDb(Team battingTeam) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();

        int runs = battingTeam.getRuns();
        int balls = battingTeam.getBallsPlayed();
        float overs = (float) ((int)(balls/6) + 0.1*(balls%6));
        int wickets = battingTeam.getWickets();
        System.out.println(runs + " " + overs + " " + wickets + " " + GameController.currentInningId);
        String sql = "update innings set runs = " +
                runs + ",overs = " + overs +
                ", wickets = " + wickets + " where id = " + GameController.currentInningId;
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

    public static void updateBallTable(int ballNo, int batsmanId, int bowlerId, int runs, String outType) throws SQLException {
        int wicket = 1;
        if(outType.compareTo(" ")==0) wicket = 0;
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into ball_updates values(" +
                GameController.matchId + ", " +
                GameController.currentInningId+ ", " +
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
        int runsScored = player.getRuns();
        int runsConceeded = player.getRunsConceeded();
        int hundreds = 0, fifties = 0;
        if(runsScored>=100) hundreds++;
        else if(runsScored>=50) fifties++;
        int wickets = player.getWickets();
        int ballsPlayed = player.getBallsPlayed();
        int ballsBowled = player.getBallsBowled();
//        System.out.println(runsScored);
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "update players set " +
                "matches_played = matches_played + " + matchesPlayed +
                ", runs_scored = runs_scored + " + runsScored +
                ", balls_bowled = balls_bowled + "+ ballsBowled +
                ", balls_played = balls_played + "+ ballsPlayed +
                ", wickets = wickets + " + wickets +
                ", fifties = fifties + "+ fifties +
                ", hundreds = hundreds + "+ hundreds +
                ", runs_conceeded = runs_conceeded + "+ runsConceeded +
                " where id = " + player.getJerseyNumber();
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

}
