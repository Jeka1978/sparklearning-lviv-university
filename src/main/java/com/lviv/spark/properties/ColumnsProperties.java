package com.lviv.spark.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anatoliy on 12.04.2017.
 */
@Component
public class ColumnsProperties implements Serializable {
    public List<String> columnNames;

    @Value("${columnNames}")
    private void setColumnNames(String[] names) {
        this.columnNames = Arrays.asList(names);
    }

    public int getColumnIndex(String columnName) {
        return columnNames.indexOf(columnName);
    }

    public Boolean columnExists(String columnName) {
        return columnNames.contains(columnName);
    }

    public int getNumberOfColumns() {
        return columnNames.size();
    }
}
