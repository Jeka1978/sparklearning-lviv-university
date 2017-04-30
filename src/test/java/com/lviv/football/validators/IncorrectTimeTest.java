package com.lviv.football.validators;

import com.lviv.football.ActionInfo;
import com.lviv.football.configs.MainConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.lviv.songs.constants.Profiles.DEV;

/**
 * Created by rudnitskih on 4/30/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class IncorrectTimeTest {
    @Autowired
    IncorrectTime incorrectTime;

    @Test
    public void shouldReturnIncorrectHoursInStartTimeCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("startTime=48:43".split(";"))
            );

        String code = incorrectTime.validate(actionInfo);

        Assert.assertEquals(incorrectTime.getIssueCode(), code);
    }

    @Test
    public void shouldReturnIncorrectMinsInStartTimeCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("startTime=22:90".split(";"))
            );

        String code = incorrectTime.validate(actionInfo);

        Assert.assertEquals(incorrectTime.getIssueCode(), code);
    }

    @Test
    public void shouldReturnIncorrectMinsInEventTimeCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("eventTime=200:43".split(";"))
            );

        String code = incorrectTime.validate(actionInfo);

        Assert.assertEquals(incorrectTime.getIssueCode(), code);
    }

    @Test
    public void shouldReturnIncorrectSecsInEventTimeCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("eventTime=45:90".split(";"))
            );

        String code = incorrectTime.validate(actionInfo);

        Assert.assertEquals(incorrectTime.getIssueCode(), code);
    }

    @Test
    public void shouldNotReturnIssueCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("eventTime=42:42;startTime=22:00".split(";"))
            );

        String code = incorrectTime.validate(actionInfo);

        Assert.assertEquals(null, code);
    }
}