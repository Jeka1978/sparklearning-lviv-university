package com.lviv.songs.infra;/**
 * Created by Evegeny on 11/03/2017.
 */

import org.apache.spark.sql.types.DataType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Component
public @interface UDF {
}
