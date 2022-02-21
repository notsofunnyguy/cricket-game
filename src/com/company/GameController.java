package com.company;

import java.util.Scanner;

public class GameController {

    public void playGame() {
        Scanner sc = new Scanner(System.in);

        Team india = new Team("India");
        Team pakistan = new Team("Pakistan");

        int indiaWins = 0;
        int pakistanWins = 0;

        System.out.println("Type of match : enter 1 for Single Match enter number of matches to played in the Series");
        int noOfMatches = sc.nextInt();

        System.out.println("Enter total number of overs: ");
        int totalOvers = sc.nextInt();

        for(int i = 1; i <=noOfMatches;i++) {

            Match match = new Match();
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

        String typeOfGame;
        if(noOfMatches==1){
            typeOfGame ="Match";
        }
        else{
            typeOfGame ="Series.";
        }

        if(indiaWins >noOfMatches/2) {
            System.out.println("India wins the " + typeOfGame + ".");
        }else if(pakistanWins >noOfMatches/2) {
            System.out.println("Pakistan wins the " + typeOfGame + ".");
        }else {
            System.out.println("Series Draw.");
        }
    }
}
