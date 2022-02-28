package com.company.models;

import com.company.CricketGame;
import com.company.interfaces.Observer;
import com.company.enums.BatsmanStatus;

import java.sql.*;
import java.util.*;

public class Team implements Observer {

    private String name;
    private ArrayList<Player> players;
    private int teamId;
    private int wins;
    private int runs;
    private int wickets;
    private int ballsPlayed;
    private int totalBalls;
    private ArrayList<Player> bowlers;
    private int nextBowlerIndex;
    private Queue<Integer> batsmanInBattingOrder;

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

    public int getTotalBalls() {
        return totalBalls;
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

    public Player getNextBatsman(){
        Player nextBatsman = players.get(batsmanInBattingOrder.poll());
        nextBatsman.setBatsmanStatus(BatsmanStatus.PLAYING);
        return nextBatsman;
    }

    public Player getNextBowler(){
        Player nextBowler = bowlers.get(nextBowlerIndex);
        nextBowlerIndex = (nextBowlerIndex + 1) % bowlers.size();
        return nextBowler;
    }

    public void setTotalBalls(int totalBalls) {
        this.totalBalls = totalBalls;
    }

    public void incWins() {
        this.wins++;
    }



    @Override
    public void updateTeam(int runs) throws SQLException {
        this.runs += runs%7;
        this.ballsPlayed++;
        if(runs==7){
            this.wickets++;
        }
    }

    @Override
    public void updateBowler(int run) throws SQLException {

    }

    @Override
    public void updateStriker(int run) throws SQLException {

    }


}
