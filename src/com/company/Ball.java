package com.company;

public class Ball {
    static int ballOutcome;

    public int playBall(){
        this.ballOutcome = getBallOutcome();
        return ballOutcome;
    }

    public static int getBallOutcome(){
        int randomOutcome = (int)(Math.random() * 100);
        int outcome = 7;
        if(randomOutcome <= 23)
            outcome = 0;
        else if(randomOutcome <= 45)
            outcome = 1;
        else if(randomOutcome <= 70)
            outcome = 2;
        else if(randomOutcome <= 75)
            outcome = 3;
        else if(randomOutcome <= 85)
            outcome = 4;
        else if(randomOutcome <= 93)
            outcome = 6;
        return outcome;
    }
}
