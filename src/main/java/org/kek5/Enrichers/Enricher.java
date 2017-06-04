package org.kek5.Enrichers;

import org.apache.spark.sql.DataFrame;

import java.io.Serializable;

/**
 * Created by kek5 on 5/13/17.
 */
public interface Enricher extends Serializable{
    DataFrame enrich(DataFrame df);
    String column2Enrich();
    String enrichedColumn();
}
