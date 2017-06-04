package org.kek5.Enrichers.RowEnrichers;

import org.apache.spark.sql.DataFrame;
import org.springframework.stereotype.Service;


@Service
public class NewFromEnricher implements RowEnricher {

    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(TempColumns.newFrom, df.col(TempColumns.newTo.split("_")[1]));

    }
}
