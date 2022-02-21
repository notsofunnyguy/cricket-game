package com.company.controllers;

import com.company.enums.Innings;
import com.company.enums.StatusOfBowler;
import com.company.models.Player;
import com.company.models.Team;

import java.util.ArrayList;
import java.util.Iterator;

import static com.company.controllers.BallController.playBall;


public class InningService implements Subject {

    private static Player striker;
    private static Player nonStriker;
    private static Player bowler;
    private static int totalOvers;
    private static int run;
    ArrayList<Observer> observerList;

    public InningService(){
        observerList = new ArrayList<>();
    }

    public void playInning(Team A, Team B, Innings inning){
        setStriker(A);
        setNonStriker(A);
        setBowler(B);
        initObservers(A);
        int totalBalls = A.getTotalBalls();
        totalOvers = totalBalls/6;
        for (int over = 1; over <= totalOvers; over++) {
            for (int ball = 0; ball < 6; ball++) {
                run = playBall();
                updateBallOutcome(A, B);
                if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
                    break;
            }
            swapStrikerNonStriker();
            if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
                break;
            Player prevBowler =  bowler;
            unregisterObserver(bowler);
            setBowler(B);
            registerObserver(bowler, "bowler");
            if(prevBowler.getBallsBowled() == totalBalls/5 ) prevBowler.setBowlerStatus(StatusOfBowler.REACHEDMAXOVERLIMIT);
            prevBowler.setBowlerStatus(StatusOfBowler.CANBOWLNEXT);
        }
    }

    public static void setBowler(Team bowlingTeam) {
        InningService.bowler = bowlingTeam.getNextBowler();
    }

    public static void setStriker(Team battingTeam) {
        InningService.striker = battingTeam.getNextBatsman();
    }

    public static void setNonStriker(Team battingTeam) {
        InningService.nonStriker = battingTeam.getNextBatsman();
    }

    public void initObservers(Team A){
        registerObserver(striker, "striker");
        registerObserver(nonStriker, "nonStriker");
        registerObserver(bowler, "bowler");
        registerObserver(A, "team");
    }

    public void updateBallOutcome(Team A, Team B){
        notifyObservers();
        if(run == 7 ){
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
    public void notifyObservers() {
        int idx = 0;
        for (Iterator<Observer> it = observerList.iterator(); it.hasNext();) {
            Observer o = it.next();
            o.update(run, idx);
            idx++;
        }
    }
}
