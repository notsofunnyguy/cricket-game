package com.company.models;

import com.company.controllers.Observer;
import com.company.enums.StatusOfBatsman;
import com.company.enums.StatusOfBowler;
import com.company.enums.TypeOfPlayer;

import java.util.*;

public class Team implements Observer {

    private String name;
    private ArrayList<Player> players;
    private Player captain;
    private int runs;
    private int wickets;
    private int ballsPlayed;
    private int totalBalls;
    private HashSet<Player> activeBowlers;

    public Team(String name){
        this.name = name;
        this.players = new ArrayList<>();

        Player player[] = new Player[11];
        Character ch ='a';

        this.activeBowlers = new HashSet<> ();

        for (int i = 0; i < 11; i++) {
            player[i] = new Player(String.valueOf(ch), i);
            player[i].setBatsmanStatus(StatusOfBatsman.CANBATNEXT);
            player[i].setBowlerStatus(StatusOfBowler.CANBOWLNEXT);
            if(i<5) player[i].setPlayerType(TypeOfPlayer.BATSMAN);
            else player[i].setPlayerType(TypeOfPlayer.BOWLER);
            this.players.add(player[i]);
            ch++;
        }

    }

    public void resetTeam(){
        this.activeBowlers.clear();
        this.runs = this.wickets = this.ballsPlayed = 0;
        for (Player player:players) {
            player.Reset();
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    public void setTotalBalls(int totalBalls) {
        this.totalBalls = totalBalls;
    }

    public void incBallsPlayed() {
        this.ballsPlayed += 1;
    }

    public void incRuns(int runs){
        this.runs += runs;
    }

    public void incWickets(){
        this.wickets+=1;
    }

    public Player getNextBatsman(){

        StatusOfBatsman status = StatusOfBatsman.CANBATNEXT;
        Player nextBatsman = null;

        for (Player player: this.players) {
            if(player.getBatsmanStatus().compareTo(status)==0){
                player.setBatsmanStatus(StatusOfBatsman.PLAYING);
                nextBatsman = player;
                break;
            }
        }

        return nextBatsman;
    }

    public Player getNextBowler(){

        StatusOfBowler status = StatusOfBowler.CANBOWLNEXT;
        Player nextBowler = null;
        ListIterator<Player> List_Iterator = players.listIterator(players.size());

        while (List_Iterator.hasPrevious()) {
            Player bowler = List_Iterator.previous();
            if(bowler.getBowlerStatus().compareTo(status)==0 && bowler.getBallsBowled() < this.getTotalBalls()/5){
                bowler.setBowlerStatus(StatusOfBowler.BOWLING);
                nextBowler = bowler;
                break;
            }
        }

        activeBowlers.add(nextBowler);
        return nextBowler;
    }

    public Set<Player> getActiveBowlers(){
        return activeBowlers;
    }

    @Override
    public void update(int runs, int idx) {
        if(idx==3) {
            this.incRuns(runs);
            this.incBallsPlayed();
            if (runs == 7)
                this.incWickets();
        }
    }

}
