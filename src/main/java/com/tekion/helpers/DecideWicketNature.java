package com.tekion.helpers;

import com.tekion.enums.WicketTypes;


import java.util.List;

public abstract class DecideWicketNature {

    /*

    This method here chooses the random
    wicket type if any player gets out.
     */
    public static String getRandomWicketNature(){
        int randomWicketNature = (int)(Math.random() * 5);
        String[] wicketType = new String[] {WicketTypes.BOWLED.name(),WicketTypes.CAUGHT.name(), WicketTypes.LBW.name()
                , WicketTypes.RUN_OUT.name(), WicketTypes.STUMPED.name()};
        return wicketType[randomWicketNature];
    }
}
