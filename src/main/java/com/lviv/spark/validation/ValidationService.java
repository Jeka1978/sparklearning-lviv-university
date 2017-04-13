package com.lviv.spark.validation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import scala.Tuple2;

/**
 * Created by Anatoliy on 05.04.2017.
 */
public interface ValidationService {
    Tuple2<JavaRDD<Row>,ValidationResults> validateAndFilter(JavaRDD<Row> rows);
}
