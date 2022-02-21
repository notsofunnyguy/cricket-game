package com.company.controllers;

import com.company.models.Team;

import java.util.Scanner;

import static com.company.controllers.Display.displayResult;

public class GameController {

    public void playGame(){
        Scanner sc = new Scanner(System.in);
        Team india = new Team("India");
        Team pakistan = new Team("Pakistan");
        int indiaWins = 0;
        int pakistanWins = 0;
        System.out.println("Type of match : enter 1 for Single Match enter number of matches to played in the Series");
        int noOfMatches = sc.nextInt();
        System.out.println("Enter total number of overs: ");
        int totalOvers = sc.nextInt();
        for(int matchNum = 1; matchNum <=noOfMatches ; matchNum++) {
            MatchProcessor match = new MatchProcessor();
            match.setTotalOvers(totalOvers);
            Team winningTeam = match.playMatch(india, pakistan);
            if (winningTeam == india){
                indiaWins += 1;
            } else if (winningTeam == pakistan) {
                pakistanWins += 1;
            }
            india.resetTeam();
            pakistan.resetTeam();
        }

        displayResult(indiaWins, pakistanWins, noOfMatches);

    }
}
