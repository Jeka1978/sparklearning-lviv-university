package org.kek5.Utils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Config.Columns.ColumnConfig;
import org.kek5.Config.Columns.RawDataColumns;
import org.springframework.stereotype.Service;

/**
 * Created by kek5 on 4/17/17.
 */
@Service
public class FromStrings2DataFrame implements DataFrameCreator<String> {
    @AutowiredBroadcast
    private Broadcast<RowCreator> rowCreator;
    @AutowiredBroadcast
    private Broadcast<FromRows2DataFrame> fromRow2DataFrameCreator;

    @Override
    public DataFrame create(JavaRDD<String> rdd, ColumnConfig columnConfig) {
        JavaRDD<Row> rowRDD = rdd.map(line -> rowCreator.value().line2Row(line, columnConfig));

        return fromRow2DataFrameCreator.value().create(rowRDD, columnConfig);
    }

    @Override
    public DataFrame create(JavaRDD<String> rawStrings) {
        rawStrings = rawStrings.filter(str -> !str.isEmpty());
        JavaRDD<Row> rowRDD = rawStrings.map(line -> rowCreator.value().line2Row(line));

        return fromRow2DataFrameCreator.value().create(rowRDD);
    }
}
