package com.lviv.football.printers;

import com.lviv.football.ActionInfo;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.List;

/**
 * Created by rudnitskih on 4/15/17.
 */
public interface Printer {
    void printActions(JavaRDD<ActionInfo> actionsWithValidationIssuesRDD,
                      int amountLines,
                      String[] columns,
                      String message);

    void printValidationIssues(List<Tuple2<Integer, String>> amountIssuesByCode);
}
