import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class CricketGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Team India = new Team("India");
        Team Pakistan = new Team("Pakistan");
        int IndiaWins = 0;
        int PakistanWins = 0;
        System.out.println("Type of match : enter 1 for Single Match enter number of matches to played in the Series");
        int noOfMatches = sc.nextInt();
        for (int i = 1; i <= noOfMatches; i++) {
            Match match = new Match();
            Team winningTeam =match.playMatch(India, Pakistan);
            if(winningTeam == India)
                IndiaWins += 1;
            else if(winningTeam == Pakistan)
                PakistanWins += 1;
            India.resetTeam();
            Pakistan.resetTeam();
        }
        if(IndiaWins > noOfMatches/2){
            System.out.println("India Wins the series.");
        }else if(PakistanWins > noOfMatches/2){
            System.out.println("Pakistan Wins the series.");
        }else{
            System.out.println("Series Draw.");
        }
    }





}
