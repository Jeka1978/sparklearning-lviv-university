package org.kek5.UDFs.Enrichers;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF1;
import org.kek5.Annotations.AutowiredBroadcast;
import org.kek5.Config.Columns.ActionCodeConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kek5 on 6/4/17.
 */
@org.kek5.Annotations.UDF1
public class NewAction implements UDF1<String, String> {
    @AutowiredBroadcast
    Broadcast<ActionCodeConfig> codeConfig;

    @Override
    public String call(String code) throws Exception {
        if(code.equals("3")) {
            return codeConfig.getValue().code_description.get("4");
        } else {
            return codeConfig.getValue().code_description.get("3");
        }
    }
}
