package com.company.controllers;

import com.company.CricketGame;
import com.company.enums.Innings;
import com.company.enums.BowlerStatus;
import com.company.helpers.DBUpdatesHelperClass;
import com.company.helpers.DecideWicketNature;
import com.company.interfaces.Observer;
import com.company.interfaces.Subject;
import com.company.models.Player;
import com.company.models.Team;

import java.sql.*;
import java.util.ArrayList;

import static com.company.controllers.BallController.playBall;


public class InningController implements Subject {

    private static Player striker;
    private static Player nonStriker;
    private static Player bowler;
    private static int totalOvers;
    private static int run;
    static ArrayList<Observer> observerList;

    public InningController(){
        observerList = new ArrayList<>();
    }

    public void playInning(Team A, Team B, Innings inning) throws SQLException {

        if(inning.compareTo(Innings.FIRSTINNING)==0)
            System.out.println("First Inning:");
        else{
            System.out.println("Second Inning:");
            System.out.println("Target : " + B.getRuns());
        }

        int totalBalls = A.getTotalBalls();
        totalOvers = totalBalls/6;

        initialiseObservers(A, B, totalBalls);

        for (int over = 0; over < totalOvers; over++) {
            System.out.println("Over:" + over + "\n");
            for (int ball = 0; ball < 6; ball++) {
                run = playBall();
                displayBallStats(over, ball);
                updateBallOutcome(A);
                if(isMatchFinish(A, B, inning))
                    break;
            }

            System.out.println();
            Player.updateScoreboard(striker, nonStriker, bowler);
            swapStrikerNonStriker();

            DBUpdatesHelperClass.updateMatchStatsDb(A);

            if(isMatchFinish(A, B, inning))
                break;

            setBowler(B, totalBalls);

        }
    }

    private void initialiseObservers(Team A,Team B, int totalBalls){
        observerList.clear();
        setStriker(A);
        setNonStriker(A);
        InningController.bowler = B.getNextBowler();
        initObservers(A);
    }

    private void setBowler(Team bowlingTeam, int totalBalls) {
        Player prevBowler =  bowler;
        unregisterObserver(bowler);
        InningController.bowler = bowlingTeam.getNextBowler();
        registerObserver(bowler, "bowler");
        if(prevBowler.getBallsBowled() == totalBalls/5 ) prevBowler.setBowlerStatus(BowlerStatus.REACHEDMAXOVERLIMIT);
        prevBowler.setBowlerStatus(BowlerStatus.CANBOWLNEXT);
    }

    private static void setNonStriker(Team battingTeam) {
        InningController.nonStriker = battingTeam.getNextBatsman();
    }

    private static void setStriker(Team battingTeam) {
        InningController.striker = battingTeam.getNextBatsman();
    }


    private boolean isMatchFinish(Team A, Team B, Innings inning){
        if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
            return true;
        return false;
    }



    private void initObservers(Team A){
        registerObserver(striker, "striker");
        registerObserver(nonStriker, "nonStriker");
        registerObserver(bowler, "bowler");
        registerObserver(A, "team");
    }

    public void updateBallOutcome(Team A) throws SQLException {
        notifyObservers();
        if(run == 7 ){
            striker.setOutBy(bowler.getJerseyNumber());
            String outType = DecideWicketNature.getRandomWicketNature();
            String sql = "update scoreboard set wicket_type = '" + outType + "' where match_id=" + GameController.getMatchId() + " and player_id = " + striker.getJerseyNumber();
            DBUpdatesHelperClass.executeUpdateQuery(sql);
            Player.updateScoreboard(striker, nonStriker, bowler);
            unregisterObserver(striker);
            setStriker(A);
            registerObserver(striker, "striker");
        }
        else if(run%2 == 1){
            swapStrikerNonStriker();
        }
    }

    public void swapStrikerNonStriker(){
        unregisterObserver(striker);
        Player temp =  striker;
        striker =  nonStriker;
        nonStriker = temp;
        registerObserver(nonStriker, "nonStriker");
    }

    @Override
    public void registerObserver(Observer o, String typeOfObserver) {
        if(typeOfObserver.compareTo("striker")==0)
            observerList.add(0,o);
        else if(typeOfObserver.compareTo("nonStriker")==0)
            observerList.add(1,o);
        else if(typeOfObserver.compareTo("bowler")==0)
            observerList.add(2,o);
        else observerList.add(3,o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        int idx = observerList.indexOf(o);
        observerList.remove(idx);
    }

    @Override
    public void notifyObservers() throws SQLException {
        observerList.get(0).updateStriker(run);
        observerList.get(2).updateBowler(run);
        observerList.get(3).updateTeam(run);
    }

    private void displayBallStats(int overs, int balls){
        if(run<7)
            System.out.println(overs+"."+ (balls+1) + " : " + bowler.getName() + " to " + striker.getName() + "   " + run + " runs");
        else
            System.out.println(overs+"."+ (balls+1) + " : " + bowler.getName() + " to " + striker.getName() + "   W");
    }
}
