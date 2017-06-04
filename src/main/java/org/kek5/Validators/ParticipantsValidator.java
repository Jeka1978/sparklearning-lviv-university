package org.kek5.Validators;

import org.apache.spark.sql.DataFrame;
import org.kek5.UDFs.Filters.ParticipantsFilter;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.*;
/**
 * Created by kek5 on 4/19/17.
 */
@Service
public class ParticipantsValidator implements Validator {
    @Override
    public DataFrame validate(DataFrame df) {
        return df.withColumn(errorColumn(),
                callUDF(ParticipantsFilter.class.getName(),
                        col(ColumnsToValidate.CODE_COLUMN), col(ColumnsToValidate.FROM_COLUMN), col(ColumnsToValidate.TO_COLUMN)));
    }

    @Override
    public String errorColumn() {
        return Errors.PARTICIPANT_ERROR_COLUMN;
    }
}
