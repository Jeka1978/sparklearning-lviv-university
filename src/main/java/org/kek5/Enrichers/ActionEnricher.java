package org.kek5.Enrichers;

import org.apache.spark.sql.DataFrame;
import org.kek5.UDFs.Enrichers.Action;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * Created by kek5 on 5/13/17.
 */
@Service
public class ActionEnricher implements Enricher {
    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(enrichedColumn(),
                callUDF(Action.class.getName(),
                        col(column2Enrich())));
    }

    @Override
    public String column2Enrich() {
        return Columns2Enrich.ACTION;
    }

    @Override
    public String enrichedColumn() {
        return "actionDescription";
    }
}
