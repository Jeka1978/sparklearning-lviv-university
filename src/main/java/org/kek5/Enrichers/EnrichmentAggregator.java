package org.kek5.Enrichers;

import lombok.Data;
import org.apache.spark.sql.DataFrame;

/**
 * Created by kek5 on 5/13/17.
 */

public interface EnrichmentAggregator {
    public DataFrame encrich(DataFrame df);
}
