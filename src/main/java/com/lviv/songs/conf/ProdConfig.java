package com.lviv.songs.conf;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Configuration
@Profile("PROD")
public class ProdConfig {
    @Bean
    public SparkConf sparkConf(){
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("songs");
        return sparkConf;
    }
}
