package com.lviv.football.enrichers;

import com.lviv.football.ActionInfo;
import com.lviv.football.configs.MainConfig;
import com.lviv.football.constants.Columns;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.lviv.songs.constants.Profiles.DEV;

/**
 * Created by rudnitskih on 4/23/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class TeamEnricherTest {
    @Autowired
    private TeamEnricher teamEnricher;

    @Test
    public void shouldAddTeam() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Collections.singletonList("from=Denys Harmash")
            );

        actionInfo = teamEnricher.addAdditionalData(actionInfo);

        Assert.assertEquals("Ukraine", actionInfo.getProperty(Columns.team));

    }

    @Test
    public void notShouldAddTeam() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Collections.singletonList("from=Dmytro Rudnytskykh")
            );

        actionInfo = teamEnricher.addAdditionalData(actionInfo);

        Assert.assertEquals("", actionInfo.getProperty(Columns.team));
    }
}