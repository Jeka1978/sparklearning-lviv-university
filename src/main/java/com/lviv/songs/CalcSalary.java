package com.lviv.songs;

import com.lviv.songs.infra.UDF;
import org.apache.spark.sql.api.java.UDF1;

/**
 * Created by Evegeny on 11/03/2017.
 */
@UDF
public class CalcSalary implements UDF1<Long,Long> {
    @Override
    public Long call(Long age) throws Exception {
        return age*15;
    }
}
