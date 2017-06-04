package org.kek5.Enrichers;

import org.apache.spark.sql.DataFrame;
import org.kek5.UDFs.Enrichers.Half;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;


@Service
public class HalfEnricher implements Enricher {
    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(enrichedColumn(),
                callUDF(Half.class.getName(),
                        col(column2Enrich())));
    }

    @Override
    public String column2Enrich() {
        return Columns2Enrich.TIME;
    }

    @Override
    public String enrichedColumn() {
        return "matchHalf";
    }
}
