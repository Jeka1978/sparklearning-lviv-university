package org.kek5.Validators;

import lombok.SneakyThrows;
import org.apache.spark.sql.DataFrame;
import org.kek5.UDFs.Filters.TimeFilter;
import org.springframework.stereotype.Service;
import static org.apache.spark.sql.functions.*;

/**
 * Created by kek5 on 4/18/17.
 */
@Service
public class TimeValidator implements Validator {

    @SneakyThrows
    @Override
    public DataFrame validate(DataFrame df) {
        return df.withColumn(errorColumn(),
                callUDF(TimeFilter.class.getName(), col(ColumnsToValidate.EVENT_TIME_COLUMN)));
    }

    @Override
    public String errorColumn() {
        return Errors.TIME_ERROR_COLUMN;
    }
}
