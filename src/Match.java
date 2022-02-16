

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
public class Match {

    static Player striker;
    static Player nonStriker;
    static Player bowler;

    public Team playMatch(Team A, Team B){
        System.out.println("Match Starts\nEnter total number of overs: ");
        Scanner sc = new Scanner(System.in);
        int totalBalls = sc.nextInt()*6;
        A.setTotalBalls(totalBalls);
        B.setTotalBalls(totalBalls);
        if(toss()){
            playInning(A, B, Innings.FIRSTINNING);
            showScoreboard(A, B);
            playInning(B, A, Innings.SECONDINNING);
            showScoreboard(B, A);
        }else{
            playInning(B, A, Innings.FIRSTINNING);
            showScoreboard(B, A);
            playInning(A, B, Innings.SECONDINNING);
            showScoreboard(A, B);
        }
        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;
        return null;
    }

    private static void playBall(Team A, Team B){
        int outcome = ballOutcome();
        A.incBallsPlayed(striker);
         bowler.incBallsBowled();
        if(outcome == 7){
            A.incWickets( striker,  bowler);
            if(A.getWickets()<10)
                 striker = A.getNextBatsman();
        }else {
            A.incRuns(outcome);
             striker.incRunsScored(outcome);
             bowler.incRunsConceeded(outcome);
            if(outcome%2==1){
                Player temp =  striker;
                 striker =  nonStriker;
                 nonStriker = temp;
            }
        }
    }

    private static void playInning(Team A, Team B, Innings inning){
         striker = A.getNextBatsman();
         nonStriker = A.getNextBatsman();
         bowler = B.getNextBowler();
        int totalBalls = A.getTotalBalls();
        int totalOvers = totalBalls/6;
        for (int over = 1; over <= totalOvers; over++) {
            for (int ball = 0; ball < 6; ball++) {
                playBall(A, B);
                if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
                    break;
            }
            Player temp =  striker;
             striker =  nonStriker;
             nonStriker = temp;
            if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
                break;
            Player prevBowler =  bowler;
             bowler = B.getNextBowler();
            if(prevBowler.getBallsBowled()== totalBalls/5 )prevBowler.setBowlerStatus(StatusOfBowler.REACHEDMAXOVERLIMIT);
            prevBowler.setBowlerStatus(StatusOfBowler.CANBOWLNEXT);
        }
    }

    private static int ballOutcome(){
        int randomOutcome = (int)(Math.random() * 100);
        int outcome = 7;
        if(randomOutcome <= 23)
            outcome = 0;
        else if(randomOutcome <= 45)
            outcome = 1;
        else if(randomOutcome <= 70)
            outcome = 2;
        else if(randomOutcome <= 75)
            outcome = 3;
        else if(randomOutcome <= 85)
            outcome = 4;
        else if(randomOutcome <= 93)
            outcome = 6;
        return outcome;
    }

    public static boolean toss(){
        int tossOutcome = (int) (Math.random()*100);
        boolean order = true;
        switch (tossOutcome&1) {
            case 0 :
                System.out.println("Team A wins the toss");
                break;
            case 1 :
                System.out.println("Team B wins the toss");
                order = false;
                break;
            default:
                break;
        }
        return order;
    }

    public void showScoreboard(Team A, Team B){
        System.out.println("Team Batting :" + A.getName() + "                  Team Bowling :" + B.getName());
        System.out.println("Runs : " + A.getRuns() + "                  Wickets : " + A.getWickets()+ "                  Overs: " + (int)A.getBallsPlayed()/6 + "."  + A.getBallsPlayed()%6);
        System.out.println("Player                  Runs                  Balls                  Fours                  Sixes");
        ArrayList<Player> batsmen = A.getPlayers();
        for (Player batsman: batsmen) {
            System.out.println(batsman.getName() + "                        " + batsman.getRuns()
                    + "                     " + batsman.getBallsPlayed()  + "                      " + batsman.getFours()
                    + "                       " + batsman.getSixes());
        }
        Set<Player> bowlers = B.getActiveBowlers();
        System.out.println("Player                  Balls                  Runs                  Wickets");
        for (Player bowler:bowlers) {
            System.out.println(bowler.getName() + "                        " + bowler.getBallsBowled()
                    + "                       " + bowler.getRunsConceeded()  + "                       " + bowler.getWickets());
        }
    }
}
