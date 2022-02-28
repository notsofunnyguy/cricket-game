package com.company.controllers;

import com.company.enums.Innings;
import com.company.models.Team;
import java.sql.SQLException;
import static com.company.helpers.DisplayHelper.displayScoreboard;
import static com.company.helpers.Toss.toss;

public class MatchController {

    public static Team playMatch(Team A, Team B, int totalOvers) throws SQLException {

        InningController inning = new InningController();
        int totalBalls = totalOvers*6;

        A.setTotalBalls(totalBalls);
        B.setTotalBalls(totalBalls);

        if(toss()){
            inning.playInning(A, B, Innings.FIRSTINNING);
            displayScoreboard(A, B);
            inning.playInning(B, A, Innings.SECONDINNING);
            displayScoreboard(B, A);
        }else{
            inning.playInning(B, A, Innings.FIRSTINNING);
            displayScoreboard(B, A);
            inning.playInning(A, B, Innings.SECONDINNING);
            displayScoreboard(A, B);
        }

        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;
        return null;

    }

}
