package org.kek5.Utils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Annotations.ColumnQualifier;
import org.kek5.Config.Columns.ColumnConfig;
import org.kek5.Config.Columns.ColumnTypes;
import org.kek5.Config.Columns.ErrorColumns;
import org.kek5.Config.Columns.RawDataColumns;
import org.springframework.stereotype.Service;
import scala.Tuple2;


/**
 * Created by kek5 on 5/11/17.
 */
@Service
public class ErrorStatCollector {
    @AutowiredBroadcast
    Broadcast<RawDataColumns> columns;
    @AutowiredBroadcast
    Broadcast<ErrorColumns> errorColumns;
    @AutowiredBroadcast
    Broadcast<FromRows2DataFrame> fromRows2DataFrame;

    public DataFrame generateStatistics(DataFrame errorDF) {
        for (String column : columns.getValue().getColumns()) {
            errorDF = errorDF.drop(column);
        }

        JavaRDD<Row> rows = errorDF.toJavaRDD()
                .flatMap(ErrorStatUtil::getErrors)
                .mapToPair(error -> new Tuple2<>(error, "1"))
                .reduceByKey((acum, v) -> String.valueOf(Integer.valueOf(acum)+Integer.valueOf(v))) // coz I got string cols (
                .map(pair -> RowFactory.create(pair._1, pair._2));
        DataFrame errorStatDF = fromRows2DataFrame.getValue().create(rows, errorColumns.getValue());


        return errorStatDF;
    }
}
