package com.lviv.spark;

import com.lviv.spark.io.ActionToCsvWriter;
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

import java.io.BufferedReader;
import java.io.File;
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
public class ActionToCsvWriterTest {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    ActionToCsvWriter writer;

    @Test
    public void writeTest() throws Exception {
        FootballAction action = new FootballAction();
        action.setCode(6);
        action.setStartTime("5:20");
        action.setEventTime("67:42");
        action.setStadion("shepherd");
        action.setTo("Igor Akinfeev");
        action.setFrom("Emre Can");

        String fileName = "test.csv";

        List<FootballAction> actions = Arrays.asList(action);

        JavaRDD<FootballAction> rdd = sc.parallelize(actions);
        writer.write(rdd, fileName);

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            List<String> lines = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }

            Assert.assertEquals(1, lines.size());
            Assert.assertEquals("code=6;from=Emre Can;to=Igor Akinfeev;eventTime=67:42;stadion=shepherd;startTime=5:20;", lines.get(0));

        } finally {
            br.close();
        }
    }
}
