package org.kek5.UDFs.Enrichers;

import org.apache.spark.sql.api.java.UDF1;

/**
 * Created by kek5 on 5/13/17.
 */
@org.kek5.Annotations.UDF1
public class Half implements UDF1<String, String> {
    @Override
    public String call(String eventTime) throws Exception {
        int minutes = Integer.valueOf(eventTime.split(":")[0]);
        if(minutes < 45) {
            return "First half";
        } else {
            return "Second half";
        }
    }
}
