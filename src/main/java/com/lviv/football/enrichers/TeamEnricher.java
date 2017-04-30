package com.lviv.football.enrichers;

import com.lviv.football.ActionInfo;
import com.lviv.football.configs.UserConfig;
import com.lviv.infra.AutowiredBroadcast;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.lviv.football.constants.Columns.from;

/**
 * Created by rudnitskih on 4/15/17.
 */
@Component
public class TeamEnricher implements DataEnricher {
    @AutowiredBroadcast
    Broadcast<UserConfig> userConfig;

    @Override
    public ActionInfo addAdditionalData(ActionInfo action) {
        String fromName = action.getProperty(from);

        if (fromName != null) {
            for (Map.Entry<String, List<String>> entry : userConfig.value().getTeams().entrySet()) {
                String team = entry.getKey();
                List<String> players = entry.getValue();

                if (players.indexOf(fromName) > -1) {
                    action.addProperty("team=" + team);
                    break;
                }
            }
        }

        return action;
    }
}
