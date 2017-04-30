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
 * Created by rudnitskih on 4/23/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class IncorrectPlayerTest {
    @Autowired
    IncorrectPlayer incorrectPlayer;

    @Test
    public void shouldReturnIncorrectFromPlayerCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Dmytro Rudnytskykh;to=Artyom Dzyuba".split(";"))
            );

        String code = incorrectPlayer.validate(actionInfo);

        Assert.assertEquals(incorrectPlayer.getIssueCode(), code);
    }

    @Test
    public void shouldReturnIncorrectToPlayerCode() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Artyom Dzyuba;to=Dmytro Rudnytskykh".split(";"))
            );

        String code = incorrectPlayer.validate(actionInfo);

        Assert.assertEquals(incorrectPlayer.getIssueCode(), code);
    }

    @Test
    public void shouldReturnOneCodeForBothParticipant() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Ivan Petrov;to=Dmytro Rudnytskykh".split(";"))
            );

        String code = incorrectPlayer.validate(actionInfo);

        Assert.assertEquals(incorrectPlayer.getIssueCode(), code);
    }

    @Test
    public void shouldNotReturnOneCodeForBothParticipant() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Artyom Dzyuba;to=Igor Akinfeev".split(";"))
            );

        String code = incorrectPlayer.validate(actionInfo);

        Assert.assertEquals(null, code);
    }
}