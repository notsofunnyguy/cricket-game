package com.company.controllers;

import com.company.enums.Innings;
import com.company.models.Player;
import com.company.models.Team;

import java.util.ArrayList;
import java.util.Set;

public abstract class Display {

    public static void displayScoreboard(Team A, Team B, Innings inning){
        if(inning.compareTo(Innings.SECONDINNING)==0){
            System.out.println("Target: " + B.getRuns());
        }
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

    public static void displayResult(int indiaWins, int pakistanWins, int noOfMatches){
        String typeOfGame;
        if(noOfMatches==1){
            typeOfGame ="Match";
        }
        else{
            typeOfGame ="Series";
        }

        if(indiaWins >noOfMatches/2) {
            System.out.print("India wins the " + typeOfGame + ". ");
            if(noOfMatches>1){
                System.out.println("By: " + indiaWins + ":" + pakistanWins);
            }
        }else if(pakistanWins >noOfMatches/2) {
            System.out.print("Pakistan wins the " + typeOfGame + ". ");
            if(noOfMatches>1){
                System.out.println("By: " + pakistanWins + ":" + indiaWins);
            }
        }else {
            System.out.println(typeOfGame + " Draw.");
        }
    }

}
