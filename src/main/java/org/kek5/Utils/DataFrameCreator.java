package org.kek5.Utils;

import lombok.Data;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.kek5.Config.Columns.ColumnConfig;

import java.io.Serializable;

/**
 * Created by kek5 on 4/25/17.
 */
public interface DataFrameCreator<T> extends Serializable {
    public DataFrame create(JavaRDD<T> rdd, ColumnConfig columnConfig);
    public DataFrame create(JavaRDD<T> rdd);
}
