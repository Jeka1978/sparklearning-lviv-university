package com.lviv.spark.validation.action;

import com.lviv.spark.FootballAction;
import com.lviv.spark.validation.ValidationResults;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

/**
 * Created by Anatoliy on 05.04.2017.
 */
public interface ActionValidationService {
    Tuple2<JavaRDD<FootballAction>,ValidationResults> validateAndFilter(JavaRDD<FootballAction> rows);
}
