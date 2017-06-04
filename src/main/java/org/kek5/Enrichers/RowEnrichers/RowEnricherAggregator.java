package org.kek5.Enrichers.RowEnrichers;

import org.apache.spark.sql.DataFrame;
import org.kek5.Config.Columns.ActionCodeConfig;
import org.kek5.Enrichers.Columns2Enrich;
import org.kek5.UDFs.Enrichers.NewCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.spark.sql.functions.*;

/**
 * Created by kek5 on 6/2/17.
 */
@Service
public class RowEnricherAggregator {
    @Autowired
    private List<RowEnricher> enrichers;
    @Autowired
    private TempColumnsRemover remover;

    public DataFrame enrich(DataFrame df) {
        DataFrame rows2enrich = df.filter(
                df.col(Columns2Enrich.ACTION).equalTo("4").or(df.col(Columns2Enrich.ACTION).equalTo("3")));

        for(RowEnricher enricher: enrichers) {
            rows2enrich = enricher.enrich(rows2enrich);
        }

        rows2enrich = remover.remove(rows2enrich);

        return rows2enrich;
    }

}
