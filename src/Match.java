import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Match {

    enum Innings{
        FirstInning,
        SecondInning
    }
    public Team playMatch(Team A, Team B){
        System.out.println("Match Starts");
        Scanner sc = new Scanner(System.in);
        int totalBalls = sc.nextInt();
        A.setTotalBalls(totalBalls);
        B.setTotalBalls(totalBalls);
        if(Toss(A, B)){
            Inning(A, B, Innings.FirstInning);
            showScoreboard(A, B);
            Inning(B, A, Innings.SecondInning);
            showScoreboard(B, A);
        }else{
            Inning(B, A, Innings.FirstInning);
            showScoreboard(B, A);
            Inning(A, B, Innings.SecondInning);
            showScoreboard(A, B);
        }
        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;
        return null;
    }

    private static void Inning(Team A, Team B, Innings inning){
        Player Striker = A.getNextBatsman();
        Player nonStriker = A.getNextBatsman();
        Player bowler = B.getNextBowler();
        int totalBalls = A.getTotalBalls();
        int totalOvers = totalBalls/6;
        for (int i = 1; i <= totalOvers; i++) {
            for (int j = 0; j < 6; j++) {
                int outcome = ballOutcome();
                A.incBallsPlayed(Striker);
                bowler.incBallsBowled();
                if(outcome==7){
                    A.incWickets(Striker, bowler);
                    if(A.getWickets()<10)
                        Striker = A.getNextBatsman();
                }else {
                    A.incRuns(outcome);
                    Striker.incRunsScored(outcome);
                    bowler.incRunsConceeded(outcome);
                    if(outcome%2==1){
                        Player temp = Striker;
                        Striker = nonStriker;
                        nonStriker = temp;
                    }
                }
                if(A.getWickets()==10 || (inning.compareTo(Innings.SecondInning)==0 && A.getRuns()>B.getRuns()))
                    break;
            }
            Player temp = Striker;
            Striker = nonStriker;
            nonStriker = temp;
            if(A.getWickets()==10 || (inning.compareTo(Innings.SecondInning)==0 && A.getRuns()>B.getRuns()))
                break;
            Player prevBowler = bowler;
            bowler = B.getNextBowler();
            if(prevBowler.getBallsBowled()== totalBalls/5 )prevBowler.setBowlerStatus(statusOfBowler.ReachedMaxOverLimit);;
            prevBowler.setBowlerStatus(statusOfBowler.CanBowlNext);
        }
    }
    private static int ballOutcome(){
        int outcome = (int)(Math.random() * 72);;
        return outcome/10;
    }

    public static boolean Toss(Team A, Team B){
        Scanner sc = new Scanner(System.in);
        int tossOutcome = (int) (Math.random()*100);
        boolean order = true;
        switch (tossOutcome&1) {
            case 1 :
                System.out.println("Team A wins the toss");
                System.out.println("enter 1 to bat first enter 0 for ball first");
                int captainsCall = sc.nextInt();
                switch (captainsCall){
                    case 1 :
                        order = true;
                        break;
                    case 0 :
                        order = false;
                        break;
                    default :
                        break;
                }
                break;
            case 0 :
                System.out.println("Team B wins the toss");
                System.out.println("enter 1 to bat first enter 0 for ball first");
                captainsCall = sc.nextInt();
                switch (captainsCall){
                    case 1 :
                        order = false;
                        break;
                    case 2 :
                        order = true;
                        break;
                    default :
                        break;
                }
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
        ArrayList<Player> bowlers = B.getPlayers();
        System.out.println("Player                  Balls                  Runs                  Wickets");
        for (Player bowler:bowlers) {
            System.out.println(bowler.getName() + "                        " + bowler.getBallsBowled()
                    + "                       " + bowler.getRunsConceeded()  + "                       " + bowler.getWickets());
        }
    }
}
