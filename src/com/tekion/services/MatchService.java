package com.tekion.services;

import com.tekion.models.Team;
import com.tekion.services.InningService;

import java.sql.SQLException;
import static com.tekion.helpers.Toss.toss;

public abstract class MatchService {


    /*
    28-02-2022

    This method playMatch called by playGame
    calling method conductInningsInOrder to
    conducts innings in the order.


    @params     A               first team playing in a match
    @params     B               second team playing in a match
    @params     totalOvers      number of overs for which the match will be played

    @return match winning team.
     */
    public static Team playMatch(Team A, Team B, int Overs) throws SQLException {

        InningService inning = new InningService();

        if(toss())
            inning.conductInningsInOrder(A, B);
        else
            inning.conductInningsInOrder(B, A);

        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;
        return null;

    }



}
