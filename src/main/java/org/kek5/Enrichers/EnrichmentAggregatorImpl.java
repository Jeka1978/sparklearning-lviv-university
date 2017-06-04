package org.kek5.Enrichers;

import lombok.SneakyThrows;
import org.apache.spark.sql.DataFrame;
import org.kek5.Validators.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by kek5 on 5/13/17.
 */
@Service
public class EnrichmentAggregatorImpl implements EnrichmentAggregator {
    @Autowired
    private List<Enricher> enrichers;

    @SneakyThrows
    @Override
    public DataFrame encrich(DataFrame df) {

        for (Field errorColumn : Errors.class.getFields()) {
            df = df.drop((String) errorColumn.get(errorColumn.getName()));
        }

        for(Enricher enricher : enrichers) {
            df = enricher.enrich(df);
        }

        return df;
    }
}
