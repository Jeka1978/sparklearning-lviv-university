package com.lviv.football.configs;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by rudnitskih on 4/11/17.
 */
@Configuration
@ComponentScan(basePackages = {"com.lviv.football", "com.lviv.infra"})
@PropertySource("classpath:football_columns.properties")
public class MainConfig {
    @Autowired
    private SparkConf sparkConf;

    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(sparkConf);
    }
}
