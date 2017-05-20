package com.lviv.taxi;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

/**
 * Created by Evegeny on 11/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("taxi").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> rdd = sc.textFile("data/taxi/trips.txt");
        rdd.persist(StorageLevel.MEMORY_AND_DISK());
        long count = rdd.count();
        System.out.println("count = " + count);

        JavaRDD<Trip> trips = rdd.map(String::toLowerCase).
                map(line -> {
                    String[] arr = line.split(" ");
                    return new Trip(arr[0], arr[1], Integer.parseInt(arr[2]));
                });


        trips.persist(StorageLevel.MEMORY_AND_DISK());

        long longTripsToBoston = trips.filter(trip -> trip.getKm() > 10)
                .filter(trip -> trip.getCity().equals("boston")).count();

        JavaPairRDD<String, Integer> pairRDD = trips.mapToPair(trip -> new Tuple2<>(trip.getId(), trip.getKm()));

        System.out.println("longTripsToBoston = " + longTripsToBoston);

        Double bostonTotal = trips.filter(trip -> trip.getCity().equals("boston"))
                .mapToDouble(Trip::getKm).sum();
        int[] total=new int[1];
        trips.foreach(trip -> total[0]++);

        System.out.println("bostonTotal = " + bostonTotal);
    }
}
