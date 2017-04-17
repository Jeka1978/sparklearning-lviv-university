package com.lviv.spark.enrichment;

import com.lviv.spark.FootballAction;
import lombok.Setter;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Anatoliy on 16.04.2017.
 */
@Service
public class DataEnrichmentServiceImpl implements DataEnrichmentService {
    @Autowired
    List<DataEnrichmentAction> actions;

    @Override
    public JavaRDD<FootballAction> enrichData(JavaRDD<FootballAction> rdd) {
        for(DataEnrichmentAction action:actions) {
            rdd = action.execute(rdd);
        }
        return rdd;
    }
}
