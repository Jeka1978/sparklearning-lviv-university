package com.lviv.spark.validation;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anatoliy on 12.04.2017.
 */
public class ValidationResults {
    @Getter @Setter
    Map<String,Integer> results;
    public ValidationResults() {
        results = new HashMap<>();
    }
}
