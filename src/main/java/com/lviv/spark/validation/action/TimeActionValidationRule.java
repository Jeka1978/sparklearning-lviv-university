package com.lviv.spark.validation.action;

import com.lviv.spark.FootballAction;
import com.lviv.spark.utils.StringUtils;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.Serializable;

/**
 * Created by Anatoliy on 11.04.2017.
 */
@Component
public class TimeActionValidationRule extends ActionValidationRuleBase implements Serializable {

    @Override
    protected boolean actionIsInvalid(FootballAction action) {
        String eventTime = action.getEventTime();
        if(eventTimeInvalid(eventTime)) {
            return true;
        } else {
            String startTime = action.getStartTime();
            if(startTimeInvalid(startTime)) {
                return true;
            }
        }
        return false;
    }

    private boolean startTimeInvalid(String startTime) {
        try {
            Tuple2<Integer, Integer> timeValues = StringUtils.parseTimeString(startTime);
            Integer minutes = timeValues._2();
            if(minutes < 0 || minutes > 59) return true;

            Integer hours = timeValues._1();
            if(hours < 0 || hours > 23) return true;
        } catch(Exception e) {
            return true;
        }
        return false;
    }

    private boolean eventTimeInvalid(String eventTime) {
        try {
            Tuple2<Integer, Integer> timeValues = StringUtils.parseTimeString(eventTime);
            Integer seconds = timeValues._2();
            if(seconds < 0 || seconds > 59) return true;

            Integer minutes = timeValues._1();
            if(minutes < 0 || minutes > 89) return true;
        } catch(Exception e) {
            return true;
        }

        return false;
    }

    @Override
    public String columnName() {
        return "TimeInvalid";
    }

    @Override
    public String[] requiredColumns() {
        return new String[]{"eventTime", "startTime"};
    }
}
