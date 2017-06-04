package org.kek5.Config;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kek5 on 4/24/17.
 */
@Getter
@Component
public class TeamConfig implements Serializable {
    private Map<String, String> player2team = new HashMap<>();
    private String teamProperties = "teams.properties";

    @PostConstruct
    @SneakyThrows
    private void initTeams() {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(teamProperties));
        for(Object key : props.keySet()) {
            String line = (String) props.get(key);
            String[] players = line.split(",");
            for(String player : players) {
                player2team.put(player, (String)key);
            }
        }
    }
}
