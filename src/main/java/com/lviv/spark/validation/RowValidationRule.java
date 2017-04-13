package com.lviv.spark.validation;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * Created by Anatoliy on 05.04.2017.
 */
public interface RowValidationRule {
    JavaRDD<Row> validate(JavaRDD<Row> rdd);

    String columnName();

    String[] requiredColumns();
}