package com.tekion.GameRuleEngine;
import com.tekion.constants.StringUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

public class MatchRuleEngine {

    public static boolean tossRuleEngine(){

        int tossOutcome = (int) (Math.random()*100);
        tossOutcome = tossOutcome&1;

        System.out.println(tossOutcome);
        Rule firstTeamBattingFirstRule = new RuleBuilder()
                .name(StringUtils.TOSS_RULE)
                .description(StringUtils.FIRST_TEAM_BAT_FIRST_DESCRIPTION)
                .when(facts -> (int)facts.get("number")==1)
                .then(facts -> facts.put("order", true))
                .build();

        Rule secondTeamBattingFirstRule = new RuleBuilder()
                .name(StringUtils.TOSS_RULE)
                .description(StringUtils.SECOND_TEAM_BAT_FIRST_DESCRIPTION)
                .when(facts -> (int)facts.get("number")==0)
                .then(facts -> facts.put("order", false))
                .build();

        Facts facts = new Facts();

        facts.put("number", tossOutcome);

        Rules rules = new Rules();
        rules.register(firstTeamBattingFirstRule);
        rules.register(secondTeamBattingFirstRule);

        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

        return facts.get("order");

    }




}
