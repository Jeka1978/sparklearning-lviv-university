package org.kek5.Config.Columns;

import lombok.SneakyThrows;
import org.kek5.Annotations.ColumnQualifier;
import org.kek5.Validators.Errors;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kek5 on 5/9/17.
 */
@ColumnQualifier(ColumnTypes.ERROR_COLUMNS)
public class ErrorColumns implements ColumnConfig {
    public final static String ERROR_NAME = "Error name";
    public final static String COUNT = "Count";
    private List<String> columns = new ArrayList<>();

    @SneakyThrows
    @PostConstruct
    public void setColumns() {
        this.columns.add(ERROR_NAME);
        this.columns.add(COUNT);
    }

    @Override
    public List<String> getColumns() {
        return this.columns;
    }
}
