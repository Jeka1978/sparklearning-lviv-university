package org.kek5.Utils;

import org.apache.spark.sql.DataFrame;
import static org.apache.spark.sql.functions.*;
import org.kek5.UDFs.Filters.ErrorFilter;
import org.kek5.Validators.Errors;
import org.springframework.stereotype.Service;
import java.io.Serializable;


/**
 * Created by kek5 on 5/5/17.
 */
@Service
public class ErrorCollector implements Serializable {
    public DataFrame collect(DataFrame df) { // would be easier with spark2.0 to pass list of columns to drop
        df = df.filter(callUDF(ErrorFilter.class.getName(),
                col(Errors.PLAYER_ERROR_COLUMN),
                col(Errors.TIME_ERROR_COLUMN),
                col(Errors.PARTICIPANT_ERROR_COLUMN)));

        return df;
    }
}
