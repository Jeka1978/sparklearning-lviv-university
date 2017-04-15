package com.lviv.football.configs.sparkConfigs;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.lviv.songs.constants.Profiles.DEV;

/**
 * Created by rudnitskih on 4/11/17.
 */
@Configuration
@Profile(DEV)
public class DevConfig {
    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("football").setMaster("local[*]");

        return sparkConf;
    }
}
