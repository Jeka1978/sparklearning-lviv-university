package com.lviv.songs;

import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Evegeny on 11/03/2017.
 */
public interface WordService extends Serializable {
    List<String> topX(JavaRDD<String> lines, int x);
}
