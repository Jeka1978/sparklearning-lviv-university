package com.lviv.spark;

import com.lviv.spark.io.CsvToActionReader;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.lviv.spark.constants.Profiles.DEV;

/**
 * Created by Anatoliy on 21.04.2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
@ActiveProfiles(DEV)
public class CsvToActionReaderTest {

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    CsvToActionReader reader;

    @Test
    public void mapToActionTest() throws Exception {
        List<String> strings = Arrays.asList(
            "code=6;from=Emre Can;to=Igor Akinfeev;eventTime=67:42;stadion=shepherd;startTime=5:20;"
        );
        JavaRDD<String> rdd = sc.parallelize(strings);
        List<FootballAction> actions = reader.mapToActions(rdd).collect();
        Assert.assertEquals(1, actions.size());
        FootballAction action = actions.get(0);
        Assert.assertEquals("Igor Akinfeev", action.getTo());
        Assert.assertEquals(6, action.getCode());
        Assert.assertEquals("67:42", action.getEventTime());
        Assert.assertEquals("shepherd", action.getStadion());
        Assert.assertEquals("5:20", action.getStartTime());
    }
}
