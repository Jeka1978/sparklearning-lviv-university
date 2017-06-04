package org.kek5.Utils;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Config.Columns.ColumnConfig;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by kek5 on 4/25/17.
 */
@Service
public class StructFieldCreator implements Serializable {

    public StructField[] create(ColumnConfig columnConfig) {
        StructField[] fields = new StructField[columnConfig.getColumns().size()];
        for(int i = 0; i < fields.length; i++) {
            fields[i] = DataTypes.createStructField(columnConfig.getColumns().get(i), DataTypes.StringType, true);
        }
        return fields;
    }
}
