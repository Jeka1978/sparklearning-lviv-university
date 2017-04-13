package com.lviv.spark.conf;

import com.lviv.spark.constants.Application;
import com.lviv.spark.constants.Profiles;
import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(Profiles.DEV)
public class DevConfig {
    @Bean
    public SparkConf sparkConf(){
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName(Application.APP_NAME).setMaster("local[*]");
        return sparkConf;
    }
}
