package org.kek5;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.kek5.Enrichers.EnrichmentAggregator;
import org.kek5.Enrichers.RowEnrichers.RowEnricherAggregator;
import org.kek5.Utils.ErrorCollector;
import org.kek5.Utils.ErrorStatCollector;
import org.kek5.Utils.FromStrings2DataFrame;
import org.kek5.Validators.ValidatorAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kek5 on 4/18/17.
 */
@Service
public class FootballService {
    @Autowired
    private JavaSparkContext sc;
    @Autowired
    private FromStrings2DataFrame fromStrings2DataFrame;
    @Autowired
    private ValidatorAggregator validatorAggregator;
    @Autowired
    private ErrorCollector errorCollector;
    @Autowired
    private ErrorStatCollector errorStatCollector;
    @Autowired
    private EnrichmentAggregator enricher;
    @Autowired
    private RowEnricherAggregator rowEnricher;


    private String path = "data/rawData.txt";


    public void doWork() {
        JavaRDD<String> rawStrings = sc.textFile(path);
        DataFrame df = fromStrings2DataFrame.create(rawStrings);
        df = validatorAggregator.validate(df);

        df.persist();

        DataFrame validDF = errorCollector.collect(df);
        DataFrame errorDF = df.except(validDF);
        DataFrame errorStatistics = errorStatCollector.generateStatistics(errorDF);

        DataFrame enrichedDF = enricher.encrich(validDF);

        enrichedDF.persist();

        DataFrame enrichedRows = rowEnricher.enrich(enrichedDF);
        DataFrame result = enrichedDF.unionAll(enrichedRows).distinct();

        errorStatistics.show();
        result.show();
    }
}
