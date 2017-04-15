package com.lviv.football.configs;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Created by rudnitskih on 4/12/17.
 */
@Component
public class UserConfig implements Serializable {
    public List<String> columnNames;
    public Map<String, String> codes;
    public Map<String, List<String>> teams = new HashMap<>();
    public List<String> codesWithOneParticipant = Arrays.asList("6", "10");

    /*
     *
     */
    @Value("${columnNames}")
    private void setColumnNames(String[] columnNames) {
        this.columnNames = Arrays.asList(columnNames);
    }

    @PostConstruct
    public void initialize() {
        this.codes = readProps("codes.properties");

        readProps("teams.properties")
            .forEach((country, players) ->
                teams.put(country, Arrays.asList(players.split("\\s*,\\s*")))
            );
    }

    @SneakyThrows
    private Map<String, String> readProps(String propertiesFileName) {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(propertiesFileName);
        props.load(stream);

        HashMap<String, String> properties = new HashMap<>();
        Enumeration e = props.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            properties.put(key, props.getProperty(key));
        }

        return properties;
    }
}
