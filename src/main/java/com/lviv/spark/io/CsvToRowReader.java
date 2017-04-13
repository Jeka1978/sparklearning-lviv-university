package com.lviv.spark.io;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;

/**
 * Created by Anatoliy on 12.04.2017.
 */
public interface CsvToRowReader {
    JavaRDD<Row> mapToRows(JavaRDD<String> lines);
}
