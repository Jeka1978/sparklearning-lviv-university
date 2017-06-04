package org.kek5.UDFs.Enrichers;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF1;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Config.TeamConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kek5 on 5/13/17.
 */
@org.kek5.Annotations.UDF1
public class Team implements UDF1<String, String> {
    @AutowiredBroadcast
    Broadcast<TeamConfig> teamConfig;

    @Override
    public String call(String player) throws Exception {
        return teamConfig.getValue().getPlayer2team().get(player);
    }
}
