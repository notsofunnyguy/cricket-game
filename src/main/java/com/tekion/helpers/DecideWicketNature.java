package com.tekion.helpers;

public abstract class DecideWicketNature {

    /*

    This method here chooses the random
    wicket type if any player gets out.
     */
    public static String getRandomWicketNature(){
        int randomWicketNature = (int)(Math.random() * 5);
        switch (randomWicketNature){
            case 0:
                return "BOWLED";
            case 1:
                return "CAUGHT";
            case 2:
                return "LBW";
            case 3:
                return "RUN_OUT";
            default:
                return "STUMPED";
        }
    }
}
