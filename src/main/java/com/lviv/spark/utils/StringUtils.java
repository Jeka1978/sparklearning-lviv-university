package com.lviv.spark.utils;

import scala.Tuple2;

/**
 * Created by Anatoliy on 16.04.2017.
 */
public class StringUtils {
    public static boolean isEmpty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

    public static Tuple2<Integer,Integer> parseTimeString(String eventTime) throws Exception {
        String[] parts = eventTime.split(":");
        if(parts.length != 2)
            throw new Exception("Invalid time format");
        int seconds = Integer.parseInt(parts[1]);
        int minutes = Integer.parseInt(parts[0]);
        return new Tuple2<>(minutes,seconds);
    }
}
