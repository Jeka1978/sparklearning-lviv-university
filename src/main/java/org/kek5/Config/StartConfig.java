package org.kek5.Config;

import lombok.Getter;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kek5 on 4/17/17.
 */
@Configuration
@ComponentScan("org.kek5")
@Getter
public class StartConfig {
    @Autowired
    private SparkConf sparkConf;

    @Bean
    public JavaSparkContext sc() { return new JavaSparkContext(this.sparkConf); }

    @Bean
    public SQLContext sqlContext() { return new SQLContext(sc()); }

}
