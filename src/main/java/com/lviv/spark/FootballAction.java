package com.lviv.spark;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anatoliy on 14.04.2017.
 */
public class FootballAction implements Serializable {
    @Getter @Setter
    int code;
    @Getter @Setter
    String from;
    @Getter @Setter
    String to;
    @Getter @Setter
    String time;
    @Getter @Setter
    String stadion;
    @Getter @Setter
    String startTime;

    @Getter @Setter
    Map<String, Integer> validationResults;
    public FootballAction() {
        validationResults = new HashMap<>();
    }
}
