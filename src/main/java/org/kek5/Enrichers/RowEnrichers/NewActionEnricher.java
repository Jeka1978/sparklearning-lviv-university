package org.kek5.Enrichers.RowEnrichers;

import org.apache.spark.sql.DataFrame;
import org.kek5.Enrichers.Columns2Enrich;
import org.kek5.UDFs.Enrichers.NewAction;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * Created by kek5 on 6/4/17.
 */
@Service
public class NewActionEnricher implements RowEnricher {
    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(TempColumns.newDiscription, callUDF(NewAction.class.getName(), col(Columns2Enrich.ACTION)));
    }
}
