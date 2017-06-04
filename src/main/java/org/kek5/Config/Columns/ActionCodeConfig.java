package org.kek5.Config.Columns;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kek5 on 5/13/17.
 */
@Component
public class ActionCodeConfig implements Serializable{
    private static final String codeDescriptionFile = "codes.properties";
    public Map<String, String> code_description = new HashMap<>();

    @PostConstruct
    @SneakyThrows
    private void initCodeDescription() {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(codeDescriptionFile));
        String description;
        for(Object key : props.keySet()) {
            description = (String) props.get(key);
            code_description.put((String) key, description);
        }
    }
}
