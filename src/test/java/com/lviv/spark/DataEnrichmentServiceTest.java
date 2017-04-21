package com.lviv.spark;

import com.lviv.spark.enrichment.DataEnrichmentService;
import com.lviv.spark.io.ActionToCsvWriter;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lviv.spark.constants.Profiles.DEV;

/**
 * Created by Anatoliy on 21.04.2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
@ActiveProfiles(DEV)
public class DataEnrichmentServiceTest {
    @Autowired
    JavaSparkContext sc;

    @Autowired
    DataEnrichmentService dataEnrichmentService;

    @Test
    public void writeTest() throws Exception {
        FootballAction action = new FootballAction();
        action.setCode(6);
        action.setStartTime("5:20");
        action.setEventTime("67:42");
        action.setStadion("shepherd");
        action.setTo("Igor Akinfeev");
        action.setFrom("Emre Can");

        List<FootballAction> actions = Arrays.asList(action);
        JavaRDD<FootballAction> rdd = sc.parallelize(actions);
        List<FootballAction> results = dataEnrichmentService.enrichData(rdd).collect();

        Assert.assertEquals(1, results.size());
        FootballAction action2 = results.get(0);

        Assert.assertEquals((Integer)2, action2.getTimeNumber());
        Assert.assertEquals("Russia", action2.getTeamName());
        Assert.assertEquals("kick to gate", action2.getCodeName());
    }
}
