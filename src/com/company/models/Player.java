package com.company.models;
import com.company.helpers.DBUpdatesHelperClass;
import com.company.interfaces.Observer;
import com.company.enums.BatsmanStatus;
import com.company.enums.BowlerStatus;
import com.company.enums.PlayerType;

import java.sql.SQLException;
import java.util.*;


public class Player implements Observer {


    private String name;
    private int jerseyNumber;
    private int teamId;
    private PlayerType playerType;
    private int runsScored;
    private int runsConceeded;
    private int fours;
    private int sixes;
    private int wickets;
    private int ballsPlayed;
    private int ballsBowled;
    private int outBy;
    private ArrayList<Integer> wicketsOf;
    private BatsmanStatus batsmanStatus;
    private BowlerStatus bowlerStatus;

    public Player(String name, int jerseyNumber,  int teamId){
        this.name = name;
        this.teamId = teamId;
        this.jerseyNumber = jerseyNumber;
        this.batsmanStatus =  BatsmanStatus.YET_TO_BAT;
        this.bowlerStatus = BowlerStatus.CANBOWLNEXT;
        wicketsOf = new ArrayList<>();
    }

    public void Reset(){
        this.batsmanStatus =  BatsmanStatus.YET_TO_BAT;
        this.bowlerStatus = BowlerStatus.CANBOWLNEXT;
        this.runsScored = this.wickets = this.ballsPlayed = this.ballsBowled = this.fours = this.sixes = this.runsConceeded = 0;
        wicketsOf.clear();
    }

    public String getName(){
        return name;
    }

    public int getJerseyNumber(){
        return jerseyNumber;
    }

    public int getRuns() {
        return runsScored;
    }

    public int getRunsConceeded() {
        return runsConceeded;
    }

    public int getFours() {
        return fours;
    }

    public int getSixes() {
        return sixes;
    }

    public int getWickets() {
        return wickets;
    }

    public int getOutBy(int jerseyNumber) {
        return outBy;
    }

    public ArrayList<Integer> getWicketsOf() {
        return wicketsOf;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setOutBy(int outBy){
        this.outBy = outBy;
    }

    public void tookWicketOf(int wicketsOf){
        this.wicketsOf.add(wicketsOf);
    }

    public void setPlayerType(String playerType) {
        if(playerType.compareTo("Batsman")==0)
            this.playerType = PlayerType.BATSMAN;
        else if(playerType.compareTo("Bowler")==0)
            this.playerType = PlayerType.BOWLER;
        else this.playerType = PlayerType.ALLROUNDER;
    }

    public void setBatsmanStatus( BatsmanStatus batsmanStatus) {
        this.batsmanStatus = batsmanStatus;
    }

    public void setBowlerStatus(BowlerStatus bowlerStatus) {
        this.bowlerStatus = bowlerStatus;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public void updateBowler(int runs) throws SQLException {
        this.runsConceeded += runs%7;
        this.ballsBowled++;
        if(runs==7) this.wickets++;
    }

    @Override
    public void updateStriker(int runs) throws SQLException {
        this.runsScored += runs%7;
        this.ballsPlayed++;
        if(runs==4) this.fours++;
        else if(runs==6) this.sixes++;
    }

    @Override
    public void updateTeam(int runs) throws SQLException {

    }

    public static void updateScoreboard(Player striker, Player nonStriker, Player bowler) throws SQLException {
        DBUpdatesHelperClass.updateBatsmanOnScoreboardDb(striker);
        DBUpdatesHelperClass.updateBatsmanOnScoreboardDb(nonStriker);
        DBUpdatesHelperClass.updateBowlerOnScoreboardDb(bowler);
    }

}

