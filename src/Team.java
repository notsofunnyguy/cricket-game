import java.util.ArrayList;
import java.util.ListIterator;

public class Team {

    private String name;
    private ArrayList<Player> players;
    private Player captain;
    private int Target;
    private int runs;
    private int wickets;
    private int ballsPlayed;
    private int totalBalls;

    public Team(String name){
        this.name = name;
        this.players = new ArrayList<>();
        Player player[] = new Player[11];
        Character ch ='a';
        for (int i = 0; i < 11; i++) {
            player[i] = new Player(String.valueOf(ch), i);
            player[i].setBatsmanStatus(statusOfBatsman.CanBatNext);
            player[i].setBowlerStatus(statusOfBowler.CanBowlNext);
            if(i<5) player[i].setPlayerType(typeOfPlayer.Batsman);
            else if(i==5) player[i].setPlayerType(typeOfPlayer.AllRounder);
            else player[i].setPlayerType(typeOfPlayer.Bowler);
            this.players.add(player[i]);
            ch++;
        }
    }

    public void resetTeam(){
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

    public int getTarget() {
        return Target;
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

    public void setTarget(int target) {
        this.Target = target;
    }

    public void setTotalBalls(int totalBalls) {
        this.totalBalls = totalBalls;
    }

    public void incBallsPlayed(Player player) {
        this.ballsPlayed += 1;
        player.incBallsPlayed();
    }

    public void incRuns(int runs){
        this.runs += runs;
    }

    public void incWickets(Player batsman, Player bowler){
        this.wickets+=1;
        bowler.incWickets(batsman);
        batsman.getOutBy(bowler.getJerseyNumber());
    }

    public Player getNextBatsman(){
        statusOfBatsman status = statusOfBatsman.CanBatNext;
        Player nextBatsman = null;
        for (Player player: this.players) {
            if(player.getBatsmanStatus().compareTo(status)==0){
                player.setBatsmanStatus(statusOfBatsman.Playing);
                nextBatsman = player;
                break;
            }
        }
        return nextBatsman;
    }

    public Player getNextBowler(){
        statusOfBowler status = statusOfBowler.CanBowlNext;
        Player nextBowler = null;
        ListIterator<Player> List_Iterator = players.listIterator(players.size());
        while (List_Iterator.hasPrevious()) {
            Player bowler = List_Iterator.previous();
            if(bowler.getBowlerStatus().compareTo(status)==0 && bowler.getBallsBowled() < this.getTotalBalls()/5){
                bowler.setBowlerStatus(statusOfBowler.Bowling);
                nextBowler = bowler;
                break;
            }
        }
        return nextBowler;
    }

}
