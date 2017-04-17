package com.lviv.spark;

import com.lviv.spark.io.CsvField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 14.04.2017.
 */
public class FootballAction implements Serializable {
    @Getter @Setter @CsvField
    int code;
    @Getter @Setter @CsvField
    String from;
    @Getter @Setter @CsvField
    String to;
    @Getter @Setter @CsvField
    String eventTime;
    @Getter @Setter @CsvField
    String stadion;
    @Getter @Setter @CsvField
    String startTime;

    @Getter @Setter @CsvField
    String teamName;
    @Getter @Setter @CsvField
    String codeName;
    @Getter @Setter @CsvField
    int timeNumber;

    @Getter @Setter
    List<String> validationErrors;
    public FootballAction() {
        validationErrors = new ArrayList<>();
    }
}
