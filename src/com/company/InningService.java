package com.company;

public class InningService implements BallUpdate{

    private static Player striker;
    private static Player nonStriker;
    private static Player bowler;
    private static int totalOvers;
    static Ball balll;

    public void playInning(Team A, Team B, Innings inning){
        striker = A.getNextBatsman();
        nonStriker = A.getNextBatsman();
        bowler = B.getNextBowler();
        balll = new Ball();
        int totalBalls = A.getTotalBalls();
        totalOvers = totalBalls/6;
        for (int over = 1; over <= totalOvers; over++) {
            for (int ball = 0; ball < 6; ball++) {
                int outcome = balll.playBall();
                updateBallOutcome(outcome, A, B);
                if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
                    break;
            }
            swapStrikerNonStriker();
            if(A.getWickets()==10 || (inning.compareTo(Innings.SECONDINNING)==0 && A.getRuns()>B.getRuns()))
                break;
            Player prevBowler =  bowler;
            bowler = B.getNextBowler();
            if(prevBowler.getBallsBowled() == totalBalls/5 ) prevBowler.setBowlerStatus(StatusOfBowler.REACHEDMAXOVERLIMIT);
            prevBowler.setBowlerStatus(StatusOfBowler.CANBOWLNEXT);
        }
    }


    public void updateBallOutcome(int runs, Team A, Team B){
        updateBatsman(runs);
        updateBowler(runs);
        updateTeams(A, runs);
        if(runs == 7){
            A.incWickets(striker,  bowler);
            if(A.getWickets()<10)
                striker = A.getNextBatsman();
        }else {
            if(runs%2 == 1){
                swapStrikerNonStriker();
            }
        }
    }

    public void swapStrikerNonStriker(){
        Player temp =  striker;
        striker =  nonStriker;
        nonStriker = temp;
    }


    @Override
    public void updateBatsman(int runs) {
        striker.incBallsPlayed();
        striker.incRunsScored(runs);
    }

    @Override
    public void updateBowler(int runs) {
        bowler.incBallsBowled();
        bowler.incRunsConceeded(runs);
    }

    @Override
    public void updateTeams(Team T, int runs) {
        T.incBallsPlayed();
        T.incRuns(runs);
    }

}
