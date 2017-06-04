package org.kek5.Enrichers.RowEnrichers;

import lombok.SneakyThrows;
import org.apache.spark.sql.DataFrame;
import org.kek5.Validators.Errors;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * Created by kek5 on 6/4/17.
 */
@Service
public class TempColumnsRemover {
    @SneakyThrows
    public DataFrame remove(DataFrame df) {

        for (Field tempColumn : TempColumns.class.getFields()) {
            df = df.withColumn(((String) tempColumn.get(tempColumn.getName())).split("_")[1],
                    df.col((String) tempColumn.get(tempColumn.getName())));
        }
        for (Field tempColumn : TempColumns.class.getFields()) {
            df = df.drop((String) tempColumn.get(tempColumn.getName()));
        }

        return df;
    }
}
