package org.kek5.Utils;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Annotations.ColumnQualifier;
import org.kek5.Config.Columns.ColumnConfig;
import org.kek5.Config.Columns.ColumnTypes;
import org.kek5.Config.Columns.RawDataColumns;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kek5 on 4/18/17.
 */
@Service
public class RowCreator implements Serializable{
    @AutowiredBroadcast
    private Broadcast<RawDataColumns> rawColumn;

    public Row line2Row(String line, ColumnConfig columnConfig) {
        Map<String, String> temp = FieldMapperUtil.line2Map(line);
        List<String> rowValues = new ArrayList<>();

        for(String column: columnConfig.getColumns()) {
            rowValues.add(temp.get(column));
        }

        return RowFactory.create(rowValues.toArray());
    }

    public Row line2Row(String line) {
        Map<String, String> temp = FieldMapperUtil.line2Map(line);
        List<String> rowValues = new ArrayList<>();

        for(String column: rawColumn.value().getColumns()) {
            rowValues.add(temp.get(column));
        }

        return RowFactory.create(rowValues.toArray());
    }

}
