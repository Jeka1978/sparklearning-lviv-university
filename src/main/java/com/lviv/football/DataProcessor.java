package com.lviv.football;

import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rudnitskih on 4/12/17.
 */
public interface DataProcessor extends Serializable {
    List<Tuple2<Integer, String>> getAmountIssuesByCode(JavaRDD<ActionInfo> actionsRDD);

    JavaRDD<ActionInfo> getActionsWithValidationIssues(JavaRDD<String> originalRdd);

    JavaRDD<ActionInfo> getExtendedData(JavaRDD<ActionInfo> actionsWithValidationIssuesRDD);
}
