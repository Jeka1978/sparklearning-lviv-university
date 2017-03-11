package com.lviv.songs;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Configuration
@ComponentScan(basePackages = {"com.lviv.songs"})
@PropertySource("classpath:user.properties")
public class StartConfig {
    @Autowired
    private SparkConf sparkConf;

    @Bean
    public JavaSparkContext sc(){
        return new JavaSparkContext(sparkConf);
    }

    @Bean
    public SQLContext sqlContext(){
        return new SQLContext(sc());
    }
}







