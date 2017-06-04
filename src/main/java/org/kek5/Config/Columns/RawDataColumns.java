package org.kek5.Config.Columns;

import lombok.Getter;
import org.kek5.Annotations.ColumnQualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kek5 on 5/9/17.
 */

@PropertySource({"classpath:football_columns.properties"})
@ColumnQualifier(ColumnTypes.RAW_COLUMNS)
public class RawDataColumns implements ColumnConfig {
    protected List<String> columns;

    @Value("${columnNames}")
    public void setColumns(String[] columnNames) { this.columns = Arrays.asList(columnNames); }

    @Override
    public List<String> getColumns() {
        return this.columns;
    }
}
