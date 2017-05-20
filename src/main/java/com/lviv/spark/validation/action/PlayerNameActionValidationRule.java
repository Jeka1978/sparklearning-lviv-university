package com.lviv.spark.validation.action;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.FootballAction;
import com.lviv.spark.properties.FootballTeamsProperties;
import com.lviv.spark.utils.StringUtils;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Anatoliy on 11.04.2017.
 */
@Component
public class PlayerNameActionValidationRule extends ActionValidationRuleBase implements Serializable {
    @AutowiredBroadcast
    private Broadcast<FootballTeamsProperties> teamsProperties;

    @Override
    protected boolean actionIsInvalid(FootballAction action) {
        FootballTeamsProperties tp = teamsProperties.value();
        String playerFrom = action.getFrom();
        String playerTo = action.getTo();
        if(playerNameIsInvalid(tp, playerFrom)) {
            return true;
        }
        if(playerNameIsInvalid(tp, playerTo)) {
            return true;
        }
        return false;
    }

    private boolean playerNameIsInvalid(FootballTeamsProperties tp, String playerFrom) {
        return !StringUtils.isEmpty(playerFrom) && !tp.ContainsPlayer(playerFrom);
    }

    @Override
    public String columnName() {
        return "PlayerNameInvalid";
    }

    @Override
    public String[] requiredColumns() {
        return new String[]{"from", "to"};
    }
}
