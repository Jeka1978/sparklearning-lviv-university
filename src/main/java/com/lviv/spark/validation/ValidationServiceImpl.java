package com.lviv.spark.validation;

import com.lviv.spark.properties.ColumnsProperties;
import com.lviv.spark.utils.RowUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anatoliy on 05.04.2017.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    ColumnsProperties columnsProperties;

    @Autowired
    List<ValidationRule> validationRules;

    @Override
    public Tuple2<JavaRDD<Row>,ValidationResults> validateAndFilter(JavaRDD<Row> rows) {

        // use rules according to columns configuration
        List<ValidationRule> rr = getRulesByColumns();
        ValidationRule[] rules = rr.toArray(new ValidationRule[rr.size()]);

        // apply every rule one by one
        for(ValidationRule rule: rules) {
            rows = rule.validate(rows);
        }

        // save results
        rows.persist(StorageLevel.DISK_ONLY());

        // filter all invalid values and remove validation columns
        int from = columnsProperties.getNumberOfColumns();
        int to = from+rules.length;
        JavaRDD<Row> filtered = rows.filter(row -> {
            for(int i = from; i < to; i++) {
                if(row.getInt(i) == 1) return false;
            }
            return true;
        }).map(row -> RowUtils.TakeColumns(row, from));

        // collect validation statistics
        Map<Integer, Integer> invalid = rows.flatMapToPair(row -> {
            List<Tuple2<Integer, Integer>> ls = new ArrayList<>();
            for (int i = from; i < to; i++) {
                int validationResult = row.getInt(i);
                // reduce number of operations
                if (validationResult > 0) ls.add(new Tuple2(i-from, validationResult));
            }
            return ls;
        }).reduceByKey((x,y) -> (int)x+(int)y).collectAsMap(); // I don't know why, but without explicit cast to integer I have compilation error

        ValidationResults results = new ValidationResults();
        for(int i:invalid.keySet()) {
            results.results.put(rules[i].columnName(), invalid.get(i));
        }
        return new Tuple2<>(filtered, results);
    }

    private List<ValidationRule> getRulesByColumns() {
        List<ValidationRule> ls = new ArrayList<>();
        for(ValidationRule rule: validationRules) {
            Boolean allFieldsPresented = true;
            for(String columnName: rule.requiredColumns()) {
                if(!columnsProperties.columnExists(columnName)) {
                    allFieldsPresented = false;
                    break;
                }
            }
            if(allFieldsPresented) {
                ls.add(rule);
            }
        }
        return ls;
    }
}
