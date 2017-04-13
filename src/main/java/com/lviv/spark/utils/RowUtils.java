package com.lviv.spark.utils;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 13.04.2017.
 */
public class RowUtils {
    public static Row AddValueToRow(Row row, Object value) {
        List<Object> vals = new ArrayList<>();
        for(int i = 0; i < row.size(); i++) {
            vals.add(row.get(i));
        }
        vals.add(value);
        return RowFactory.create(vals.toArray());
    }

    public static Row TakeColumns(Row row, int n) {
        List<Object> vals = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            vals.add(row.get(i));
        }
        return RowFactory.create(vals.toArray());
    }
}
