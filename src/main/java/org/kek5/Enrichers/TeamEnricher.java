package org.kek5.Enrichers;

import org.apache.spark.sql.DataFrame;
import org.kek5.UDFs.Enrichers.Half;
import org.kek5.UDFs.Enrichers.Team;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

/**
 * Created by kek5 on 5/13/17.
 */
@Service
public class TeamEnricher implements Enricher {
    @Override
    public DataFrame enrich(DataFrame df) {
        return df.withColumn(enrichedColumn(),
                callUDF(Team.class.getName(),
                        col(column2Enrich())));
    }

    @Override
    public String column2Enrich() {
        return Columns2Enrich.TEAM;
    }

    @Override
    public String enrichedColumn() {
        return "team";
    }
}
