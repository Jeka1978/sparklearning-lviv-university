package org.kek5.Enrichers.RowEnrichers;

import lombok.Data;
import org.apache.spark.sql.DataFrame;


/**
 * Created by kek5 on 6/1/17.
 */
public interface RowEnricher {
    public DataFrame enrich(DataFrame df);
}
