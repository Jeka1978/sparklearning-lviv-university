package com.lviv.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Anatoliy on 05.04.2017.
 */
@Configuration
@ComponentScan(basePackages = {"com.lviv.spark", "com.lviv.songs.infra"})
@PropertySource("classpath:application.properties")
@PropertySource("classpath:football_columns.properties")
@PropertySource("classpath:football_files.properties")
public class Config {
    @Autowired
    private SparkConf sparkConf;

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(sparkConf);
    }

    @Bean
    public SQLContext sqlContext(){
        return new SQLContext(sc());
    }
}
