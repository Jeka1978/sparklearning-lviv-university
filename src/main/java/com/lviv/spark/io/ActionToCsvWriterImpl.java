package com.lviv.spark.io;

import com.lviv.spark.FootballAction;
import com.lviv.spark.utils.StringUtils;
import lombok.SneakyThrows;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Anatoliy on 16.04.2017.
 */
@Component
public class ActionToCsvWriterImpl implements ActionToCsvWriter, Serializable {
    @Override
    public void write(JavaRDD<FootballAction> rdd, String fileName) {
        List<String> lines = collectActionsAsStrings(rdd);

        writeToFile(lines, fileName);
    }

    private List<String> collectActionsAsStrings(JavaRDD<FootballAction> rdd) {
        return rdd.map(action -> {
                StringBuilder sb = new StringBuilder();
                Class<? extends FootballAction> type = action.getClass();

                Field[] fields = type.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(CsvField.class)) {
                        field.setAccessible(true);
                        writeProperty(sb, field.getName(), field.get(action));
                        field.setAccessible(false);
                    }
                }

                return sb.toString();
            }).collect();
    }

    @SneakyThrows
    private void writeToFile(List<String> lines, String fileName) {
        FileWriter fw = new FileWriter(fileName);

        for (String line:lines) {
            fw.write(line + "\n");
        }

        fw.close();
    }

    private void writeProperty(StringBuilder sb, String name, Object val) {
        if(val != null && !StringUtils.isEmpty(val.toString())) {
            sb.append(String.format("%s=%s;", name, val.toString()));
        }
    }
}
