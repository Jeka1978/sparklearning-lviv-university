package com.lviv.spark.io;

import com.lviv.spark.validation.ValidationResults;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Anatoliy on 12.04.2017.
 */
@Component
public class ValidationResultsSaverImpl implements ValidationResultsSaver {
    @Override
    @SneakyThrows
    public void saveValidation(ValidationResults result, String fileName) {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        Map<String, Integer> resultsArray = result.getResults();
        for(String key:resultsArray.keySet()) {
            int value = resultsArray.get(key);
            writer.println(key+": "+value);
        }
        writer.close();
    }
}
