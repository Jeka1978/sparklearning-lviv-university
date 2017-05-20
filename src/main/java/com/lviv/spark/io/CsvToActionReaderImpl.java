package com.lviv.spark.io;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.FootballAction;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anatoliy on 14.04.2017.
 */
@Component
public class CsvToActionReaderImpl implements CsvToActionReader, Serializable {
    @AutowiredBroadcast
    Broadcast<StringConvertors> stringConvertors;

    @Override
    public JavaRDD<FootballAction> mapToActions(JavaRDD<String> lines) {

        return lines.map((String line) -> {
            Map<String, String> vals = GetMap(line);
            FootballAction action = new FootballAction();
            Class<? extends FootballAction> type = action.getClass();
            Field[] fields = type.getDeclaredFields();
            StringConvertors convertors = stringConvertors.value();

            for(Field field:fields) {
                if(field.isAnnotationPresent(CsvField.class) && vals.containsKey(field.getName())) {
                    Function<String, Object> convertor = convertors.getConvertors().get(field.getType());
                    Object value = convertor.call(vals.get(field.getName()));
                    field.setAccessible(true);
                    field.set(action, value);
                    field.setAccessible(false);
                }
            }

            return action;
        });
    }

    private Map<String,String> GetMap(String line) {
        String[] vals = line.split(";");
        Map<String,String> map = new HashMap<>();
        for(String val:vals) {
            String[] parts = val.split("=");
            if(parts.length > 1) {
                map.put(parts[0].trim(), parts[1].trim());
            }
        }
        return map;
    }
}
