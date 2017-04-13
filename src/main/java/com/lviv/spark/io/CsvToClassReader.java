package com.lviv.spark.io;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;

/**
 * Created by Anatoliy on 14.04.2017.
 */
public interface CsvToClassReader {
    JavaRDD<FootballAction> mapToRows(JavaRDD<String> lines);
}
