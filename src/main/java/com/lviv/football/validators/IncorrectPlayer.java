package com.lviv.football.validators;

import com.lviv.football.ActionInfo;
import com.lviv.football.configs.UserConfig;
import com.lviv.infra.AutowiredBroadcast;
import lombok.Getter;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.lviv.football.constants.Columns.from;
import static com.lviv.football.constants.Columns.to;

/**
 * Created by rudnitskih on 4/12/17.
 */
@Component
public class IncorrectPlayer implements ActionValidator {
    @Getter
    public String issueCode = "3";

    @Getter
    public String description = "Player is not found";

    @AutowiredBroadcast
    private Broadcast<UserConfig> userConfig;

    @Override
    public String validate(ActionInfo action) {
        Map<String, Boolean> names = new HashMap<>();

        names.put(action.getProperty(from), false);
        names.put(action.getProperty(to), false);

        names.replaceAll((name, correctValue) -> {
            if (!Objects.equals(name, "")) {
                for (List<String> players : userConfig.value().getTeams().values()) {
                    if (players.indexOf(name) > -1) {
                        correctValue = true;
                        break;
                    }
                }
            } else {
                correctValue = true;
            }

            return correctValue;
        });

        names.values();


        return new ArrayList<>(names.values()).get(0)
            && new ArrayList<>(names.values()).get(1) ? null : issueCode;
    }
}
