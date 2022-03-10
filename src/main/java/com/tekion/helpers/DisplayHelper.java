package com.tekion.helpers;

import com.tekion.constants.StringUtils;
import com.tekion.models.Team;
import com.tekion.repository.DbUpdates;

import java.sql.*;

public abstract class DisplayHelper {


    /*

    This method displays the final
    result after match/series.
     */
    public static void updateResult(Team A, Team B, int noOfMatches) throws SQLException {
        if(A.getWins() > noOfMatches/2)
            updateFinalResult(A, B, noOfMatches);
        else if(B.getWins() > noOfMatches/2)
            updateFinalResult(B, A, noOfMatches);
        else {
            if(noOfMatches>1)
                DbUpdates.updateSeriesTable("DRAW", A.getWins() + ":" + B.getWins());
        }
    }

    private static void updateFinalResult(Team winningTeam, Team losingTeam, int noOfMatches) throws SQLException {
        if(noOfMatches>1){
            DbUpdates.updateSeriesTable(winningTeam.getName(), winningTeam.getWins() + ":" + losingTeam.getWins());
        }
    }

}
