package org.kek5.UDFs.Enrichers;

/**
 * Created by kek5 on 5/13/17.
 */

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF1;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Config.Columns.ActionCodeConfig;

@org.kek5.Annotations.UDF1
public class Action implements UDF1<String, String> {
    @AutowiredBroadcast
    Broadcast<ActionCodeConfig> code_description;

    @Override
    public String call(String code) throws Exception {
        return code_description.getValue().code_description.get(code);
    }
}
