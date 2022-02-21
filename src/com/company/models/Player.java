package com.company.models;

import com.company.controllers.Observer;
import com.company.enums.StatusOfBatsman;
import com.company.enums.StatusOfBowler;
import com.company.enums.TypeOfPlayer;

import java.util.*;


public class Player implements Observer {


    private String name;
    private int jerseyNumber;
    private TypeOfPlayer playerType;
    private int runsScored;
    private int runsConceeded;
    private int fours;
    private int sixes;
    private int wickets;
    private int ballsPlayed;
    private int ballsBowled;
    private int outBy;
    private ArrayList<Integer> wicketsOf;
    private StatusOfBatsman batsmanStatus;
    private StatusOfBowler bowlerStatus;

    public Player(String name, int jerseyNumber){
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        this.batsmanStatus =  StatusOfBatsman.CANBATNEXT;
        this.bowlerStatus = StatusOfBowler.CANBOWLNEXT;
        wicketsOf = new ArrayList<>();
    }

    public void Reset(){
        this.batsmanStatus =  StatusOfBatsman.CANBATNEXT;
        this.bowlerStatus = StatusOfBowler.CANBOWLNEXT;
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

    public TypeOfPlayer getPlayerType(){
        return playerType;
    }

    public StatusOfBatsman getBatsmanStatus(){
        return batsmanStatus;
    }

    public StatusOfBowler getBowlerStatus(){
        return bowlerStatus;
    }

    public void incRunsScored(int runs) {
        this.runsScored += runs;
        if(runs==4) this.incFours();
        if(runs==6) this.incSixes();
    }

    public void incRunsConceeded(int runs) {
        this.runsConceeded += runs;
    }

    public void incWickets(){
        this.wickets += 1;
//        this.tookWicketOf(batsman.getJerseyNumber());
    }

    public void incBallsPlayed() {
        this.ballsPlayed += 1;
    }

    public void incBallsBowled() {
        this.ballsBowled += 1;
    }

    public void incFours() {
        this.fours += 1;
    }

    public void incSixes() {
        this.sixes += 1;
    }

    public void setOutBy(int outBy){
        this.outBy = outBy;
    }

    public void tookWicketOf(int wicketsOf){
        this.wicketsOf.add(wicketsOf);
    }

    public void setPlayerType(TypeOfPlayer playerType) {
        this.playerType = playerType;
    }

    public void setBatsmanStatus( StatusOfBatsman batsmanStatus) {
        this.batsmanStatus = batsmanStatus;
    }

    public void setBowlerStatus(StatusOfBowler bowlerStatus) {
        this.bowlerStatus = bowlerStatus;
    }

    @Override
    public void update(int runs, int idx) {
        if(idx==0){
            this.incRunsScored(runs);
            this.incBallsPlayed();
        }else if(idx==2){
            this.incRunsConceeded(runs);
            this.incBallsBowled();
            if(runs==7)
                this.incWickets();
        }
    }

}

