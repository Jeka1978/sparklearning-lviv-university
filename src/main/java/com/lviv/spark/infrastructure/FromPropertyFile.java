package com.lviv.spark.infrastructure;

/**
 * Created by Anatoliy on 12.04.2017.
 */
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface FromPropertyFile {
    String fileName();
}
