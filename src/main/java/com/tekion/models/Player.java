package com.tekion.models;
import com.tekion.enums.BowlerStatus;
import com.tekion.enums.PlayerType;
import com.tekion.enums.BatsmanStatus;

import java.util.*;


public class Player{


    private String name;
    private int id;
    private int teamId;
    private int matchesPlayed;
    private PlayerType playerType;
    private int runsScored;
    int fifties;
    int hundreds;
    private int fours;
    private int sixes;
    private int ballsPlayed;
    private int wickets;
    private int runsConceeded;
    private int ballsBowled;
    private int outBy;
    private String wicketType;
    private ArrayList<Integer> wicketsOf;
    private int subordinateId;
    private BatsmanStatus batsmanStatus;
    private BowlerStatus bowlerStatus;

    Player(){

    }

    /*

    Constructor initialising the players.
     */
    public Player(String name, int id,  int teamId){
        this.name = name;
        this.teamId = teamId;
        this.id = id;
        this.batsmanStatus =  BatsmanStatus.YET_TO_BAT;
        this.bowlerStatus = BowlerStatus.CANBOWLNEXT;
        wicketsOf = new ArrayList<>();
        this.subordinateId = -1;
    }

    public Player(int id, String name, int teamId, String playerType, int matchesPlayed, int runsScored, int ballsPlayed
    , int fifties, int hundreds, int fours, int sixes, int wickets, int runsConceeded, int ballsBowled ){
        this.fifties = fifties;
        this.ballsBowled = ballsBowled;
        this.ballsPlayed = ballsPlayed;
        this.hundreds = hundreds;
        this.id = id;
        this.runsScored = runsScored;
        this.sixes = sixes;
        this.fours = fours;
        this.teamId = teamId;
        this.runsConceeded = runsConceeded;
        this.name = name;
        this.matchesPlayed = matchesPlayed;
        this.wickets = wickets;
        this.setPlayerType(playerType);
    }

    /*

    this method resets the player values
    after every match.
     */
    public void Reset(){
        this.batsmanStatus =  BatsmanStatus.YET_TO_BAT;
        this.bowlerStatus = BowlerStatus.CANBOWLNEXT;
        this.runsScored = this.wickets = this.ballsPlayed = this.ballsBowled = this.fours = this.sixes = this.runsConceeded = 0;
        wicketsOf.clear();
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public int getFifties() {
        return fifties;
    }

    public int getHundreds() {
        return hundreds;
    }


    public int getFours() {
        return fours;
    }

    public int getSixes() {
        return sixes;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public int getWickets() {
        return wickets;
    }

    public int getRunsConceeded() {
        return runsConceeded;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }
    public int getOutBy(int id) {
        return outBy;
    }

    public String getWicketType() {
        return wicketType;
    }

    public int getSubordinateId() {
        return subordinateId;
    }

    public void setSubordinateId(int subordinateId) {
        this.subordinateId = subordinateId;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

//    public ArrayList<Integer> getWicketsOf() {
//        return wicketsOf;
//    }





    public void setId(int id) {
        this.id = id;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public void setFifties(int fifties) {
        this.fifties = fifties;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public void setHundreds(int hundreds) {
        this.hundreds = hundreds;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setRunsConceeded(int runsConceeded) {
        this.runsConceeded = runsConceeded;
    }


    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public void setOutBy(int outBy){
        this.outBy = outBy;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }

    public int getOutBy() {
        return outBy;
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

    /*

    this method updates the player's scores.
     */
    public void updateBatsman(int runs){
        this.runsScored += runs%7;
        this.ballsPlayed++;
        if(runs==4) this.fours++;
        else if(runs==6) this.sixes++;
    }

    /*

    this method updates the bowlers attributes.
     */
    public void updateBowler(int runs){
        this.runsConceeded += runs%7;
        this.ballsBowled++;
    }

    public static void updateWickets(Player batsman, Player bowler){
        bowler.wickets++;
        batsman.setOutBy(bowler.getId());
        bowler.wicketsOf.add(batsman.getId());
    }

}

