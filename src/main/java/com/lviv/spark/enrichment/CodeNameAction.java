package com.lviv.spark.enrichment;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.FootballAction;
import com.lviv.spark.properties.FootballCodesProperties;
import com.lviv.spark.properties.FootballTeamsProperties;
import com.lviv.spark.utils.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Anatoliy on 16.04.2017.
 */
@Component
public class CodeNameAction implements DataEnrichmentAction,Serializable {
    @AutowiredBroadcast
    private Broadcast<FootballCodesProperties> codesProperties;
    @Override
    public JavaRDD<FootballAction> execute(JavaRDD<FootballAction> rdd) {
        return rdd.map(action -> {
            FootballCodesProperties cp = codesProperties.value();
            
            action.setCodeName(cp.getCodeName(action.getCode()));

            return action;
        });
    }
}
