package com.tekion.helpers;

public class Toss {
    public static int tossWinningTeamId;
    /*

    This method randomly decides which
    team wins the toss.
     */
    public static boolean toss(){
        int tossOutcome = (int) (Math.random()*100);
        boolean order = true;
        switch (tossOutcome&1) {
            case 0 :
                System.out.println("Team A wins the toss");
                break;
            case 1 :
                System.out.println("Team B wins the toss");
                order = false;
                break;
            default:
                break;
        }
        return order;
    }
}
