package com.lviv.spark.properties;

import com.lviv.spark.infrastructure.FromPropertyFile;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anatoliy on 12.04.2017.
 */
@Component
public class FootballCodesProperties implements Serializable {
    @Getter
    Map<Integer,String> codes;

    @FromPropertyFile(fileName = "codes.properties")
    public void setCodes(List<String> lines) {
        codes = new HashMap<>();
        for(String line:lines) {
            String[] parts = line.split("=");
            if(parts.length == 2) {
                try {
                    int code = Integer.parseInt(parts[0].trim());
                    codes.put(code,parts[1].trim());
                } catch(Exception e) {

                }
            }
        }
    }

    public boolean codeIsCorrect(int code) {
        return codes.containsKey(code);
    }

    public String getCodeName(int code) {return codes.get(code); }
}
