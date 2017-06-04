package org.kek5.Validators;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;

import java.io.Serializable;

/**
 * Created by kek5 on 4/18/17.
 */
public interface Validator extends Serializable {
    public DataFrame validate(DataFrame df);

    public String errorColumn();
}
