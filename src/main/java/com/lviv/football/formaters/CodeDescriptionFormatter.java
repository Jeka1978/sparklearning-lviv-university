package com.lviv.football.formaters;

import com.lviv.football.ActionInfo;
import com.lviv.football.configs.UserConfig;
import com.lviv.football.constants.Columns;
import com.lviv.infra.AutowiredBroadcast;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by rudnitskih on 4/15/17.
 */
@Component
public class CodeDescriptionFormatter implements ActionFormatter {
    @AutowiredBroadcast
    Broadcast<UserConfig> userConfig;

    @Override
    public ActionInfo addAdditionalData(ActionInfo action) {
        String code = action.getProperty(Columns.code);
        String codeDescription;

        if (code != null) {
            for (Map.Entry<String, String> codeEntry : userConfig.value().codes.entrySet()) {
                if (codeEntry.getKey().equals(code)) {
                    codeDescription = codeEntry.getValue();

                    action.addProperty(Columns.actionDescription + "=" + codeDescription);
                    break;
                }
            }
        }

        return action;
    }
}
