package org.kek5.Utils;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Annotations.ColumnQualifier;
import org.kek5.Config.Columns.ColumnConfig;
import org.kek5.Config.Columns.ColumnTypes;
import org.kek5.Config.Columns.RawDataColumns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by kek5 on 4/25/17.
 */
@Service
public class FromRows2DataFrame implements DataFrameCreator<Row> {
    @Autowired
    SQLContext sqlContext;
    @AutowiredBroadcast
    Broadcast <StructFieldCreator> structCreator;
    @AutowiredBroadcast
    Broadcast <RawDataColumns> rawColumns;

    @Override
    public DataFrame create(JavaRDD<Row> rdd, ColumnConfig columnConfig) {
        StructField[] fields = structCreator.value().create(columnConfig);
        return sqlContext.createDataFrame(rdd, DataTypes.createStructType(fields));
    }

    @Override
    public DataFrame create(JavaRDD<Row> rows) {
        StructField[] fields = structCreator.value().create(rawColumns.value());
        return sqlContext.createDataFrame(rows, DataTypes.createStructType(fields));
    }
}
