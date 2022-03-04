package com.tekion.services;



import com.tekion.models.Team;
import com.tekion.repository.DbUpdates;

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
    public static Team playMatch(Team A, Team B) throws SQLException {

        InningService inning = new InningService();

        if(toss()) {
            DbUpdates.updateTossWinningTeamID(A.getTeamId());
            inning.conductInningsInOrder(A, B);
        }
        else {
            DbUpdates.updateTossWinningTeamID(B.getTeamId());
            inning.conductInningsInOrder(B, A);
        }
        DbUpdates.updateMatchStatsDb(A);
        DbUpdates.updateMatchStatsDb(B);
        A.updatePlayerStatsOfTeam();
        B.updatePlayerStatsOfTeam();
        if(A.getRuns()>B.getRuns()) return A;
        else if(A.getRuns()<B.getRuns()) return B;
        return null;

    }



}
