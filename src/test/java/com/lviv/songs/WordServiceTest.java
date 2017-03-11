package com.lviv.songs;

import com.lviv.songs.constants.Profiles;
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

import static com.lviv.songs.constants.Profiles.*;
import static org.junit.Assert.*;

/**
 * Created by Evegeny on 11/03/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StartConfig.class)
@ActiveProfiles(DEV)
public class WordServiceTest {

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private WordService wordService;

    @Test
    public void topX() throws Exception {
        List<String> strings = Arrays.asList("java is the best", "scala", "java better than scala", "scala", "JAVA", "jaVa", "java", "java");
        JavaRDD<String> rdd = sc.parallelize(strings);
        List<String> topX = wordService.topX(rdd, 2);
        Assert.assertEquals("java",topX.get(0));
        Assert.assertEquals("scala",topX.get(1));
    }

}












