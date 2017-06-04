package org.kek5.Validators;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;

/**
 * Created by kek5 on 4/18/17.
 */
public interface ValidatorAggregator {
    public DataFrame validate(DataFrame df);

}
