package com.lviv.football;

import com.lviv.football.configs.UserConfig;
import com.lviv.football.constants.Columns;
import com.lviv.infra.AutowiredBroadcast;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.broadcast.Broadcast;

import java.io.Serializable;
import java.util.*;

/**
 * Created by rudnitskih on 4/11/17.
 */
@Data
public class ActionInfo implements Serializable {
    Map<String, String> props = new HashMap<>();
    private HashSet<String> validationIssues = new HashSet<>();

    @AutowiredBroadcast
    private Broadcast<UserConfig> userConfig;

    public void addValidationIssue(String newValidationIssue) {
        validationIssues.add(newValidationIssue);
        String property = Columns.validationIssues + "=" + StringUtils.join(getValidationIssues(), "; ");

        addProperty(property);
    }

    public ActionInfo addProperties(List<String> stringProperties) {
        stringProperties.forEach((stringProperty) -> {
            String[] parts = stringProperty.split("=");
            String key = parts[0];

            props.put(key, parts[1]);
        });

        return this;
    }

    public ActionInfo addProperty(String stringProperty) {
        return addProperties(Collections.singletonList(stringProperty));
    }

    public String getProperty(String key) {
        String value = props.get(key);

        if (value == null) {
            value = "";
        }

        return value;
    }

    public String getProperties() {
        String propertiesLine = "";

        for (Map.Entry<String, String> propertyEntry : props.entrySet()) {
            propertiesLine += propertyEntry.getKey() + "=" + propertyEntry.getValue() + ";";
        }

        return propertiesLine;
    }
}
