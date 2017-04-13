package com.lviv.spark.properties;

import com.lviv.spark.infrastructure.FromPropertyFile;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 12.04.2017.
 */
@Component
public class FootballTeamsProperties implements Serializable {
    public List<FootballTeam> teams;

    @FromPropertyFile(fileName = "teams.properties")
    public void setTeams(List<String> lines) {
        teams = new ArrayList<FootballTeam>();
        for(String line: lines){
            String[] parts = line.split(" = ");
            FootballTeam team = new FootballTeam(parts[0], parts[1].split(","));
            teams.add(team);
        }
    }

    public Boolean ContainsPlayer(String name){
        for(FootballTeam team: teams) {
            if(team.getPlayers().contains(name)) return true;
        }
        return false;
    }
}
