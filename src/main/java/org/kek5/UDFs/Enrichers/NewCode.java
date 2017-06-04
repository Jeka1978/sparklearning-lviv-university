package org.kek5.UDFs.Enrichers;

import org.apache.spark.sql.api.java.UDF1;

/**
 * Created by kek5 on 6/4/17.
 */
@org.kek5.Annotations.UDF1
public class NewCode implements UDF1<String, String> {

    @Override
    public String call(String code) throws Exception {
        if(code.equals("4")) {
            return "3";
        } else {
            return "4";
        }
    }
}
