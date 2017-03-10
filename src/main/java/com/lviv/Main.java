package com.lviv;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.fluttercode.datafactory.impl.DataFactory;

/**
 * Created by Evegeny on 10/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("taxi").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> rdd = sc.textFile("data/taxi/trips.txt");
        long count = rdd.count();
        System.out.println("count = " + count);

    }
}
