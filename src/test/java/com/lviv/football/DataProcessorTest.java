package com.lviv.football;

import com.lviv.football.configs.MainConfig;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lviv.songs.constants.Profiles.DEV;

/**
 * Created by rudnitskih on 4/23/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class DataProcessorTest {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private DataProcessor dataProcessor;

    private List<String> actionsStringList = Arrays.asList(
        "code=2;from=Dmytro Rudnytskykh;to=Sami KhediraApri;eventTime=2:12;stadion=they;startTime=21:39;",
        "code=3;from=Artyom Dzyuba;to=Sami KhediraApri;eventTime=2:12;stadion=they;startTime=55:39;",
        "code=6;from=Emre Can;to=Igor Akinfeev;eventTime=200:42;stadion=shepherd;startTime=5:20;"
    );

    @Test
    public void shouldReturnActionsWithValidationIssues() throws Exception {
        JavaRDD<String> actionsRdd = sc.parallelize(actionsStringList);

        List<ActionInfo> actionsList = dataProcessor
            .getActionsWithValidationIssues(actionsRdd)
            .collect();

        Assert.assertEquals("3", actionsList.get(0).getProperty("validationIssues"));
        Assert.assertEquals("", actionsList.get(1).getProperty("validationIssues"));
        Assert.assertEquals("1; 2", actionsList.get(2).getProperty("validationIssues"));
    }

    @Test
    public void getExtendedData() throws Exception {
        JavaRDD<String> actionsRdd = sc.parallelize(actionsStringList);

        JavaRDD<ActionInfo> actionsWithValidationIssues = dataProcessor.getActionsWithValidationIssues(actionsRdd);

        List<ActionInfo> extendedActions = dataProcessor
            .getExtendedData(actionsWithValidationIssues)
            .collect();

        ActionInfo firstAction = extendedActions.get(0);
        ActionInfo secondAction = extendedActions.get(1);

        Assert.assertEquals("Artyom Dzyuba", firstAction.getProperty("from"));
        Assert.assertEquals("Sami KhediraApri", firstAction.getProperty("to"));
        Assert.assertEquals("", firstAction.getProperty("validationIssues"));

        Assert.assertEquals("First period", firstAction.getProperty("period"));
        Assert.assertEquals("pass sent", firstAction.getProperty("actionDescription"));
        Assert.assertEquals("Russia", firstAction.getProperty("team"));

        Assert.assertEquals("Sami KhediraApri", secondAction.getProperty("from"));
        Assert.assertEquals("Artyom Dzyuba", secondAction.getProperty("to"));
        Assert.assertEquals("", secondAction.getProperty("validationIssues"));

        Assert.assertEquals("First period", secondAction.getProperty("period"));
        Assert.assertEquals("pass received", secondAction.getProperty("actionDescription"));
        Assert.assertEquals("Germany", secondAction.getProperty("team"));
    }

    @Test
    public void getAmountIssuesByCode() throws Exception {
        List<ActionInfo> actions = new ArrayList<>();

        actions.add(new ActionInfo()
            .addValidationIssue("1")
            .addValidationIssue("2")
        );

        actions.add(new ActionInfo()
            .addValidationIssue("2")
            .addValidationIssue("2")
        );

        actions.add(new ActionInfo()
            .addValidationIssue("1")
            .addValidationIssue("2")
            .addValidationIssue("3")
        );

        actions.add(new ActionInfo());

        JavaRDD<ActionInfo> actionsRdd = sc.parallelize(actions);

        List<Tuple2<Integer, String>> amountIssuesByCode = dataProcessor.getAmountIssuesByCode(actionsRdd);

        Assert.assertEquals(new Tuple2<>(3, "2"), amountIssuesByCode.get(0));
        Assert.assertEquals(new Tuple2<>(2, "1"), amountIssuesByCode.get(1));
        Assert.assertEquals(new Tuple2<>(1, "3"), amountIssuesByCode.get(2));
    }

}