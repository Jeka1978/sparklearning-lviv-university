package com.lviv.spark.io;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;

/**
 * Created by Anatoliy on 14.04.2017.
 */
public interface CsvToActionReader {
    JavaRDD<FootballAction> mapToActions(JavaRDD<String> lines);
}
