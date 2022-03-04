package com.tekion.services;

public abstract class BallService {


    public static int playBall(){
        return getBallOutcome();
    }

    /*
    28-02-2022

    This method getBallOutcome will
    generate the random score for the ball
    with higher probability of lower runs.
     */
    public static int getBallOutcome(){
        int randomOutcome = (int)(Math.random() * 100);
        int outcome = 7;
        if(randomOutcome <= 27)
            outcome = 0;
        else if(randomOutcome <= 50)
            outcome = 1;
        else if(randomOutcome <= 75)
            outcome = 2;
        else if(randomOutcome <= 82)
            outcome = 3;
        else if(randomOutcome <= 90)
            outcome = 4;
        else if(randomOutcome <= 95)
            outcome = 6;
        return outcome;
    }

}
