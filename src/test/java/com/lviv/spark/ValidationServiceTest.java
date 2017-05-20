package com.lviv.spark;

import com.lviv.spark.io.ActionToCsvWriter;
import com.lviv.spark.validation.ValidationResults;
import com.lviv.spark.validation.action.ActionValidationService;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Tuple2;

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
public class ValidationServiceTest {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    ActionValidationService validationService;

    @Test
    public void validateTest() throws Exception {
        FootballAction action = new FootballAction();
        action.setCode(0); // invalid code
        action.setStartTime("5:20");
        action.setEventTime("97:42"); // invalid time
        action.setStadion("shepherd");
        action.setTo("Igr Akinfeev"); // invalid name
        action.setFrom("Emre Can");

        List<FootballAction> actions = Arrays.asList(action);

        JavaRDD<FootballAction> rdd = sc.parallelize(actions);
        Tuple2<JavaRDD<FootballAction>, ValidationResults> results = validationService.validateAndFilter(rdd);

        List<FootballAction> ls = results._1().collect();
        Assert.assertEquals(0, ls.size());

        ValidationResults vr = results._2();
        Assert.assertEquals(3, vr.getResults().size());
    }
}
