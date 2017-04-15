package com.lviv.football.validators;

import com.lviv.football.ActionInfo;
import com.lviv.football.constants.Columns;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * Created by rudnitskih on 4/13/17.
 */
@Component
public class IncorrectTime implements ActionValidator {
    @Getter
    private String issueCode = "1";

    @Getter
    private String description = "Time is no correct";

    @Override
    public String validate(ActionInfo action) {
        String startTime = action.getProperty(Columns.startTime);
        String eventTime = action.getProperty(Columns.eventTime);
        Boolean correctStartTime = true;
        Boolean correctEventTime = true;

        if (!Objects.equals(startTime, "")) {
            String[] startTimeParts = startTime.split(":");

            // in a day less 24 hours
            // in an hour less 60 mins
            correctEventTime = Integer.parseInt(startTimeParts[0]) < 24 && Integer.parseInt(startTimeParts[1]) < 60;
        }

        if (!Objects.equals(eventTime, "") && correctStartTime) {
            String[] eventTimeParts = eventTime.split(":");

            // football game has less than 120 min
            // one minute has less than 60 secs
            correctStartTime = Integer.parseInt(eventTimeParts[0]) < 120 && Integer.parseInt(eventTimeParts[1]) < 60;
        }

        return correctStartTime && correctEventTime ? null : issueCode;
    }
}
