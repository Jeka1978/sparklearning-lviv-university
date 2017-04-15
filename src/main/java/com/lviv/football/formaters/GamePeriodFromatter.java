package com.lviv.football.formaters;

import com.lviv.football.ActionInfo;
import com.lviv.football.constants.Columns;
import org.springframework.stereotype.Component;

/**
 * Created by rudnitskih on 4/15/17.
 */
@Component
public class GamePeriodFromatter implements ActionFormatter {
    @Override
    public ActionInfo addAdditionalData(ActionInfo action) {
        String eventTime = action.getProperty(Columns.eventTime);

        if (eventTime != null) {
            int minutesFromStart = Integer.parseInt(eventTime.split(":")[0]);

            String gamePeriod = minutesFromStart < 45 ? "First period" : "Second Period";

            action.addProperty(Columns.period + "=" + gamePeriod);
        }

        return action;
    }
}
