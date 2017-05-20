package com.lviv.spark.validation.action;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;

/**
 * Created by Anatoliy on 05.04.2017.
 */
public abstract class ActionValidationRuleBase implements ActionValidationRule {
    @Override
    public JavaRDD<FootballAction> validate(JavaRDD<FootballAction> rdd) {
        return rdd.map(action -> {
            if(actionIsInvalid(action)) {
                action.getValidationErrors().add(this.columnName());
            }
            return action;
        });
    }

    protected abstract boolean actionIsInvalid(FootballAction action);
}