package org.kek5.Validators;

import org.apache.spark.sql.DataFrame;
import org.kek5.UDFs.Filters.PlayerFilter;

import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import static org.apache.spark.sql.functions.*;

/**
 * Created by kek5 on 4/19/17.
 */
@Service
public class PlayerValidator implements Validator{


    @Override
    public DataFrame validate(DataFrame df) {
        return df.withColumn(errorColumn(),
                        callUDF(PlayerFilter.class.getName(),
                                col(ColumnsToValidate.FROM_COLUMN),
                                col(ColumnsToValidate.TO_COLUMN)));
    }

    @Override
    public String errorColumn() {
        return Errors.PLAYER_ERROR_COLUMN;
    }
}
