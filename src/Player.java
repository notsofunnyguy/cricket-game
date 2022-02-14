import java.util.ArrayList;


enum typeOfPlayer{
    Batsman,
    Bowler,
    AllRounder
}

enum statusOfBatsman{
    Out,
    Playing,
    CanBatNext;
}

enum statusOfBowler{
    Bowling,
    ReachedMaxOverLimit,
    CanBowlNext;
}

public class Player {


    private String name;
    private int jerseyNumber;
    private typeOfPlayer playerType;
    private int runsScored;
    private int runsConceeded;
    private int fours;
    private int sixes;
    private int wickets;
    private int ballsPlayed;
    private int ballsBowled;
    private int outBy;
    private ArrayList<Integer> wicketsOf;
    private statusOfBatsman batsmanStatus;
    private statusOfBowler bowlerStatus;

    public Player(String name, int jerseyNumber){
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        this.batsmanStatus = statusOfBatsman.CanBatNext;
        this.bowlerStatus = statusOfBowler.CanBowlNext;
        wicketsOf = new ArrayList<>();
    }

    public void Reset(){
        this.batsmanStatus = statusOfBatsman.CanBatNext;
        this.bowlerStatus = statusOfBowler.CanBowlNext;
        this.runsScored = this.wickets = this.ballsPlayed = this.ballsBowled = this.fours = this.sixes = 0;
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

    public typeOfPlayer getPlayerType(){
        return playerType;
    }

    public statusOfBatsman getBatsmanStatus(){
        return batsmanStatus;
    }

    public statusOfBowler getBowlerStatus(){
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

    public void incWickets(Player batsman){
        this.wickets += 1;
        this.tookWicketOf(batsman.getJerseyNumber());
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

    public void setPlayerType(typeOfPlayer playerType) {
        this.playerType = playerType;
    }

    public void setBatsmanStatus(statusOfBatsman batsmanStatus) {
        this.batsmanStatus = batsmanStatus;
    }

    public void setBowlerStatus(statusOfBowler bowlerStatus) {
        this.bowlerStatus = bowlerStatus;
    }
}

