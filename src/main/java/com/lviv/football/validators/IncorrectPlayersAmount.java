package com.lviv.football.validators;

import com.lviv.football.ActionInfo;
import com.lviv.football.configs.UserConfig;
import com.lviv.infra.AutowiredBroadcast;
import lombok.Getter;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.lviv.football.constants.Columns.*;

/**
 * Created by rudnitskih on 4/14/17.
 */
@Component
public class IncorrectPlayersAmount implements ActionValidator {
    @Getter
    private String issueCode = "2";

    @Getter
    private String description = "No correct list of players";

    @AutowiredBroadcast
    private Broadcast<UserConfig> userConfig;

    @Override
    public String validate(ActionInfo action) {
        String fromName = action.getProperty(from);
        String toName = action.getProperty(to);
        String actionCode = action.getProperty(code);

        Boolean isCorrectAction = true;

        List<String> codesWithOneParticipant = userConfig.value().getCodesWithOneParticipant();

        if (!Objects.equals(actionCode, "")) {
            if (codesWithOneParticipant.contains(actionCode)) {
                if (!Objects.equals(fromName, "") && !Objects.equals(toName, "")) {
                    isCorrectAction = false;
                }
            } else {
                if (Objects.equals(fromName, "") || Objects.equals(toName, "")) {
                    isCorrectAction = false;
                }
            }
        }

        return isCorrectAction ? null : issueCode;
    }
}
