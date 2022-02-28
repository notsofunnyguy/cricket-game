package com.tekion.models;

import com.tekion.CricketGame;
import com.tekion.controllers.GameController;
import com.tekion.enums.BowlerStatus;
import com.tekion.enums.BatsmanStatus;
import com.tekion.helpers.DBUpdatesHelperClass;

import java.sql.*;
import java.util.*;

public class Team{

    private String name;
    private ArrayList<Player> players;
    private int teamId;
    private int wins;
    private int runs;
    private int wickets;
    private int ballsPlayed;
    private ArrayList<Player> bowlers;
    private int nextBowlerIndex;
    private Queue<Integer> batsmanInBattingOrder;
    private Player striker;
    private Player nonStriker;
    private Player bowler;


    /*
    28-02-2022

    This is constructor used for initialising
    the team object.

    @params     name    team name
     */
    public Team(String name) throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = null;

        this.name = name;
        this.players = new ArrayList<>();

        this.bowlers = new ArrayList<> ();
        this.batsmanInBattingOrder = new LinkedList<>();
        sql = "select id from teams where name = '" + this.name + "'";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        this.teamId = rs.getInt("id");
        initialisePlayers();
        stmt.close();
        conn.close();
    }

    /*
    28-02-2022

    This method initialisePlayers initialises
    the players of the team.
     */
    private void initialisePlayers() throws SQLException {
        Connection conn = DriverManager.getConnection(CricketGame.DB_URL, CricketGame.USER, CricketGame.PASS);
        Statement stmt = conn.createStatement();
        String sql = "select id, name, player_type from players where team_id = " + teamId;
        ResultSet rs = stmt.executeQuery(sql);

        int i = 0;
        Player player;
        while(rs.next()) {
            player = new Player(rs.getString("name"), rs.getInt("id"), teamId);
            player.setPlayerType(rs.getString("player_type"));
            this.batsmanInBattingOrder.add(i);
            this.players.add(player);
            if((rs.getString("player_type")).compareTo("Batsman")!=0)
                bowlers.add(player);
            i++;
        }
        nextBowlerIndex = 0;
        stmt.close();
        conn.close();
    }

    /*
    28-02-2022

    This method resetTeam is used after every match
    to reset the data of the teams.
     */
    public void resetTeam(){
        batsmanInBattingOrder.clear();
        int i=0;
        while(i<11){
            batsmanInBattingOrder.add(i++);
        }
        this.runs = this.wickets = this.ballsPlayed = this.nextBowlerIndex = 0;
        for (Player player:players) {
            player.Reset();
        }
    }

    public int getTeamId() {
        return teamId;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public int getRuns() {
        return runs;
    }

    public int getWickets() {
        return wickets;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    /*
    28-02-2022

    This method getNextBatsman will return the next batsman
    after current batsman got out.

    @return     Player      next batsman
     */
    private Player getNextBatsman(){
        Player nextBatsman = players.get(batsmanInBattingOrder.poll());
        nextBatsman.setBatsmanStatus(BatsmanStatus.PLAYING);
        return nextBatsman;
    }

    /*
    28-02-2022

    This method will return the next bowler after
    every over.

    @return     Player      next bowler who gonna bowl the over.
     */
    private Player getNextBowler(){
        Player nextBowler = bowlers.get(nextBowlerIndex);
        nextBowlerIndex = (nextBowlerIndex + 1) % bowlers.size();
        return nextBowler;
    }

    /*
    28-02-2022

    This method will set the bowler
    and also set the status of current and
    previous bowler.
     */
    public void setBowler() {
        Player prevBowler =  bowler;
        bowler = this.getNextBowler();
        if(prevBowler.getBallsBowled() == (GameController.totalOvers*6)/5 ) prevBowler.setBowlerStatus(BowlerStatus.REACHEDMAXOVERLIMIT);
        prevBowler.setBowlerStatus(BowlerStatus.CANBOWLNEXT);
    }

    public void setStriker() {
        striker = this.getNextBatsman();
    }

    public void updateWins() {
        this.wins++;
    }


    public static void initPlayers(Team A,Team B){
        A.striker = A.getNextBatsman();
        A.nonStriker = A.getNextBatsman();
        B.bowler = B.getNextBowler();
    }

    /*
    28-02-2022

    This method swaps the striker and nonStriker
    when odd run happens or over change.
     */
    public void swapStrikerNonStriker(){
        Player temp =  striker;
        this.striker =  nonStriker;
        this.nonStriker = temp;
    }

    /*
    28-02-2022

    This method updates teams runs and
    balls played.

    @params     runs    recent ball score
     */
    public void updateBattingTeam(int runs){
        this.runs += runs%7;
        this.ballsPlayed++;
        striker.updateBatsman(runs);
    }

    /*
    28-02-2022

    This method calls the method to update
    the bowler data.

    @params     runs    recent ball score
     */
    public void updateBowlingTeam(int runs){
        this.bowler.updateBowler(runs);
    }

    /*
    28-02-2022

    This method calls the method to update the
    data/records when wicket falls.
     */
    public static void updateWickets(Team A, Team B, String wicketType) throws SQLException {
        A.wickets++;
        String sql = "update scoreboard set wicket_type = '" + wicketType + "' where match_id=" + GameController.matchId + " and player_id = " + A.striker.getJerseyNumber();
        DBUpdatesHelperClass.executeUpdateQuery(sql);
        Player.updateWickets(A.striker, B.bowler);
    }

    /*

    This method calls the methods to update
    the striker and nonStriker data on DB.
     */
    public void updateBattingScoreboard() throws SQLException {
        DBUpdatesHelperClass.updateBatsmanOnScoreboardDb(this.striker);
        DBUpdatesHelperClass.updateBatsmanOnScoreboardDb(this.nonStriker);
    }

    /*

    This method calls the methods to update
    the bowler data on DB.
     */
    public void updateBowlingScoreboard()  throws SQLException {
        DBUpdatesHelperClass.updateBowlerOnScoreboardDb(this.bowler);
    }

    /*

    This method displays the every ball
    details. which ball , striker and bowler and what
    happens in the recent ball.
     */
    public static void displayBallStats(Team A, Team B, int overs, int balls, int runs){
        if(runs<7)
            System.out.println(overs+"."+ (balls+1) + " : " + B.bowler.getName() + " to " + A.striker.getName() + "   " + runs + " runs");
        else
            System.out.println(overs+"."+ (balls+1) + " : " + B.bowler.getName() + " to " + A.striker.getName() + "   W");
    }

}
