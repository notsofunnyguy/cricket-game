package com.company.controllers;

import com.company.enums.Innings;
import com.company.models.Player;
import com.company.models.Team;

import java.util.ArrayList;
import java.util.Set;

import static com.company.controllers.Display.displayScoreboard;
import static com.company.controllers.GameHelpers.toss;

public class MatchProcessor {

    private InningService inning;
    private int totalOvers;

    public Team playMatch(Team A, Team B){

        inning = new InningService();
        int totalBalls = this.totalOvers*6;

        A.setTotalBalls(totalBalls);
        B.setTotalBalls(totalBalls);

        if(toss()){
            inning.playInning(A, B, Innings.FIRSTINNING);
            displayScoreboard(A, B, Innings.FIRSTINNING);
            inning.playInning(B, A, Innings.SECONDINNING);
            displayScoreboard(B, A, Innings.SECONDINNING);
        }else{
            inning.playInning(B, A, Innings.FIRSTINNING);
            displayScoreboard(B, A, Innings.FIRSTINNING);
            inning.playInning(A, B, Innings.SECONDINNING);
            displayScoreboard(A, B, Innings.SECONDINNING);
        }

        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;

        return null;
    }

    public void setTotalOvers(int totalOvers) {
        this.totalOvers = totalOvers;
    }

}
