package org.kek5.UDFs.Filters;

import org.apache.spark.sql.api.java.UDF1;
import org.kek5.Validators.Errors;

/**
 * Created by kek5 on 4/19/17.
 */
@org.kek5.Annotations.UDF1
public class TimeFilter implements UDF1<String, String> {

    @Override
    public String call(String time) throws Exception {
        int minutes = Integer.parseInt(time.split(":")[0]);
        int seconds = Integer.parseInt(time.split(":")[1]);
        if(minutes < 0 || minutes > 95) {
            return Errors.TIME_ERROR_COLUMN;
        } else if (seconds < 0 || seconds > 60) {
            return Errors.TIME_ERROR_COLUMN;
        } else {
            return "";
        }
    }
}
