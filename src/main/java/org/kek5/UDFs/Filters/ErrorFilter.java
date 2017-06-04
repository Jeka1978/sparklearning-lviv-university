package org.kek5.UDFs.Filters;

import org.apache.spark.sql.api.java.UDF3;

@org.kek5.Annotations.UDF3
public class ErrorFilter implements UDF3<String, String, String, Boolean> {

    @Override
    public Boolean call(String o, String o2, String o3) throws Exception {
        if(o.startsWith("Error") || o2.startsWith("Error") || o3.startsWith("Error")) {
            return false;
        } else {
            return true;
        }
    }
}
