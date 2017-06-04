package org.kek5.Enrichers.RowEnrichers;

import org.apache.spark.sql.DataFrame;
import org.kek5.Enrichers.Columns2Enrich;
import org.kek5.UDFs.Enrichers.NewCode;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

@Service
public class NewCodeEnricher implements RowEnricher {
    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(TempColumns.newCode,
                callUDF(NewCode.class.getName(), col(Columns2Enrich.ACTION)));
    }
}
