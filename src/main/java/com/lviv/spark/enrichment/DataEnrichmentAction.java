package com.lviv.spark.enrichment;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;

import java.util.List;

/**
 * Created by Anatoliy on 16.04.2017.
 */
public interface DataEnrichmentAction {
    JavaRDD<FootballAction> execute(JavaRDD<FootballAction> rdd);
}
