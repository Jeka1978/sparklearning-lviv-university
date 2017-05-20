package com.lviv.spark.validation.action;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;

/**
 * Created by Anatoliy on 05.04.2017.
 */
public interface ActionValidationRule {
    JavaRDD<FootballAction> validate(JavaRDD<FootballAction> rdd);

    String columnName();

    String[] requiredColumns();
}