package com.company.helpers;

public abstract class DecideWicketNature {
    public static String getRandomWicketNature(){
        int randomWicketNature = (int)(Math.random() * 5);
        switch (randomWicketNature){
            case 0:
                return "Bowled";
            case 1:
                return "Caught";
            case 2:
                return "LBW";
            case 3:
                return "Run_Out";
            default:
                return "Stumped";
        }
    }
}
