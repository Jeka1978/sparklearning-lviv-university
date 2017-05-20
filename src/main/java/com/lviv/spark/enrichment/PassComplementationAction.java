package com.lviv.spark.enrichment;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Anatoliy on 20.05.2017.
 */
@Order(value=-10)
@Component
public class PassComplementationAction implements DataEnrichmentAction {

    @Override
    public JavaRDD<FootballAction> execute(JavaRDD<FootballAction> rdd) {
        return rdd.flatMap(action -> {
            ArrayList<FootballAction> result = new ArrayList<>();
            result.add(action);

            if(action.getCode() == 3) {
                FootballAction action2 = action.publicClone();

                action2.setCode(4);
                action2.setTo(action.getFrom());
                action2.setFrom(action.getTo());
                result.add(action2);
            }

            return result;
        });
    }
}
