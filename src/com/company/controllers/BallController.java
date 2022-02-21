package com.company.controllers;

public abstract class BallController {

    public static int playBall(){
        return getBallOutcome();
    }

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
