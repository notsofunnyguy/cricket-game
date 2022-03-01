package main.java.com.tekion.models;
import com.tekion.enums.BowlerStatus;
import com.tekion.enums.PlayerType;
import main.java.com.tekion.enums.BatsmanStatus;

import java.util.*;


public class Player{


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

    /*

    Constructor initialising the players.
     */
    public Player(String name, int jerseyNumber,  int teamId){
        this.name = name;
        this.teamId = teamId;
        this.jerseyNumber = jerseyNumber;
        this.batsmanStatus =  BatsmanStatus.YET_TO_BAT;
        this.bowlerStatus = BowlerStatus.CANBOWLNEXT;
        wicketsOf = new ArrayList<>();
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
        batsman.getOutBy(bowler.getJerseyNumber());
        bowler.wicketsOf.add(batsman.getJerseyNumber());
    }

}

