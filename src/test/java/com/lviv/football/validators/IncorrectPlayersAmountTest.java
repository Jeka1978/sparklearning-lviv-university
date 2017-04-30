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
public class IncorrectPlayersAmountTest {
    @Autowired
    IncorrectPlayersAmount incorrectPlayersAmount;

    @Test
    public void shouldReturnCodeForActionWithTwoParticipant() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Dmytro Rudnytskykh;code=1".split(";"))
            );

        String code = incorrectPlayersAmount.validate(actionInfo);

        Assert.assertEquals(incorrectPlayersAmount.getIssueCode(), code);
    }

    @Test
    public void shouldNotReturnCodeForActionWithTwoParticipant() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Dmytro Rudnytskykh;to=Ivan Petrenko;code=1".split(";"))
            );

        String code = incorrectPlayersAmount.validate(actionInfo);

        Assert.assertEquals(null, code);
    }

    @Test
    public void shouldReturnCodeForActionWithOneParticipant() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Dmytro Rudnytskykh;to=Ivan Petrenko;code=6".split(";"))
            );

        String code = incorrectPlayersAmount.validate(actionInfo);

        Assert.assertEquals(code, incorrectPlayersAmount.getIssueCode());
    }

    @Test
    public void shouldNotReturnCodeForActionWithOneParticipant() throws Exception {
        ActionInfo actionInfo = new ActionInfo()
            .addProperties(
                Arrays.asList("from=Dmytro Rudnytskykhcode=6".split(";"))
            );

        String code = incorrectPlayersAmount.validate(actionInfo);

        Assert.assertEquals(null, code);
    }
}