package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
public class Match {

    private InningService inning;
    private int totalOvers;

    public Team playMatch(Team A, Team B){
        inning = new InningService();
        int totalBalls = this.totalOvers*6;
        A.setTotalBalls(totalBalls);
        B.setTotalBalls(totalBalls);
        if(toss()){
            inning.playInning(A, B, Innings.FIRSTINNING);
            showScoreboard(A, B);
            inning.playInning(B, A, Innings.SECONDINNING);
            showScoreboard(B, A);
        }else{
            inning.playInning(B, A, Innings.FIRSTINNING);
            showScoreboard(B, A);
            inning.playInning(A, B, Innings.SECONDINNING);
            showScoreboard(A, B);
        }
        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;
        return null;
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

    public void setTotalOvers(int totalOvers) {
        this.totalOvers = totalOvers;
    }
}
