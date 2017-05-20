package com.lviv.spark.enrichment;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.FootballAction;
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
public class PlayerTeamAction implements DataEnrichmentAction,Serializable {
    @AutowiredBroadcast
    private Broadcast<FootballTeamsProperties> teamsProperties;

    @Override
    public JavaRDD<FootballAction> execute(JavaRDD<FootballAction> rdd) {
        return rdd.map(action -> {
            FootballTeamsProperties td = teamsProperties.value();

            String playerTo = action.getTo();
            String playerFrom = action.getFrom();

            if(StringUtils.isEmpty(playerTo)) {
                action.setTeamName(td.GetPlayerTeam(playerFrom));
            } else {
                action.setTeamName(td.GetPlayerTeam(playerTo));
            }

            return action;
        });
    }
}
