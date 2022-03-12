package com.tekion.services;



import com.tekion.controllers.GameController;
import com.tekion.helpers.Toss;
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
    @return match winning team.
     */
    public static Team playMatch(Team A, Team B) throws SQLException {

        InningService inning = new InningService();

        if(toss()) {
            Toss.tossWinningTeamId = A.getTeamId();
            inning.conductInningsInOrder(A, B);
        }
        else {
            Toss.tossWinningTeamId = B.getTeamId();
            inning.conductInningsInOrder(B, A);
        }
        Team winningTeam = null;
        GameController.winningTeamId = -1;
        if(A.getRuns()>B.getRuns()){
            winningTeam =  A;
            GameController.winningTeamId = A.getTeamId();
        }
        else if(A.getRuns()<B.getRuns()){
            GameController.winningTeamId = B.getTeamId();
            winningTeam = B;
        }
        DbUpdates.updateMatchStatsDb(A, B);
        DbUpdates.updateScoreboard(A);
        DbUpdates.updateScoreboard(B);
        return winningTeam;

    }
}
