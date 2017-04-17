package com.lviv.spark.validation.action;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.FootballAction;
import com.lviv.spark.properties.FootballCodesProperties;
import com.lviv.spark.utils.StringUtils;
import lombok.SneakyThrows;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.spark.api.java.function.Function;

/**
 * Created by Anatoliy on 11.04.2017.
 */
@Component
public class GameCodeActionValidationRule extends ActionValidationRuleBase implements Serializable {

    @AutowiredBroadcast
    Broadcast<FootballCodesProperties> footbalCodesBroadcast;

    @Override
    @SneakyThrows
    protected boolean actionIsInvalid(FootballAction action) {
        Integer code = action.getCode();
        FootballCodesProperties codes = footbalCodesBroadcast.value();
        if(!codes.codeIsCorrect(code))
            return true;

        // check correct players number to code
        if(!codeToPlayersValidation.get(code).call(action))
            return true;

        return false;
    }

    Map<Integer, Function<FootballAction,Boolean>> codeToPlayersValidation;

    public GameCodeActionValidationRule() {
        codeToPlayersValidation = new HashMap<>();
        Function<FootballAction,Boolean> requireFrom = (fa) -> !StringUtils.isEmpty(fa.getFrom()) && StringUtils.isEmpty(fa.getTo());
        Function<FootballAction,Boolean> requireTo = (fa) -> StringUtils.isEmpty(fa.getFrom()) && !StringUtils.isEmpty(fa.getTo());
        Function<FootballAction,Boolean> requireBoth = (fa) -> !StringUtils.isEmpty(fa.getFrom()) && !StringUtils.isEmpty(fa.getTo());
        codeToPlayersValidation.put(1, requireTo);
        codeToPlayersValidation.put(2, requireTo);
        codeToPlayersValidation.put(3, requireBoth);
        codeToPlayersValidation.put(4, requireBoth);
        codeToPlayersValidation.put(5, requireFrom);
        codeToPlayersValidation.put(6, requireBoth);
        codeToPlayersValidation.put(7, requireFrom);
        codeToPlayersValidation.put(9, requireFrom);
        codeToPlayersValidation.put(10, requireBoth);
    }


    @Override
    public String columnName() {
        return "GameCodeInvalid";
    }

    @Override
    public String[] requiredColumns() {
        return new String[]{"code"};
    }


}
