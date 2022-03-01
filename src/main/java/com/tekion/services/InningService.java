package main.java.com.tekion.services;

import main.java.com.tekion.controllers.GameController;
import com.tekion.helpers.DecideWicketNature;
import main.java.com.tekion.enums.Innings;
import main.java.com.tekion.helpers.DBUpdatesHelperClass;
import main.java.com.tekion.models.Team;

import java.sql.*;

import static main.java.com.tekion.helpers.DisplayHelper.displayScoreboard;
import static main.java.com.tekion.services.BallService.playBall;


public class InningService {

    /*
    28-02-2022

    This method conductInningsInOrder is responsible
    for calling the playInning method for
    conducting innings in order as toss winning team
    decided.

    @params     A       first team
    @params     B       second team
     */
    public void conductInningsInOrder(Team A, Team B) throws SQLException {

        GameController.currentInningId++;
        DBUpdatesHelperClass.initialiseInningsDB(A.getTeamId(), B.getTeamId());
        DBUpdatesHelperClass.updateInningsIdOnMatchesTable(Innings.FIRSTINNING);
        System.out.println("First Inning:");
        playInning(A, B, Innings.FIRSTINNING);
        displayScoreboard(A, B);

        GameController.currentInningId++;
        DBUpdatesHelperClass.initialiseInningsDB(B.getTeamId(), A.getTeamId());
        DBUpdatesHelperClass.updateInningsIdOnMatchesTable(Innings.SECONDINNING);
        System.out.println("Second Inning:");
        System.out.println("Target : " + A.getRuns());
        playInning(B, A, Innings.SECONDINNING);
        displayScoreboard(B, A);

    }

    /*
    28-02-2022

    This method playInning is responsible
    for conducting the innings.

    @params     battingTeam       batting team
    @params     bowlingTeam       bowling team
    @params     inning  stores whether first or second inning is going
     */
    private void playInning(Team battingTeam, Team bowlingTeam, Innings inning) throws SQLException {
        int overs = GameController.totalOvers;
        Team.initPlayers(battingTeam, bowlingTeam);
        for (int over = 0; over < overs; over++) {
            System.out.println("Over:" + over + "\n");
            for (int ball = 0; ball < 6; ball++) {
                int runs = playBall();
                Team.displayBallStats(battingTeam, bowlingTeam, over, ball, runs);
                updateBallOutcome(6*over+ball+1, battingTeam, bowlingTeam, runs);
                if(isInningFinish(battingTeam, bowlingTeam, inning))
                    break;
            }
            System.out.println();
            battingTeam.updateBattingScoreboard();
            bowlingTeam.updateBowlingScoreboard();
            battingTeam.swapStrikerNonStriker();
            DBUpdatesHelperClass.updateInningsDb(battingTeam);
            if(isInningFinish(battingTeam, bowlingTeam, inning))
                break;
            bowlingTeam.setBowler();
        }
    }

    /*
    28-02-2022

    This method isInningFinish checks whether
    the current inning is over or not.

    @params     battingTeam       batting team
    @params     bowlingTeam       bowling team
    @params     inning  stores whether first or second inning is going
     */
    private boolean isInningFinish(Team battingTeam, Team bowlingTeam, Innings inning){
        if(battingTeam.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && battingTeam.getRuns()>bowlingTeam.getRuns()))
            return true;
        return false;
    }

    /*
    28-02-2022

    This method updateBallOutcome will call
    the necessary methods to update the
    teams and players data/records.

    @params     battingTeam       batting team
    @params     bowlingTeam       bowling team
    @params     runs              score on recent ball
     */
    public void updateBallOutcome(int ballNo, Team battingTeam, Team bowlingTeam, int runs) throws SQLException {
        String outType = " ";
        battingTeam.updateBattingTeam(runs);
        bowlingTeam.updateBowlingTeam(runs);
        if(runs == 7 ){
            outType = DecideWicketNature.getRandomWicketNature();
            Team.updateWickets(battingTeam, bowlingTeam, outType);
            battingTeam.updateBattingScoreboard();
            bowlingTeam.updateBowlingScoreboard();
        }
        else if(runs%2 == 1){
            battingTeam.swapStrikerNonStriker();
        }
        DBUpdatesHelperClass.updateBallTable(ballNo, battingTeam.getStriker().getJerseyNumber(),
                bowlingTeam.getBowler().getJerseyNumber(), runs, outType );
        if(runs == 7) battingTeam.setStriker();
    }

}
