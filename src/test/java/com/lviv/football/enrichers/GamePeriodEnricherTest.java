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
public class GamePeriodEnricherTest {
    @Autowired
    private GamePeriodEnricher gamePeriodEnricher;

    @Test
    public void shouldAddFirstPeriod() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Collections.singletonList("eventTime=42:42")
            );

        actionInfo = gamePeriodEnricher.addAdditionalData(actionInfo);

        Assert.assertEquals("First period", actionInfo.getProperty(Columns.period));
    }

    @Test
    public void shouldAddSecondPeriod() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Collections.singletonList("eventTime=45:42")
            );

        actionInfo = gamePeriodEnricher.addAdditionalData(actionInfo);

        Assert.assertEquals("Second Period", actionInfo.getProperty(Columns.period));
    }

}