package com.lviv.spark.io;

import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;

/**
 * Created by Anatoliy on 16.04.2017.
 */
public interface ActionToCsvWriter {
    void write(JavaRDD<FootballAction> rdd, String fileName);
}
