package com.lviv.spark.io;

import com.lviv.spark.properties.ColumnsProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 12.04.2017.
 */
@Component
public class CsvToRowReaderImpl implements CsvToRowReader {
    @Autowired
    ColumnsProperties columnsProperties;

    @Override
    public JavaRDD<Row> mapToRows(JavaRDD<String> lines) {
        int length = columnsProperties.getNumberOfColumns();
        return lines.filter(s -> s.split(";").length == length).map((String line) -> {
            String[] vals = line.split(";");
            List<String> ls = new ArrayList<>();
            for(String val:vals) {
                String[] parts = val.split("=");
                if(parts.length > 1) {
                    ls.add(parts[1]);
                };
            }
            return RowFactory.create(ls.toArray());
        });
    }
}
