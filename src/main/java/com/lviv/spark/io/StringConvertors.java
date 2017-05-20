package com.lviv.spark.io;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.spark.api.java.function.Function;

/**
 * Created by Anatoliy on 17.04.2017.
 */
@Component
public class StringConvertors implements Serializable {

    @Getter
    Map<Class<?>, Function<String,Object>> convertors;

    public StringConvertors() {
        convertors = new HashMap<>();
        convertors.put(String.class, (s) -> s);
        // well, ok
        convertors.put(Integer.class, (s) -> Integer.parseInt(s));
        convertors.put(int.class, (s) -> Integer.parseInt(s));
    }
}
