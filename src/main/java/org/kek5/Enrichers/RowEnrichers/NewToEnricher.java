package org.kek5.Enrichers.RowEnrichers;

import org.apache.spark.sql.DataFrame;
import org.springframework.stereotype.Service;

/**
 * Created by kek5 on 6/4/17.
 */
@Service
public class NewToEnricher implements RowEnricher{

    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(TempColumns.newTo, df.col(TempColumns.newFrom.split("_")[1]));
    }
}
