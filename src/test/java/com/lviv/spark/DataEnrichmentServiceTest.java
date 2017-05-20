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
import java.util.Optional;

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
    public void TestValuesAddedToRow() throws Exception {
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

        Optional<FootballAction> opt_action = results.stream().filter(a -> a.getCode() == 6).findFirst();
        Assert.assertNotNull(opt_action);

        FootballAction result_action = opt_action.get();
        Assert.assertEquals((Integer)2, result_action.getTimeNumber());
        Assert.assertEquals("Russia", result_action.getTeamName());
        Assert.assertEquals("kick to gate", result_action.getCodeName());
    }

    @Test
    public void TestNewRowsAdded() throws Exception {
        FootballAction action = new FootballAction();
        action.setCode(3);
        action.setStartTime("1:20");
        action.setEventTime("6:42");
        action.setStadion("shepherd");
        action.setTo("Igor Akinfeev");
        action.setFrom("Emre Can");

        List<FootballAction> actions = Arrays.asList(action);
        JavaRDD<FootballAction> rdd = sc.parallelize(actions);
        List<FootballAction> results = dataEnrichmentService.enrichData(rdd).collect();

        Assert.assertEquals(2, results.size());

        Optional<FootballAction> optional_new_action = results.stream().filter(a -> a.getCode() == 4).findFirst();
        Assert.assertNotNull(optional_new_action);

        FootballAction new_action = optional_new_action.get();
        Assert.assertEquals(4, new_action.getCode());
        Assert.assertEquals("Emre Can", new_action.getTo());
        Assert.assertEquals("Igor Akinfeev", new_action.getFrom());

        Assert.assertEquals(action.getStadion(), new_action.getStadion());
        Assert.assertEquals(action.getEventTime(), new_action.getEventTime());

    }
}