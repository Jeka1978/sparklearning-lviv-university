package org.kek5.UDFs.Filters;


import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF2;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Config.TeamConfig;
import org.kek5.Validators.Errors;

/**
 * Created by kek5 on 4/19/17.
 */
@org.kek5.Annotations.UDF2
public class PlayerFilter implements UDF2<String, String, String> {
    @AutowiredBroadcast
    private Broadcast<TeamConfig> teamConfig;

    @Override
    public String call(String from, String to) throws Exception {
        if(teamConfig.value().getPlayer2team().get(from) == null ||
                teamConfig.value().getPlayer2team().get(to) == null ) {
            if(from == null || to == null) {
                return "";
            } else {
                return Errors.PLAYER_ERROR_COLUMN;
            }
        }
        else {
            return "";
        }
    }
}
