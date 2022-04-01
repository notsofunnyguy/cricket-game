package com.tekion.helpers;


import com.tekion.GameRuleEngine.MatchRuleEngine;

public class Toss {
    public static int tossWinningTeamId;
    /*

    This method randomly decides which
    team wins the toss.
     */
    public static boolean toss(){
        boolean order = MatchRuleEngine.tossRuleEngine();
        return order;
    }
}
