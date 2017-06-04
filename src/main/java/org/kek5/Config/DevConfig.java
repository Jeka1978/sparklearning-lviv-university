package org.kek5.Config;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by kek5 on 4/17/17.
 */
@Configuration
public class DevConfig {
    @Bean
    public SparkConf sparkConf() {
        return new SparkConf().setMaster("local[*]").setAppName("FootballService");
    }
}
