package com.lviv.football;

import com.lviv.football.configs.UserConfig;
import com.lviv.football.constants.Columns;
import com.lviv.football.formaters.ActionFormatter;
import com.lviv.football.validators.ActionValidator;
import com.lviv.infra.AutowiredBroadcast;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by rudnitskih on 4/11/17.
 */
@Service
public class DataProcessorImpl implements DataProcessor {
    @AutowiredBroadcast
    private Broadcast<UserConfig> userConfig;

    /**
     * В текущей реализации @AutowiredBroadcastBPP не поддерживает коллекции,
     * можно было бы его докрутить, но времени и желания особо не было
     * (aka бизнесу нужно быстрее рабочий продукт - оптимизация потом:),
     * поэтому использовал просто стандартный @Autowired, что означает beans будут грузиться на Worker несколько раз.
     */
    @Autowired
    private List<ActionValidator> validators;

    @Autowired
    private List<ActionFormatter> formatters;

    @Override
    public JavaRDD<ActionInfo> getActionsWithValidationIssues(JavaRDD<String> originalRdd) {
        return originalRdd
            .filter(line -> !(line == null || line.trim().length() < 1)) // filter empty lines
            .map(line -> {
                List<String> properties = Arrays.asList(line.split(";"));
                List<String> selectedProperties = new ArrayList<>();

                properties.forEach(property -> {
                    if (userConfig.value().columnNames.contains(property.split("=")[0])) {
                        selectedProperties.add(property);
                    }
                });

                return new ActionInfo().addProperties(selectedProperties);
            })
            .map((action) -> {
                validators.forEach(validator -> {
                    String issueCode = validator.validate(action);

                    if (issueCode != null) {
                        action.addValidationIssue(issueCode);
                    }
                });

                return action;
            });
    }

    @Override
    public JavaRDD<ActionInfo> getExtendedData(JavaRDD<ActionInfo> actionsWithValidationIssuesRDD) {
        JavaRDD<ActionInfo> validateActions = actionsWithValidationIssuesRDD
            .filter((action) -> action.getValidationIssues().isEmpty());

        JavaRDD<ActionInfo> additionalActions = validateActions
            .map(action -> {
                if (action.getProperty("code").equals("3")) {
                    List<String> originalProperties = Arrays.asList(action.getProperties().split(";"));
                    String fromName = action.getProperty(Columns.from);
                    String toName = action.getProperty(Columns.to);

                    return new ActionInfo()
                        .addProperties(originalProperties)
                        .addProperty(Columns.code + "=" + "4")
                        .addProperty(Columns.from + "=" + toName)
                        .addProperty(Columns.to + "=" + fromName);
                } else {
                    return null;
                }
            })
            .filter(Objects::nonNull);

        return validateActions
            .union(additionalActions)
            .map(action -> {
                formatters.forEach(formatter -> formatter.addAdditionalData(action));

                return action;
            });
    }

    @Override
    public List<Tuple2<Integer, String>> getAmountIssuesByCode(JavaRDD<ActionInfo> actionsRDD) {
        return actionsRDD
            .flatMap(action -> new ArrayList<>(action.getValidationIssues()))
            .mapToPair(word -> new Tuple2<>(word, 1))
            .reduceByKey(Integer::sum)
            .mapToPair(Tuple2::swap)
            .sortByKey(false)
            .collect();
    }
}
