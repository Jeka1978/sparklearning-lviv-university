package com.lviv.spark.enrichment;

import com.lviv.spark.FootballAction;
import com.lviv.spark.utils.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.Serializable;

/**
 * Created by Anatoliy on 16.04.2017.
 */
@Component
public class TimeNumberAction implements DataEnrichmentAction,Serializable {
    @Override
    public JavaRDD<FootballAction> execute(JavaRDD<FootballAction> rdd) {
        return rdd.map(action -> {
            Tuple2<Integer, Integer> eventTime = StringUtils.parseTimeString(action.getEventTime());

            if(eventTime._1() < 44) {
                action.setTimeNumber(1);
            } else {
                action.setTimeNumber(2);
            }

            return action;
        });
    }
}
