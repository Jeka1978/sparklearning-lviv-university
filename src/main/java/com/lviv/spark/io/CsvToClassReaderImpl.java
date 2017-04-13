package com.lviv.spark.io;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.FootballAction;
import com.lviv.spark.properties.ColumnsProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anatoliy on 14.04.2017.
 */
@Component
public class CsvToClassReaderImpl implements CsvToClassReader {
    @AutowiredBroadcast
    ColumnsProperties columnsProperties;

    @Override
    public JavaRDD<FootballAction> mapToRows(JavaRDD<String> lines) {

        return lines.map((String line) -> {
            Map<String,String> vals = GetMap(line);
            FootballAction action = new FootballAction();
            if(columnsProperties.columnExists("code"))
                action.setCode(Integer.parseInt(vals.get("code")));
            if(columnsProperties.columnExists("from"))
                action.setFrom(vals.get("from"));
            if(columnsProperties.columnExists("stadion"))
                action.setStadion(vals.get("stadion"));
            if(columnsProperties.columnExists("startTime"))
                action.setStartTime(vals.get("startTime"));
            if(columnsProperties.columnExists("time"))
                action.setTime(vals.get("time"));
            if(columnsProperties.columnExists("to"))
                action.setTo(vals.get("to"));

            return action;
        });
    }

    private Map<String,String> GetMap(String line) {
        String[] vals = line.split(";");
        Map<String,String> map = new HashMap<>();
        for(String val:vals) {
            String[] parts = val.split(" = ");
            map.put(parts[0], parts[1]);
        }
        return map;
    }
}
