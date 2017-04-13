package com.lviv.spark.properties;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anatoliy on 11.04.2017.
 */
public class FootballTeam implements Serializable {
    @Getter
    List<String> name;
    @Getter
    List<String> players;

    public FootballTeam(String name, String[] players) {
        this.name = Arrays.asList(name);
        this.players = Arrays.asList(players);
    }
}
