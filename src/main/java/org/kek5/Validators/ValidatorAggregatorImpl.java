package org.kek5.Validators;

import lombok.SneakyThrows;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by kek5 on 4/18/17.
 */
@Service
public class ValidatorAggregatorImpl implements ValidatorAggregator {
    @Autowired
    private List<Validator> validators;

    @Override
    public DataFrame validate(DataFrame df) {
        for(Validator validator : validators) {
            df = validator.validate(df);
        }

        return df;
    }
}
