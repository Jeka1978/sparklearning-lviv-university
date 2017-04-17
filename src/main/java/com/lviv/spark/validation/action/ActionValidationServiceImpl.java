package com.lviv.spark.validation.action;

import com.lviv.spark.FootballAction;
import com.lviv.spark.properties.ColumnsProperties;
import com.lviv.spark.validation.ValidationResults;
import org.apache.spark.api.java.JavaRDD;
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
public class ActionValidationServiceImpl implements ActionValidationService {

    @Autowired
    ColumnsProperties columnsProperties;

    @Autowired
    List<ActionValidationRule> validationRules;

    @Override
    public Tuple2<JavaRDD<FootballAction>,ValidationResults> validateAndFilter(JavaRDD<FootballAction> actions) {

        // use rules according to columns configuration
        List<ActionValidationRule> rr = getRulesByColumns();
        ActionValidationRule[] rules = rr.toArray(new ActionValidationRule[rr.size()]);

        // apply every rule one by one
        for(ActionValidationRule rule: rules) {
            actions = rule.validate(actions);
        }

        // save intermediate result
        actions.persist(StorageLevel.DISK_ONLY());

        JavaRDD<FootballAction> filtered = actions.filter(action -> action.getValidationErrors().size() == 0);

        // collect validation statistics
        Map<String, Integer> invalid = actions.flatMapToPair(action -> {
            List<Tuple2<String, Integer>> ls = new ArrayList<>();
            List<String> validationErrors = action.getValidationErrors();
            for (String error:validationErrors) {
                ls.add(new Tuple2(error, 1));
            }
            return ls;
        }).reduceByKey((x,y) -> (int)x+(int)y).collectAsMap(); // I don't know why, but without explicit cast to integer I have compilation error

        ValidationResults results = new ValidationResults();
        results.setResults(invalid);
        return new Tuple2<>(filtered, results);
    }

    private List<ActionValidationRule> getRulesByColumns() {
        List<ActionValidationRule> ls = new ArrayList<>();
        for(ActionValidationRule rule: validationRules) {
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
